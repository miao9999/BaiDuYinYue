package com.example.dllo.baiduyinyue.ui.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Limiao on 16/7/21.
 * 音乐服务类
 */
public class MusicService extends Service {
    private MyBinder myBinder = new MyBinder();
    private MediaPlayer player = new MediaPlayer();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public class MyBinder extends Binder{
        public void play(String url){
            player = MediaPlayer.create(MusicService.this,Uri.parse(url));
            player.start();
        }

        public void pause(){
            player.pause();
        }
    }



}
