package com.example.dllo.baiduyinyue.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.db.CollectionBean;

import java.util.List;

/**
 * Created by Limiao on 16/7/27.
 * 喜欢的单曲的Adapter
 */
public class LikeSongAdapter extends BaseAdapter {
    private List<CollectionBean> collectionBeen;
    private Context context;

    public LikeSongAdapter(Context context) {
        this.context = context;
    }

    public void setCollectionBeen(List<CollectionBean> collectionBeen) {
        this.collectionBeen = collectionBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return collectionBeen == null ? 0 :collectionBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return collectionBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LikeSongViewHolder likeSongViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_local_music_song_lv,parent,false);
            likeSongViewHolder = new LikeSongViewHolder(convertView);
            convertView.setTag(likeSongViewHolder);
        }else {
            likeSongViewHolder = (LikeSongViewHolder) convertView.getTag();
        }
        likeSongViewHolder.nameTv.setText(collectionBeen.get(position).getAuthor());
        likeSongViewHolder.singerTv.setText(collectionBeen.get(position).getSongName());
        return convertView;
    }

    class LikeSongViewHolder {
        private TextView nameTv,singerTv;
        public LikeSongViewHolder(View view) {
            nameTv = (TextView) view.findViewById(R.id.item_local_music_song_lv_name_tv);
            singerTv = (TextView) view.findViewById(R.id.item_local_music_song_lv_title_tv);
        }
    }
}
