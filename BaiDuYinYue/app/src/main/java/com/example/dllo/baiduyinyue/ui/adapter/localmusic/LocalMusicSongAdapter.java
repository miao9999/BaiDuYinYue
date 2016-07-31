package com.example.dllo.baiduyinyue.ui.adapter.localmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.LocalMusicSongBean;

import java.util.List;

/**
 * Created by Limiao on 16/7/22.
 * 我的---本地音乐---歌曲的Adapter
 */
public class LocalMusicSongAdapter extends BaseAdapter {
    private List<LocalMusicSongBean> songBeen;
    private Context context;

    public LocalMusicSongAdapter(Context context) {
        this.context = context;
    }

    public void setSongBeen(List<LocalMusicSongBean> songBeen) {
        this.songBeen = songBeen;
    }

    @Override
    public int getCount() {
        return songBeen == null ? 0 : songBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return songBeen == null ? 0 : songBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SongViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_local_music_song_lv, parent, false);
            holder = new SongViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (SongViewHolder) convertView.getTag();
        }
        LocalMusicSongBean localMusicSongBean = songBeen.get(position);
        holder.singerTv.setText(localMusicSongBean.getName());
        holder.nameTv.setText(localMusicSongBean.getSinger());
        return convertView;
    }

    class SongViewHolder{
        private TextView nameTv,singerTv;
        public SongViewHolder(View view) {
            nameTv = (TextView) view.findViewById(R.id.item_local_music_song_lv_name_tv);
            singerTv = (TextView) view.findViewById(R.id.item_local_music_song_lv_title_tv);
        }
    }
}
