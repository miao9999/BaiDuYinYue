package com.example.dllo.baiduyinyue.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.EventBean;
import com.example.dllo.baiduyinyue.mode.bean.LocalMusicSongBean;
import com.example.dllo.baiduyinyue.mode.bean.MusicDetailBean;
import com.example.dllo.baiduyinyue.mode.net.NetValues;
import com.example.dllo.baiduyinyue.ui.fragment.MainFragment;
import com.example.dllo.baiduyinyue.ui.fragment.SearchFragment;
import com.example.dllo.baiduyinyue.ui.fragment.mine_child_fragment.LocalMusicFragment;
import com.example.dllo.baiduyinyue.ui.fragment.recommend_chlid_fragment.RecommendSortFragment;
import com.example.dllo.baiduyinyue.ui.fragment.top_child_fragment.TopDetailFragment;
import com.example.dllo.baiduyinyue.ui.service.MusicService;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.utils.OnSkipFragment;
import com.example.dllo.baiduyinyue.utils.T;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AbsBaseActivity implements OnSkipFragment, View.OnClickListener {


    private ProgressBar mProgress;
    private MainFragment mainFragment;
    private FragmentManager manager;
    private SearchFragment searchFragment;
    private LocalMusicFragment localMusicFragment;
    private RecommendSortFragment recommendSortFragment;
    private TopDetailFragment topDetailFragment;
    private ImageView playIv, headIv;
    private TextView songNameTv, singerTv;
    private MusicDetailBean musicDetailBean;
    private MusicDetailBean.SonginfoBean songinfoBean;
    private MusicDetailBean.BitrateBean bitrateBean;
    private MusicService.MyBinder myBinder;
    private ServiceConnection connection;
    private EventBus eventBus;
    private String localMusicUrl = null;

    private boolean isPlaying;// 监听播放-暂停键的状态,默认为true;
    private String intentUrl;
    private LocalMusicSongBean localMusicSongBean;
    private String url;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        mProgress = findView(R.id.main_progressbar);
        playIv = findView(R.id.main_play_pause_iv);
        headIv = findView(R.id.main_music_play_pic_iv);
        songNameTv = findView(R.id.main_song_name_tv);
        singerTv = findView(R.id.main_singer_tv);
        initFragment();


    }


    @Override
    protected void initData() {
        // 注册eventBus
        eventBus = EventBus.getDefault();
        eventBus.register(this);

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_content_frame, mainFragment);
        transaction.addToBackStack("mainFragment");
        transaction.commit();


        // 设置播放minibar
        playIv.setOnClickListener(this);
        playIv.setImageResource(R.mipmap.pause);
        // 点击播放图片跳转到播放详情界面
        headIv.setOnClickListener(this);

        // 绑定服务
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
        Intent serviceIntent = new Intent(this, MusicService.class);
        startService(serviceIntent);
        bindService(serviceIntent, connection, BIND_AUTO_CREATE);

    }

    // 接收eventBus的值
    @Subscribe
    public void onReceiver(EventBean eventBean) {
        Log.d("MainActivity", "aaaaa");
        int type = eventBean.getType();
        switch (type) {
            case Contant.LOCAL_TYPE:
                // 获取本地音乐的信息
                localMusicSongBean = eventBean.getLocalMusicSongBean();
                url = localMusicSongBean.getPath();
                myBinder.play(url);
                songNameTv.setText(localMusicSongBean.getName());
                singerTv.setText(localMusicSongBean.getSinger());

                break;
            case Contant.INTERNER_TYPE:
                intentUrl = eventBean.getSongUrl();
                // 解析数据
                VolleySingle.getInstance(this).startRequest(intentUrl, new VolleySingle.VolleyResult() {
                    @Override
                    public void success(String url) {
                        String songUrl = url.substring(1, url.length() - 2);
                        L.e("mainAty", Contant.SUCCESS);
                        Gson gson = new Gson();
                        musicDetailBean = gson.fromJson(songUrl, MusicDetailBean.class);
                        songinfoBean = musicDetailBean.getSonginfo();
                        bitrateBean = musicDetailBean.getBitrate();
                        myBinder.play(bitrateBean.getShow_link());
                        setMiniBar();
                    }

                    @Override
                    public void failure() {
                        L.e("mainAty", Contant.FAILURE);
                    }
                });
                break;
        }

    }

    /**
     * 设置播放条
     */
    private void setMiniBar() {
        Picasso.with(MainActivity.this).load(songinfoBean.getPic_big()).into(headIv);
        songNameTv.setText(songinfoBean.getTitle());
        singerTv.setText(songinfoBean.getAuthor());


    }

    @Override
    public void toFragment(int type, String url) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Bundle bundle = new Bundle();
        switch (type) {
            case Contant.SEARCH_FRAGMENT:
                transaction.replace(R.id.main_content_frame, searchFragment).addToBackStack(null);
                break;
            case Contant.MAIN_FRAGMENT:
                transaction.replace(R.id.main_content_frame, mainFragment).addToBackStack(null);
                break;
            case Contant.LOCAL_MUSIC_FRAGMENT:
                transaction.replace(R.id.main_content_frame, localMusicFragment).addToBackStack(null);
                break;
            case Contant.RECOMMEND_SORT_FRAGMENT:
                transaction.replace(R.id.main_content_frame, recommendSortFragment).addToBackStack(null);
                break;
            case Contant.TOP_DETAIL_FRAGMENT:
                bundle.putString(NetValues.TOP_DETAIL_URL, url);
                topDetailFragment.setArguments(bundle);
                transaction.replace(R.id.main_content_frame, topDetailFragment).addToBackStack(null);
                L.e("mainaty", url);
                break;
//            case Contant.LOCAL_MUSIC_SONG_FRAGMENT:
//                localMusicUrl = url;
//                myBinder.play(url);

        }
        transaction.commit();
    }


    @Override
    protected void onDestroy() {
        // 取消eventBus注册
        eventBus.unregister(this);
        // 解绑服务
        unbindService(connection);
        super.onDestroy();
    }

    /**
     * 监听返回键,当只有一个fragment在栈中时,按返回键直接finishMainActivity
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        super.onKeyDown(keyCode, event);
        return true;
    }

    private void initFragment() {
        mainFragment = new MainFragment();
        searchFragment = new SearchFragment();
        localMusicFragment = new LocalMusicFragment();
        recommendSortFragment = new RecommendSortFragment();
        topDetailFragment = new TopDetailFragment();
    }

    @Override
    public void onClick(View v) {
        T.shortMsg("play");
        switch (v.getId()) {
            //播放按钮
            case R.id.main_play_pause_iv:
                if (isPlaying) {
                    playIv.setImageResource(R.mipmap.pause);
//                    if (intentUrl.length() == 0) {
                    myBinder.play(url);
//                    } else {
//                        myBinder.play(bitrateBean.getShow_link());
//                    }
                    isPlaying = !isPlaying;
                } else {
                    playIv.setImageResource(R.mipmap.play);
                    myBinder.pause();
                    isPlaying = !isPlaying;
                }
                break;
            //跳转到播放详情
            case R.id.main_music_play_pic_iv:
                T.shortMsg("跳转");
                Intent intent = new Intent(MainActivity.this, MusicActivity.class);

                startActivity(intent);
                break;

        }
    }
}


