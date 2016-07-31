package com.example.dllo.baiduyinyue.ui.fragment.mine_child_fragment;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.EventBean;
import com.example.dllo.baiduyinyue.mode.bean.LocalMusicSongBean;
import com.example.dllo.baiduyinyue.ui.activity.MainActivity;
import com.example.dllo.baiduyinyue.ui.adapter.localmusic.LocalMusicSongAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.AbsBaseFragment;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limiao on 16/7/18.
 * 我的--本地音乐---歌曲的fragment
 */
public class LocalMusicSongFragment extends AbsBaseFragment {
    private ListView listView;
    private List<LocalMusicSongBean> localMusicSongBeen;
    private LocalMusicSongAdapter songAdapter;
    private EventBus eventBus;
    private EventBean eventBean;
    private LocalMusicSongBean localMusicSongBean;

    @Override
    protected int setLayout() {
        return R.layout.fragment_child_local_music_song;
    }

    @Override
    protected void initView() {
        listView = findView(R.id.local_music_song_fragment_lv);
    }

    @Override
    protected void initData() {
        eventBus = EventBus.getDefault();
        eventBean = new EventBean();
        localMusicSongBeen = new ArrayList<>();
        songAdapter = new LocalMusicSongAdapter(context);
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String name, singer, path;
            long time, size;
            name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            time = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            LocalMusicSongBean localMusicSongBean = new LocalMusicSongBean();
            localMusicSongBean.setName(name).setPath(path).setSinger(singer).setTime(time).setSize(size);
            localMusicSongBeen.add(localMusicSongBean);
        }
        cursor.close();
        songAdapter.setSongBeen(localMusicSongBeen);
        listView.setAdapter(songAdapter);
        Intent songNumIntent = new Intent(Contant.SONG_NUM_RECEIVER);
        songNumIntent.putExtra("songNum",localMusicSongBeen.size());
        songNumIntent.putParcelableArrayListExtra("localMusicSongBeen", (ArrayList<? extends Parcelable>) localMusicSongBeen);
        context.sendBroadcast(songNumIntent);


        //listview设置点击事件,播放本地音乐

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                localMusicSongBean = (LocalMusicSongBean) parent.getItemAtPosition(position);
                eventBean.setLocalMusicSongBean(localMusicSongBean);
                eventBean.setType(Contant.LOCAL_TYPE);
                eventBean.setSongNum(localMusicSongBeen.size());
                eventBean.setCurrentIndex(position);
                L.e("localmusicsongnum",localMusicSongBeen.size() + "");
                eventBean.setLocalMusicSongBeen(localMusicSongBeen);
                eventBus.post(eventBean);


            }
        });

    }



}
