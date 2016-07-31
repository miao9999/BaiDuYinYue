package com.example.dllo.baiduyinyue.ui.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.LocalMusicSongBean;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Limiao on 16/7/30.
 * 欢迎页,在这里处理一些当应用刚打开就要显示的数据
 */
public class WelcomeActivity extends AbsBaseActivity {
    private List<LocalMusicSongBean> localMusicSongBeen;
    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        localMusicSongBeen = new ArrayList<>();

    }

    @Override
    protected void initData() {
//        ContentResolver resolver = getContentResolver();
//        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
//        while (cursor.moveToNext()) {
//            String name, singer, path;
//            long time, size;
//            name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
//            singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
//            time = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
//            size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
//            path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
//            LocalMusicSongBean localMusicSongBean = new LocalMusicSongBean();
//            localMusicSongBean.setName(name).setPath(path).setSinger(singer).setTime(time).setSize(size);
//            localMusicSongBeen.add(localMusicSongBean);
//        }
//        cursor.close();
//        Intent songNumIntent = new Intent(Contant.SONG_NUM_RECEIVER);
//        songNumIntent.putExtra("songNum",localMusicSongBeen.size());
//        L.e("welcome",localMusicSongBeen.size() + "");
//        songNumIntent.putParcelableArrayListExtra("localMusicSongBeen", (ArrayList<? extends Parcelable>) localMusicSongBeen);
//        sendBroadcast(songNumIntent);
//        Log.d("WelcomeActivity", "welcome");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

              startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            }
        },3000);
    }
}
