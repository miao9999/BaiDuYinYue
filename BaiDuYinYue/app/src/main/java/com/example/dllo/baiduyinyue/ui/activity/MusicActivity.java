package com.example.dllo.baiduyinyue.ui.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.EventBean;
import com.example.dllo.baiduyinyue.mode.bean.LocalMusicSongBean;
import com.example.dllo.baiduyinyue.mode.bean.MusicDetailBean;
import com.example.dllo.baiduyinyue.mode.bean.TopDetailBean;
import com.example.dllo.baiduyinyue.mode.db.CollectionBean;
import com.example.dllo.baiduyinyue.mode.db.DBTool;
import com.example.dllo.baiduyinyue.mode.net.NetValues;
import com.example.dllo.baiduyinyue.ui.adapter.MusicAtyVpAdapter;
import com.example.dllo.baiduyinyue.ui.service.MusicService;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.utils.StreamRequest;
import com.example.dllo.baiduyinyue.utils.T;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.example.dllo.baiduyinyue.views.LrcView;
import com.example.dllo.baiduyinyue.views.PicLrcView;
import com.example.dllo.baiduyinyue.views.RunningView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Limiao on 16/7/21.
 * 播放界面的Activity
 */
public class MusicActivity extends AbsBaseActivity implements View.OnClickListener {
    private ImageView backIv;
    private SeekBar seekBar;
    private ImageView playIv, previousIv, nextIv, playModeIv, musicListIv, picOrLyricIv;
    private LocalMusicSongBean localMusicSongBean;
    private String localSongUrl;
    private MusicService.MyBinder myBinder;
    private ServiceConnection connection;
    private boolean isSeeking = false;// 监听seekbar是否被拖动
    private int currentIndex = 0;// 当前歌曲的索引
    private List<LocalMusicSongBean> localMusicSongBeen;
    private boolean isPlaying;// 监听播放状态
    private TopDetailBean topDetailBean;
    private MusicDetailBean musicDetailBean;
    private int musicType;
    private String songUrl;
    private MusicAtyVpAdapter musicAtyVpAdapter;
    private ViewPager viewPager;
    private List<View> views;
    private MusicDetailBean.SonginfoBean songinfoBean;
    private ImageView collectIv;
    private TextView startTv, endTv, titleTv, nameTv;
    private Handler handler;
    private boolean isLike = false;// 监听是为收藏,默认是不收藏
    private MusicDetailBean.BitrateBean bitrateBean;
    private LrcView lrcView;
    private View picView;
    private RefreshSongInfoReceiver refreshSongInfoReceiver;
    private RunningView runningView;
    private PicLrcView picLrcView;
    private int playMode = Contant.MODE_LOOP;// 播放模式
    private boolean refreshLike = false;// 点击上一首,下一首时刷新收藏状态,默认不收藏


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
        playModeIv = findView(R.id.music_aty_play_mode_iv);
        viewPager = findView(R.id.music_aty_viewpager);
        picOrLyricIv = findView(R.id.music_aty_lrc_pic_iv);
        startTv = findView(R.id.music_aty_start_time_tv);
        endTv = findView(R.id.music_aty_end_time_tv);
        titleTv = findView(R.id.music_aty_title_tv);
        nameTv = findView(R.id.music_aty_name_tv);

