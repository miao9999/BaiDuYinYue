package com.example.dllo.baiduyinyue.ui.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.dllo.baiduyinyue.mode.bean.LocalMusicSongBean;
import com.example.dllo.baiduyinyue.mode.bean.MusicDetailBean;
import com.example.dllo.baiduyinyue.mode.bean.TopDetailBean;
import com.example.dllo.baiduyinyue.mode.db.DBTool;
import com.example.dllo.baiduyinyue.mode.net.NetValues;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by Limiao on 16/7/21.
 * 音乐服务类
 */
public class MusicService extends Service {
    private MyBinder myBinder = new MyBinder();
    private MediaPlayer player = new MediaPlayer();
    private List<LocalMusicSongBean> localMusicSongBeen;
    private int localCurrentIndex, topDetailCurrentIndex;
    private TopDetailBean topDetailBean;
    private MusicDetailBean musicDetailBean;
    private int type;
    private Intent intent = new Intent(Contant.REFRESH_SONG_INFO_RECEIVER);
    private int playMode;// 播放模式


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return myBinder;
    }

    public class MyBinder extends Binder {
        /**
         * 开始播放
         *
         * @param url
         */
        public void play(String url) {
            player.reset();
            try {
                player.setDataSource(url);
                player.prepareAsync();
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        player.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            completion();

        }

        /**
         * 继续播放
         */
        public void continuePlay() {
            player.start();
        }

        /**
         * 停止播放
         */
        public void pause() {
            player.pause();
        }

        /**
         * 下一首
         *
         * @param type
         */
        public void next(final int type) {
            switch (type) {
                case Contant.LOCAL_TYPE:
                    // 循环播放
                    if (playMode == Contant.MODE_LOOP) {
                        localCurrentIndex++;
                        if (localCurrentIndex == (localMusicSongBeen.size())) {
                            localCurrentIndex = 0;
                        }
                        // 随机播放
                    } else if (playMode == Contant.MODE_RANDOM) {
                        Random random = new Random();
                        localCurrentIndex = random.nextInt(localMusicSongBeen.size() - 1);
                    }
                    play(localMusicSongBeen.get(localCurrentIndex).getPath());
                    intent.putExtra("currentIndex", localCurrentIndex);
                    intent.putExtra("type", Contant.LOCAL_TYPE);
                    // 判断当前歌曲否收藏
                    if (DBTool.getDbInstance().queryBySongName(localMusicSongBeen.get(localCurrentIndex).getName()).size() > 0) {
                        intent.putExtra("isLike", true);
                    } else {
                        intent.putExtra("isLike", false);
                    }
                    sendBroadcast(intent);
                    break;
                case Contant.TOP_DETAIL_TYPE:
                    // 循环播放
                    if (playMode == Contant.MODE_LOOP) {
                        topDetailCurrentIndex++;
                        if (topDetailCurrentIndex == topDetailBean.getSong_list().size()) {
                            topDetailCurrentIndex = 0;
                        }
                        // 随机播放
                    } else if (playMode == Contant.MODE_RANDOM) {
                        Random random = new Random();
                        topDetailCurrentIndex = random.nextInt(topDetailBean.getSong_list().size() - 1);

                    }
                    String songUrl = NetValues.SONG_ULR.replace(Contant.ADD_URL, topDetailBean.getSong_list().get(topDetailCurrentIndex).getSong_id());
                    VolleySingle.getInstance(getApplicationContext()).startRequest(songUrl, new VolleySingle.VolleyResult() {
                        @Override
                        public void success(String url) {
                            Gson gson = new Gson();
                            String myUrl = url.substring(1, url.length() - 2);
                            musicDetailBean = gson.fromJson(myUrl, MusicDetailBean.class);
                            MusicDetailBean.BitrateBean bitrateBean = musicDetailBean.getBitrate();
                            play(bitrateBean.getShow_link());
                            intent.putExtra("currentIndex", topDetailCurrentIndex);
                            intent.putExtra("type", Contant.TOP_DETAIL_TYPE);
                            // 判断当前歌曲否收藏
                            if (DBTool.getDbInstance().queryBySongName(musicDetailBean.getSonginfo().getTitle()).size() > 0) {
                                intent.putExtra("isLike", true);
                            } else {
                                intent.putExtra("isLike", false);
                            }
                            sendBroadcast(intent);
                        }

                        @Override
                        public void failure() {

                        }
                    });
                    break;
            }
        }

        /**
         * 上一首
         *
         * @param type
         */
        public void previous(int type) {
            switch (type) {
                case Contant.LOCAL_TYPE:
                    if (localCurrentIndex > 0) {
                        localCurrentIndex--;
                        play(localMusicSongBeen.get(localCurrentIndex).getPath());
                        intent.putExtra("currentIndex", localCurrentIndex);
                        intent.putExtra("type", Contant.LOCAL_TYPE);
                        // 判断当前歌曲否收藏
                        if (DBTool.getDbInstance().queryBySongName(localMusicSongBeen.get(localCurrentIndex).getName()).size() > 0) {
                            intent.putExtra("isLike", true);
                        } else {
                            intent.putExtra("isLike", false);
                        }
                        sendBroadcast(intent);
                    } else {
                        Toast.makeText(MusicService.this, "已经是第一首歌", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Contant.TOP_DETAIL_TYPE:
                    if (topDetailCurrentIndex > 0) {
                        topDetailCurrentIndex--;
                        String songUrl = NetValues.SONG_ULR.replace(Contant.ADD_URL, topDetailBean.getSong_list().get(topDetailCurrentIndex).getSong_id());
                        VolleySingle.getInstance(getApplicationContext()).startRequest(songUrl, new VolleySingle.VolleyResult() {
                            @Override
                            public void success(String url) {
                                Gson gson = new Gson();
                                String myUrl = url.substring(1, url.length() - 2);
                                musicDetailBean = gson.fromJson(myUrl, MusicDetailBean.class);
                                MusicDetailBean.BitrateBean bitrateBean = musicDetailBean.getBitrate();
                                play(bitrateBean.getShow_link());
                                intent.putExtra("currentIndex", topDetailCurrentIndex);
                                intent.putExtra("type", Contant.TOP_DETAIL_TYPE);
                                // 判断当前歌曲否收藏
                                if (DBTool.getDbInstance().queryBySongName(musicDetailBean.getSonginfo().getTitle()).size() > 0) {
                                    intent.putExtra("isLike", true);
                                } else {
                                    intent.putExtra("isLike", false);
                                }
                                sendBroadcast(intent);
                            }

                            @Override
                            public void failure() {

                            }
                        });
                    } else {
                        Toast.makeText(MusicService.this, "已经是第一首歌", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }


        /**
         * 获取MediaPlayer对象
         *
         * @return
         */
        public MediaPlayer getPlayer() {
            return player;
        }

        /**
         * 设置本地音乐信息
         *
         * @param localMusicData
         * @param pos
         * @param musicType
         */
        public void setLocalMusicData(List<LocalMusicSongBean> localMusicData, int pos, int musicType) {
            localMusicSongBeen = localMusicData;
            localCurrentIndex = pos;
            type = musicType;
            L.e("MyBinder", "localMusicSongBeen.size():" + localMusicSongBeen.size());

        }

        /**
         * 设置排行音乐信息
         *
         * @param topDetailData
         * @param pos
         * @param musicType
         */
        public void setTopDetailData(TopDetailBean topDetailData, int pos, int musicType) {
            topDetailBean = topDetailData;
            topDetailCurrentIndex = pos;
            type = musicType;
            L.e("MyBinder", "topdetail" + topDetailData.getSong_list().size());
        }

        public void completion() {
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    switch (type) {
                        case Contant.LOCAL_TYPE:
                            next(type);
                            break;
                        case Contant.TOP_DETAIL_TYPE:
                            next(type);
                            break;
                    }
                }
            });
        }

        /**
         * 播放模式
         *
         * @param mode
         */
        public void setPlayMode(int mode) {
            playMode = mode;
        }
    }


}
