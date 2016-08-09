package com.example.dllo.baiduyinyue.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.SonglistBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Limiao on 16/7/16.
 * 歌单的Adapter
 */
public class SongListAdapter extends BaseAdapter {
    private List<SonglistBean.SongListBean> songListBeen;
    private Context context;

    public SongListAdapter(Context context) {
        this.context = context;
    }

    public void setSongListBeen(List<SonglistBean.SongListBean> songListBeen) {
        this.songListBeen = songListBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return songListBeen == null ? 0 : songListBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return songListBeen == null ? null : songListBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SonglistViewHoldre holdre = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_songlist, parent, false);
            holdre = new SonglistViewHoldre(convertView);
            convertView.setTag(holdre);
        } else {
            holdre = (SonglistViewHoldre) convertView.getTag();
        }
        SonglistBean.SongListBean songListBean = songListBeen.get(position);
        holdre.nameTv.setText(songListBean.getTag());
        holdre.numTv.setText(songListBean.getListenum());
        holdre.titleTv.setText(songListBean.getTitle());
        Picasso.with(context).load(songListBean.getPic_300()).resize(400, 400).into(holdre.imageView);
        return convertView;
    }

    class SonglistViewHoldre {
        private TextView titleTv, numTv, nameTv;
        private ImageView imageView;

        public SonglistViewHoldre(View view) {
            titleTv = (TextView) view.findViewById(R.id.item_songlist_title_tv);
            numTv = (TextView) view.findViewById(R.id.item_songlist_num_tv);
            nameTv = (TextView) view.findViewById(R.id.item_songlist_name_tv);
            imageView = (ImageView) view.findViewById(R.id.item_songlist_bg_iv);


        }
    }
}
