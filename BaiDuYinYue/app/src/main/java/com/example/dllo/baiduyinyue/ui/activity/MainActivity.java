package com.example.dllo.baiduyinyue.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
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
import com.example.dllo.baiduyinyue.mode.bean.TopDetailBean;
import com.example.dllo.baiduyinyue.mode.db.DBTool;
import com.example.dllo.baiduyinyue.mode.net.NetValues;
import com.example.dllo.baiduyinyue.ui.fragment.MainFragment;
import com.example.dllo.baiduyinyue.ui.fragment.SearchFragment;
import com.example.dllo.baiduyinyue.ui.fragment.mine_child_fragment.LikeSongFragment;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AbsBaseActivity implements OnSkipFragment, View.OnClickListener {


    private ProgressBar progressBar;
    private MainFragment mainFragment;
    private FragmentManager manager;
    private SearchFragment searchFragment;
    private LocalMusicFragment localMusicFragment;
    private RecommendSortFragment recommendSortFragment;
    private TopDetailFragment topDetailFragment;
    private ImageView playIv, headIv, nextIv, listIv;
    private TextView songNameTv, singerTv;
    private MusicDetailBean musicDetailBean;
    private MusicDetailBean.SonginfoBean songinfoBean;
    private MusicDetailBean.BitrateBean bitrateBean;
    private MusicService.MyBinder myBinder;
    private ServiceConnection connection;
    private EventBus eventBus;
    private String songNum, likeSongNum;
    private boolean isPlaying;// 监听播放-暂停键的状态
    private String intentUrl;
    private LocalMusicSongBean localMusicSongBean;
    private String url;
    private int localCurrentIndex, topDeatilCurrentIndex;// 当前播放的歌曲的位置,默认为0,也就是第一首
    private List<LocalMusicSongBean> localMusicSongBeen;
    private TopDetailBean topDetailBean;
    private Intent intent;
    private String songUrl;
    private int musicType;
    private LikeSongFragment likeSongFragment;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        progressBar = findView(R.id.main_progressbar);
        playIv = findView(R.id.main_play_pause_iv);
        headIv = findView(R.id.main_music_play_pic_iv);
        songNameTv = findView(R.id.main_song_name_tv);
        singerTv = findView(R.id.main_singer_tv);
        nextIv = findView(R.id.main_next_iv);
        listIv = findView(R.id.main_playing_list_iv);
        playIv.setOnClickListener(this);
        headIv.setOnClickListener(this);
        nextIv.setOnClickListener(this);
        listIv.setOnClickListener(this);

        initFragment();


    }


    @Override
    protected void initData() {
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
        // 注册eventBus
        eventBus = EventBus.getDefault();
        eventBus.register(this);

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_content_frame, mainFragment);
        transaction.addToBackStack("mainFragment");
        transaction.commit();


        // 设置播放minibar
        playIv.setImageResource(R.mipmap.play);

        Intent likeSongNumIntent = getIntent();
        likeSongNum = likeSongNumIntent.getStringExtra("likeSongNum");


    }

    // 接收eventBus的值
    @Subscribe
    public void onReceiver(final EventBean eventBean) {
        Log.d("MainActivity", "aaaaa");
        musicType = eventBean.getType();
        switch (musicType) {
            case Contant.LOCAL_TYPE:
                // 获取本地音乐的信息
                localMusicSongBeen = eventBean.getLocalMusicSongBeen();
                localMusicSongBean = eventBean.getLocalMusicSongBean();
                url = localMusicSongBean.getPath();
                localCurrentIndex = eventBean.getCurrentIndex();
                myBinder.play(url);
                songNameTv.setText(localMusicSongBean.getName());
                singerTv.setText(localMusicSongBean.getSinger());
                playIv.setImageResource(R.mipmap.pause);
                myBinder.setLocalMusicData(localMusicSongBeen, localCurrentIndex, musicType);
                break;
            case Contant.TOP_DETAIL_TYPE:
                // 获取网络数据
                intentUrl = eventBean.getSongUrl();
                // 解析数据
                VolleySingle.getInstance(this).startRequest(intentUrl, new VolleySingle.VolleyResult() {
                    @Override
                    public void success(String url) {
                        topDetailBean = eventBean.getTopDetailBean();
                        topDeatilCurrentIndex = eventBean.getCurrentIndex();
                        songUrl = url.substring(1, url.length() - 2);
                        L.e("mainAty", Contant.SUCCESS);
                        Gson gson = new Gson();
                        musicDetailBean = gson.fromJson(songUrl, MusicDetailBean.class);
                        songinfoBean = musicDetailBean.getSonginfo();
                        bitrateBean = musicDetailBean.getBitrate();
                        myBinder.play(bitrateBean.getShow_link());
                        Picasso.with(MainActivity.this).load(songinfoBean.getPic_big()).resize(150, 150).error(R.mipmap.view_loading).into(headIv);
                        songNameTv.setText(songinfoBean.getTitle());
                        singerTv.setText(songinfoBean.getAuthor());
                        playIv.setImageResource(R.mipmap.pause);
                        myBinder.setTopDetailData(topDetailBean, topDeatilCurrentIndex, musicType);
                    }

                    @Override
                    public void failure() {
                        L.e("mainAty", Contant.FAILURE);
                    }
                });
                break;
        }

        // 同步进度条
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        progressBar.setMax(myBinder.getPlayer().getDuration());
                        progressBar.setProgress(myBinder.getPlayer().getCurrentPosition());
//                        L.e("progressbar");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }


    /**
     * 跳转到其他的fragment界面
     *
     * @param type fragment的标记类型
     * @param url  相应界面要传来的url
     */
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
            case Contant.LIKE_SONG_FRAGMENT:
                transaction.replace(R.id.main_content_frame, likeSongFragment).addToBackStack(null);
                break;
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

    /**
     * 初始化fragment
     */
    private void initFragment() {
        mainFragment = new MainFragment();
        searchFragment = new SearchFragment();
        localMusicFragment = new LocalMusicFragment();
        recommendSortFragment = new RecommendSortFragment();
        topDetailFragment = new TopDetailFragment();
        likeSongFragment = new LikeSongFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //播放按钮
            case R.id.main_play_pause_iv:
//                if (myBinder.getPlayer() != null) {
                if (isPlaying) {
                    playIv.setImageResource(R.mipmap.pause);
                    myBinder.play(url);
                    isPlaying = !isPlaying;
                } else {
                    playIv.setImageResource(R.mipmap.play);
                    myBinder.pause();
                    isPlaying = !isPlaying;
                }
//                } else {
//                    T.shortMsg("请选择歌曲");
//                }
                break;
            //跳转到播放详情,并把值都传到播放的musicActivity
            case R.id.main_music_play_pic_iv:
                T.shortMsg("跳转");
                switch (musicType) {
                    case Contant.LOCAL_TYPE:
                        intent = new Intent(MainActivity.this, MusicActivity.class);
                        intent.putParcelableArrayListExtra("localMusicSongBeen", (ArrayList<? extends Parcelable>) localMusicSongBeen);
                        intent.putExtra("pos", localCurrentIndex);
                        intent.putExtra("musicType", Contant.LOCAL_TYPE);
                        if ((DBTool.getDbInstance().queryBySongName(localMusicSongBean.getName()).size() > 0)) {
                            intent.putExtra("isLike", true);
                        } else {
                            intent.putExtra("isLike", false);
                        }
                        break;

                    case Contant.TOP_DETAIL_TYPE:
                        intent = new Intent(MainActivity.this, MusicActivity.class);
                        intent.putExtra("topDetailBean", topDetailBean);
                        intent.putExtra("musicType", Contant.TOP_DETAIL_TYPE);
                        intent.putExtra("pos", topDeatilCurrentIndex);
                        intent.putExtra("musicDetailBean", musicDetailBean);
                        intent.putExtra("songUrl", intentUrl);
                        if (TextUtils.isEmpty(songinfoBean.getTitle())) {
                            if (DBTool.getDbInstance().queryBySongName(songinfoBean.getTitle()).size() > 0) {
                                intent.putExtra("isLike", true);
                            } else {
                                intent.putExtra("isLike", false);
                            }
                        }
                        break;
                }
                intent.putExtra("isPlaying", isPlaying);
                startActivity(intent);
                break;
            // 下一首
            case R.id.main_next_iv:
                T.shortMsg("下一首");
                switch (musicType) {
                    case Contant.LOCAL_TYPE:
                        myBinder.next(Contant.LOCAL_TYPE);
                        localCurrentIndex++;
                        songNameTv.setText(localMusicSongBeen.get(localCurrentIndex).getName());
                        singerTv.setText(localMusicSongBeen.get(localCurrentIndex).getSinger());
                        break;
                    case Contant.TOP_DETAIL_TYPE:
                        myBinder.next(Contant.TOP_DETAIL_TYPE);
                        topDeatilCurrentIndex++;
                        Picasso.with(this).load(topDetailBean.getSong_list().get(topDeatilCurrentIndex).getPic_small()).resize(150, 150).error(R.mipmap.view_loading).into(headIv);
                        songNameTv.setText(topDetailBean.getSong_list().get(topDeatilCurrentIndex).getTitle());
                        singerTv.setText(topDetailBean.getSong_list().get(topDeatilCurrentIndex).getArtist_name());
                        break;
                }
                break;
            case R.id.main_playing_list_iv:
                T.shortMsg("列表");
                break;

        }
    }

}


