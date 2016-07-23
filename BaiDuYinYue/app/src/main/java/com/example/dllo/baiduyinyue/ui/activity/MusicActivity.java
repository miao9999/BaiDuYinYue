package com.example.dllo.baiduyinyue.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.EventBean;
import com.example.dllo.baiduyinyue.mode.bean.LocalMusicSongBean;
import com.example.dllo.baiduyinyue.mode.bean.MusicDetailBean;
import com.example.dllo.baiduyinyue.ui.service.MusicService;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.utils.T;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Limiao on 16/7/21.
 * 播放界面的Activity
 */
public class MusicActivity extends AbsBaseActivity implements View.OnClickListener {
    private ImageView backIv;
    private SeekBar seekBar;
    private ImageView playIv, previousIv, nextIv, playModeIv, musicListIv;
    private LocalMusicSongBean localMusicSongBean;
    private String url;
    private MusicService.MyBinder myBinder;
    private ServiceConnection connection;

    @Override
    protected int setLayout() {
        return R.layout.activity_music;
    }

    @Override
    protected void initView() {
        backIv = findView(R.id.music_aty_back_iv);
        playIv = findView(R.id.music_aty_play_iv);
        previousIv = findView(R.id.music_aty_previous_iv);
        nextIv = findView(R.id.music_aty_next_iv);
        musicListIv = findView(R.id.music_aty_list_iv);
        seekBar = findView(R.id.music_aty_seek_bar);

    }

    @Override
    protected void initData() {
        backIv.setOnClickListener(this);
        EventBus.getDefault().register(this);

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = (MusicService.MyBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                myBinder = null;
            }
        };

        Intent intent = new Intent(this,MusicService.class);
        startService(intent);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    public void onReceiver(EventBean eventBean) {
        int type = eventBean.getType();
        switch (type) {
            case Contant.LOCAL_TYPE:
                localMusicSongBean = eventBean.getLocalMusicSongBean();
                myBinder.play(localMusicSongBean.getPath());
                break;
            case Contant.INTERNER_TYPE:

                VolleySingle.getInstance(this).startRequest(eventBean.getSongUrl(), new VolleySingle.VolleyResult() {
                    @Override
                    public void success(String url) {
                        L.e("mainAty", Contant.SUCCESS);
                        Gson gson = new Gson();
                        MusicDetailBean musicDetailBean = gson.fromJson(url, MusicDetailBean.class);
                        url = musicDetailBean.getBitrate().getShow_link();
                        myBinder.play(url);
                    }

                    @Override
                    public void failure() {
                        L.e("mainAty", Contant.FAILURE);
                    }
                });
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_aty_back_iv:
                T.shortMsg("返回");
                startActivity(new Intent(MusicActivity.this, MainActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
