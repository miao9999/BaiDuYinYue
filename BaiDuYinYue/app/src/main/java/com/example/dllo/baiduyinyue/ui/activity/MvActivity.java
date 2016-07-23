package com.example.dllo.baiduyinyue.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.utils.Contant;

/**
 * Created by Limiao on 16/7/22.
 * 播放mv的activity
 */
public class MvActivity extends AbsBaseActivity {
    private VideoView videoView;
    private MediaController mediaController;
    @Override
    protected int setLayout() {
        return R.layout.activity_mv;
    }

    @Override
    protected void initView() {
        videoView = findView(R.id.mv_activity_vv);
    }

    @Override
    protected void initData() {
        // 获取传来的值
        Intent intent = getIntent();
        String url = intent.getStringExtra(Contant.MV_INTNET_URL);
        mediaController = new MediaController(this);
        videoView.setVideoURI(Uri.parse(url));
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        videoView.start();
    }
}
