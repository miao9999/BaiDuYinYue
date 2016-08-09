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
import com.example.dllo.baiduyinyue.mode.bean.TopDetailBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Limiao on 16/7/21.
 * 乐库--排行---详情的Adapter
 */
public class TopDetailAdapter extends BaseAdapter {
    private List<TopDetailBean.SongListBean> songListBeen;
    private Context context;

    public TopDetailAdapter(Context context) {
        this.context = context;
    }

    public void setSongListBeen(List<TopDetailBean.SongListBean> songListBeen) {
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
        TopDetailViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_top_detail_lv, parent, false);
            holder = new TopDetailViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (TopDetailViewHolder) convertView.getTag();
        }
        TopDetailBean.SongListBean songListBean = songListBeen.get(position);
        Picasso.with(context).load(songListBean.getPic_big()).resize(150, 150).into(holder.bgIv);
        holder.rankTv.setText(songListBean.getRank());
        holder.nameTv.setText(songListBean.getArtist_name());
        holder.titleTv.setText(songListBean.getTitle());
        return convertView;
    }


    class TopDetailViewHolder {
        private TextView titleTv, nameTv, rankTv;
        private ImageView bgIv;

        public TopDetailViewHolder(View view) {
            titleTv = (TextView) view.findViewById(R.id.item_top_detail_title_tv);
            nameTv = (TextView) view.findViewById(R.id.item_top_detail_name_tv);
            rankTv = (TextView) view.findViewById(R.id.item_top_detail_rank_tv);
            bgIv = (ImageView) view.findViewById(R.id.item_top_detail_bg_iv);
        }
    }
}
