package com.example.dllo.baiduyinyue.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;

/**
 * Created by Limiao on 16/7/22.
 * 播放mv的activity
 */
public class MvActivity extends AbsBaseActivity implements View.OnClickListener {
    private VideoView videoView;
    private MediaController mediaController;
    private ImageView fullScreenIv, smallScreenIv;
    private SeekBar seekBar;
    private LinearLayout bottomLl;
    private FrameLayout frameLayout;
    private Handler handler;

    @Override
    protected int setLayout() {
        return R.layout.activity_mv;
    }

    @Override
    protected void initView() {
        videoView = findView(R.id.mv_activity_vv);
        fullScreenIv = findView(R.id.mv_activity_full_screen_iv);
        smallScreenIv = findView(R.id.mv_activity_small_screen_iv);
        seekBar = findView(R.id.mv_activity_seekbar);
        bottomLl = findView(R.id.mv_activity_bottom_ll);
        frameLayout = findView(R.id.mv_activity_fl);
        fullScreenIv.setOnClickListener(this);
        smallScreenIv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        // 获取传来的值
        final Intent intent = getIntent();
        String url = intent.getStringExtra(Contant.MV_INTNET_URL);
        if (!TextUtils.isEmpty(url)) {
            mediaController = new MediaController(this);
        }
        videoView.setVideoURI(Uri.parse(url));
        videoView.setFocusable(true);
        videoView.start();
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomLl.setVisibility(View.VISIBLE);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    seekBar.setMax(videoView.getDuration());
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                seekBar.setProgress(videoView.getCurrentPosition());
                L.e("mv",videoView.getCurrentPosition() + "");
                return false;
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    videoView.seekTo(seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mv_activity_full_screen_iv:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case R.id.mv_activity_small_screen_iv:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
        }
    }
}