        // 绑定服务
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = (MusicService.MyBinder) service;
                myBinder.setPlayMode(playMode);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                myBinder = null;
            }
        };

        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        bindService(intent, connection, BIND_AUTO_CREATE);

    }

    @Override
    protected void initData() {
        ShareSDK.initSDK(this, "1583d5563be06");
        backIv.setOnClickListener(this);
        playIv.setOnClickListener(this);
        musicListIv.setOnClickListener(this);
        nextIv.setOnClickListener(this);
        previousIv.setOnClickListener(this);
        playModeIv.setOnClickListener(this);
        picOrLyricIv.setOnClickListener(this);
        // 歌曲信息图片
        // 歌曲图片和歌词的界面加载和设置
        musicAtyVpAdapter = new MusicAtyVpAdapter();
        views = new ArrayList<>();
        picView = LayoutInflater.from(this).inflate(R.layout.item_music_activity_pic, null);
        picLrcView = (PicLrcView) picView.findViewById(R.id.item_music_aty_like_lrcview);
        View lyricView = LayoutInflater.from(this).inflate(R.layout.item_music_aty_lyric, null);
        lrcView = (LrcView) lyricView.findViewById(R.id.item_music_aty_like_lrc_view);
        runningView = (RunningView) picView.findViewById(R.id.item_music_aty_pic_runningview);
        views.add(picView);
        views.add(lyricView);
        musicAtyVpAdapter.setViews(views);
        viewPager.setAdapter(musicAtyVpAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    picOrLyricIv.setImageResource(R.mipmap.bt_playpage_button_lyric_press);
                }
                if (position == 1) {
                    picOrLyricIv.setImageResource(R.mipmap.bt_playpage_button_pic_press);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 获取从mainAty传来的音乐的值
        Intent dataIntent = getIntent();
        musicType = dataIntent.getIntExtra("musicType", 0);
        switch (musicType) {
            // 本地音乐的数据
            case Contant.LOCAL_TYPE:
                localMusicSongBeen = dataIntent.getParcelableArrayListExtra("localMusicSongBeen");
                currentIndex = dataIntent.getIntExtra("pos", 0);
                localMusicSongBean = localMusicSongBeen.get(currentIndex);
                localSongUrl = localMusicSongBean.getPath();
                runningView.setImgSrc(R.mipmap.music_aty_pic_defult);
                titleTv.setText(localMusicSongBean.getName());
                nameTv.setText(localMusicSongBean.getSinger());
                lrcView.loadLrc(localSongUrl);
                picLrcView.loadLrc(localSongUrl);
                break;
            // topDetailMusic的数据
            case Contant.TOP_DETAIL_TYPE:
                topDetailBean = dataIntent.getParcelableExtra("topDetailBean");
                currentIndex = dataIntent.getIntExtra("pos", 0);
                musicDetailBean = dataIntent.getParcelableExtra("musicDetailBean");
                songUrl = dataIntent.getStringExtra("songUrl");
                preserSong(songUrl);


        }
        isPlaying = dataIntent.getBooleanExtra("isPlaying", false);
        if (isPlaying) {
            playIv.setImageResource(R.mipmap.bt_playpage_button_play_press);
        } else {
            playIv.setImageResource(R.mipmap.bt_playpage_button_pause_press);
        }

        // 设置seekbar的播放进度
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                myBinder.getPlayer().seekTo(progress);
                if (lrcView.hasLrc()) {
                    lrcView.onDrag(progress);
                    picLrcView.onDrag(progress);
                }
                isSeeking = false;
            }
        });

        //更新seekbar的进度
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        seekBar.setMax(myBinder.getPlayer().getDuration());
                        if (!isSeeking) {
                            seekBar.setProgress(myBinder.getPlayer().getCurrentPosition());
                        }
                        Message msg = new Message();
                        msg.obj = myBinder.getPlayer().getCurrentPosition();
                        msg.what = 0;
                        handler.sendMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                // 设置播放的时间
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                switch (msg.what) {
                    case 0:
                        int date = (int) msg.obj;
                        String startDate = simpleDateFormat.format(new Date(Long.valueOf(date)));
                        startTv.setText(startDate);
                        String endDate = simpleDateFormat.format(new Date(myBinder.getPlayer().getDuration()));
                        endTv.setText(endDate);
                        lrcView.updateTime(myBinder.getPlayer().getCurrentPosition());
                        picLrcView.updateTime(myBinder.getPlayer().getCurrentPosition());
                        break;
                }
                return true;
            }
        });

        // 收藏按钮的设置
        collectIv = (ImageView) picView.findViewById(R.id.item_music_aty_like_iv);
        collectIv.setOnClickListener(this);
        isLike = dataIntent.getBooleanExtra("isLike", false);
        if (isLike) {
            collectIv.setImageResource(R.mipmap.bt_playpage_button_like_hl);
        } else {
            collectIv.setImageResource(R.mipmap.bt_playpage_button_like_press);
        }

        //注册广播
        IntentFilter filter = new IntentFilter(Contant.REFRESH_SONG_INFO_RECEIVER);
        refreshSongInfoReceiver = new RefreshSongInfoReceiver();
        registerReceiver(refreshSongInfoReceiver, filter);

        // 旋转图片的开始和暂停
        runningView.setRunningListener(new RunningView.OnRunningListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onStop() {
            }
        });
    }

    /**
     * 刷新当前歌曲是否收藏
     */
    private void isLike() {
        if (refreshLike) {
            collectIv.setImageResource(R.mipmap.bt_playpage_button_like_hl);
        } else {
            collectIv.setImageResource(R.mipmap.bt_playpage_button_like_press);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 返回mainActivity
            case R.id.music_aty_back_iv:
                startActivity(new Intent(MusicActivity.this, MainActivity.class));
                break;
            // 播放暂停音乐
            case R.id.music_aty_play_iv:
                playOrPause();
                break;
            // 播放列表
            case R.id.music_aty_list_iv:
                break;
            // 上一首
            case R.id.music_aty_previous_iv:
                previous();
                break;
            // 下一首
            case R.id.music_aty_next_iv:
                next();
                break;
            // 播放模式
            case R.id.music_aty_play_mode_iv:
                switch (playMode) {
                    case Contant.MODE_LOOP:
                        playModeIv.setImageResource(R.mipmap.bt_playpage_loop_press);
                        myBinder.setPlayMode(Contant.MODE_LOOP);
                        playMode = Contant.MODE_RANDOM;
                        break;
                    case Contant.MODE_RANDOM:
                        playModeIv.setImageResource(R.mipmap.bt_playpage_random_press);
                        myBinder.setPlayMode(Contant.MODE_LOOP);
                        playMode = Contant.MODE_LOOP;
                        break;
                }
                break;
            // 歌词和图片
            case R.id.music_aty_lrc_pic_iv:
                if (viewPager.getCurrentItem() == 1) {
                    viewPager.setCurrentItem(0);
                    picOrLyricIv.setImageResource(R.mipmap.bt_playpage_button_lyric_press);
                    break;
                }
                if (viewPager.getCurrentItem() == 0) {
                    viewPager.setCurrentItem(1);
                    picOrLyricIv.setImageResource(R.mipmap.bt_playpage_button_pic_press);
                    break;
                }
                break;
            // 收藏
            case R.id.item_music_aty_like_iv:
                if (isLike) {
                    collectIv.setImageResource(R.mipmap.bt_playpage_button_like_press);
                    switch (musicType) {
                        case Contant.LOCAL_TYPE:
                            DBTool.getDbInstance().delBySongName(localMusicSongBean.getName());
                            break;
                        case Contant.TOP_DETAIL_TYPE:
                            DBTool.getDbInstance().delBySongName(songinfoBean.getTitle());
                            break;
                    }
                    isLike = !isLike;
                } else {
                    collectIv.setImageResource(R.mipmap.bt_playpage_button_like_hl);
                    switch (musicType) {
                        case Contant.LOCAL_TYPE:
                            CollectionBean collectionBean = new CollectionBean();
                            String singer = localMusicSongBean.getSinger();
                            String songName = localMusicSongBean.getName();
                            collectionBean.setAuthor(singer);
                            collectionBean.setSongName(songName);
                            collectionBean.setSongUrl(localMusicSongBean.getPath());
                            DBTool.getDbInstance().insert(collectionBean);
                            break;
                        case Contant.TOP_DETAIL_TYPE:
                            String topSinger = songinfoBean.getAuthor();
                            String topName = songinfoBean.getTitle();
                            String topPicUrl = songinfoBean.getPic_big();
                            CollectionBean topCollectionBean = new CollectionBean();
                            topCollectionBean.setSongUrl(bitrateBean.getShow_link());
                            topCollectionBean.setSongName(topName);
                            topCollectionBean.setAuthor(topSinger);
                            topCollectionBean.setPicUrl(topPicUrl);
                            DBTool.getDbInstance().insert(topCollectionBean);
                            break;
                    }
                    isLike = !isLike;
                }
                break;
        }
    }

    /**
     * 播放暂停键
     */
    private void playOrPause() {
        if (isPlaying) {
            playIv.setImageResource(R.mipmap.bt_playpage_button_pause_press);
            myBinder.continuePlay();
            isPlaying = !isPlaying;
            runningView.start();
        } else {
            playIv.setImageResource(R.mipmap.bt_playpage_button_play_press);
            myBinder.pause();
            isPlaying = !isPlaying;
            runningView.stop();
        }
    }

    /**
     * 上一首
     */

    private void previous() {
        myBinder.previous(musicType);
        isLike();
    }

    /**
     * 下一首
     */
    public void next() {
        myBinder.next(musicType);
        isLike();

    }

    /**
     * 在点击歌曲切换时刷新界面的歌曲信息
     */
    class RefreshSongInfoReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshLike = intent.getBooleanExtra("isLike", false);
            Log.d("RefreshSongInfoReceiver", "refreshLike:" + refreshLike);
            int current = intent.getIntExtra("currentIndex", 0);
            int type = intent.getIntExtra("type", 0);
            switch (type) {
                case Contant.LOCAL_TYPE:
                    titleTv.setText(localMusicSongBeen.get(current).getName());
                    nameTv.setText(localMusicSongBeen.get(current).getSinger());
                    break;
                case Contant.TOP_DETAIL_TYPE:
                    TopDetailBean.SongListBean songListBean = topDetailBean.getSong_list().get(current);
                    String songUrl = NetValues.SONG_ULR.replace(Contant.ADD_URL, songListBean.getSong_id());
                    preserSong(songUrl);
                    break;
            }

        }
    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        unregisterReceiver(refreshSongInfoReceiver);
        super.onDestroy();
    }


    /**
     * 解析单曲
     *
     * @param url
     */
    public void preserSong(String url) {
        runningView.setImgSrc(R.mipmap.music_aty_pic_defult);
        VolleySingle.getInstance(this).startRequest(url, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                String songUrl = url.substring(1, url.length() - 2);
                Gson gson = new Gson();
//                if (songinfoBean.getPic_huge() != null) {
//                    runningView.setImgUrl(songinfoBean.getPic_huge());
//                }
                musicDetailBean = gson.fromJson(songUrl, MusicDetailBean.class);
                songinfoBean = musicDetailBean.getSonginfo();
                bitrateBean = musicDetailBean.getBitrate();
                titleTv.setText(songinfoBean.getTitle());
                nameTv.setText(songinfoBean.getAuthor());
                picLrcView.loadLrc(songinfoBean.getLrclink());
                lrcView.loadLrc(songinfoBean.getLrclink());
            }

            @Override
            public void failure() {
            }
        });
    }

}
