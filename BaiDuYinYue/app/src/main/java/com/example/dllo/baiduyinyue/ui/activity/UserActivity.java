package com.example.dllo.baiduyinyue.ui.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.ui.application.MyApp;
import com.example.dllo.baiduyinyue.ui.service.AutoCloseService;
import com.example.dllo.baiduyinyue.ui.service.MusicService;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;


/**
 * Created by Limiao on 16/7/17.
 * 个人中心的activity
 */
public class UserActivity extends AbsBaseActivity implements View.OnClickListener, Handler.Callback {
    private TextView titleTv;
    private TextView loginTv;
    private PlatformActionListener paListener;
    private TextView userLoginTv;
    private TextView userNickName;
    private ImageView userIconIv;
    private Platform qq;
    private SharedPreferences sharedPreferences;
    private TextView exitTv;
    private ImageView autoCloseIv;
    private boolean isClose;// 记录是否选择定时关闭
    int pro;
    long time;// 定时关闭的时间

    @Override
    protected int setLayout() {
        return R.layout.activity_user;
    }

    @Override
    protected void initView() {
        titleTv = findView(R.id.user_aty_title_more_tv);
        loginTv = findView(R.id.user_aty_login_tv);
        userLoginTv = findView(R.id.user_aty_login_tv);
        userNickName = findView(R.id.user_aty_login_tv_after_tv);
        userIconIv = findView(R.id.user_aty_icon_iv);
        exitTv = findView(R.id.user_aty_exit_tv);
        autoCloseIv = findView(R.id.item_user_second_lv_auro_close_iv);
        titleTv.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        autoCloseIv.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        userIconIv.setVisibility(View.GONE);
        exitTv.setVisibility(View.GONE);
        ShareSDK.initSDK(this, "1583d5563be06");
        // 三方登录
        paListener = new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = platform;
                handleMessage(msg);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Message msg = new Message();
                msg.what = 2;
                msg.obj = platform;
                handleMessage(msg);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Message msg = new Message();
                msg.what = 3;
                msg.obj = platform;
                handleMessage(msg);
            }
        };

        // 判断是否启动定时关闭,设置相应的图标
        SharedPreferences sp = getSharedPreferences("isClose", MODE_PRIVATE);
        isClose = sp.getBoolean("close", false);
        if (isClose) {
            autoCloseIv.setImageResource(R.mipmap.bt_setup_auto_close_hl);
        } else {
            autoCloseIv.setImageResource(R.mipmap.bt_setup_auto_close_nor);
        }

        // 登录成功保存用户信息
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String userName = sharedPreferences.getString("username", Contant.LOGIN_INFO);
        String userIcon = sharedPreferences.getString("usericon", "");
        userNickName.setText(userName);
        if (TextUtils.isEmpty(userIcon)) {
            userIconIv.setImageResource(R.mipmap.user);
        } else {
            Picasso.with(this).load(userIcon).error(R.mipmap.user).into(userIconIv);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 返回
            case R.id.user_aty_title_more_tv:
                goTo(this, MainActivity.class);
                break;
            // 登录
            case R.id.user_aty_login_tv:
                qq = ShareSDK.getPlatform(QQ.NAME);
                qq.setPlatformActionListener(paListener);
                qq.authorize();
                break;
            // 显示定时关闭的dialog
            case R.id.item_user_second_lv_auro_close_iv:
                showDialog();
                break;
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        Platform platform = (Platform) msg.obj;
        switch (msg.what) {
            case 1:
                userNickName.setText(platform.getDb().getUserName());
                userLoginTv.setVisibility(View.GONE);
                userIconIv.setVisibility(View.VISIBLE);
                Picasso.with(UserActivity.this).load(qq.getDb().getUserIcon()).error(R.mipmap.view_loading).into(userIconIv);
                sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                exitTv.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", platform.getDb().getUserName());
                editor.putString("usericon", platform.getDb().getUserIcon());
                editor.commit();
                Toast.makeText(this, MyApp.getContext().getString(R.string.success), Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, MyApp.getContext().getString(R.string.failure), Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, MyApp.getContext().getString(R.string.cancel), Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    /**
     * 定时关闭程序的dialog
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.item_autoclose_dialog, null);
        final SeekBar seekBar = (SeekBar) view.findViewById(R.id.item_auto_close_seekbar);
        final TextView timeTv = (TextView) view.findViewById(R.id.item_auto_close_time_tv);
        seekBar.setMax(120);
        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(UserActivity.this, AutoCloseService.class);
        final PendingIntent pi = PendingIntent.getService(UserActivity.this, 0, intent, 0);
        SharedPreferences sp = getSharedPreferences("isClose", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeTv.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pro = seekBar.getProgress();
                time = pro * 60 * 1000;

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alarmManager.cancel(pi);
                autoCloseIv.setImageResource(R.mipmap.bt_setup_auto_close_nor);
                editor.putBoolean("close", false);
                editor.commit();
            }
        }).setPositiveButton("开始计时", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // RTC_WAKEUP 表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用绝对时间
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, time + System.currentTimeMillis(), pi);
                autoCloseIv.setImageResource(R.mipmap.bt_setup_auto_close_hl);
                editor.putBoolean("close", true);
                editor.commit();
            }
        }).setView(view).show();
    }

}
