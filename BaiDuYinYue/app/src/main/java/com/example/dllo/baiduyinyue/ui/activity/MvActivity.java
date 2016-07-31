package com.example.dllo.baiduyinyue.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
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
    private Button horBtn,verBtn;
    @Override
    protected int setLayout() {
        return R.layout.activity_mv;
    }

    @Override
    protected void initView() {
        videoView = findView(R.id.mv_activity_vv);
        horBtn = findView(R.id.mv_activity_hor_btn);
        verBtn = findView(R.id.mv_activity_ver_btn);
        horBtn.setOnClickListener(this);
        verBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        // 获取传来的值
        Intent intent = getIntent();
        String url = intent.getStringExtra(Contant.MV_INTNET_URL);
        L.e(url+ "mv");
        mediaController = new MediaController(this);
        videoView.setVideoURI(Uri.parse(url));
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        mediaController.setVisibility(View.INVISIBLE);
        videoView.setFocusable(true);
        videoView.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mv_activity_hor_btn:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case R.id.mv_activity_ver_btn:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
        }
    }
}
