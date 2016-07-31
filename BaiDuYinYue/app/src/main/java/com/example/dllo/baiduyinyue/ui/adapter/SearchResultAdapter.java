package com.example.dllo.baiduyinyue.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.SearchResultBean;

import java.util.List;

/**
 * Created by Limiao on 16/7/30.
 * 搜索结果的Adapter
 */
public class SearchResultAdapter extends BaseAdapter {
    private List<SearchResultBean.ResultBean.SongInfoBean.SongListBean> songListBeen;
    private Context context;

    public void setSongListBeen(List<SearchResultBean.ResultBean.SongInfoBean.SongListBean> songListBeen) {
        this.songListBeen = songListBeen;
        notifyDataSetChanged();
    }

    public SearchResultAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return songListBeen == null ? 0: songListBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return songListBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_local_music_song_lv,parent,false);
            holder = new SearchViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (SearchViewHolder) convertView.getTag();
        }
        SearchResultBean.ResultBean.SongInfoBean.SongListBean songListBean = songListBeen.get(position);
        holder.nameTv.setText(songListBean.getAuthor());
        holder.titleTv.setText(songListBean.getTitle());
        return convertView;
    }

    class SearchViewHolder{
        TextView titleTv,nameTv;
        public SearchViewHolder(View view) {
            titleTv = (TextView) view.findViewById(R.id.item_local_music_song_lv_title_tv);
            nameTv = (TextView) view.findViewById(R.id.item_local_music_song_lv_name_tv);
        }
    }
}
