package com.example.dllo.baiduyinyue.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.RecommendBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Limiao on 16/7/14.
 * 乐库--推荐——歌单推荐的Adapter
 */
public class SongListRecommendAdapter extends BaseAdapter {
    private List<RecommendBean.ResultBean.DiyBean.SonglistRecommendstBean> songlistRecommendstBeen;
    private Context context;

    public void setSonglistRecommendstBeen(List<RecommendBean.ResultBean.DiyBean.SonglistRecommendstBean> songlistRecommendstBeen) {
        this.songlistRecommendstBeen = songlistRecommendstBeen;
        notifyDataSetChanged();
    }

    public SongListRecommendAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return songlistRecommendstBeen == null ? 0 : songlistRecommendstBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return songlistRecommendstBeen == null ? null : songlistRecommendstBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_songlist_recommend_gridview,parent,false);
            holder = new GridViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (GridViewHolder) convertView.getTag();
        }
        RecommendBean.ResultBean.DiyBean.SonglistRecommendstBean songlistRecommendstBean = songlistRecommendstBeen.get(position);
        holder.numTv.setText(songlistRecommendstBean.getListenum());
        holder.titleTv.setText(songlistRecommendstBean.getTitle());
        Picasso.with(context).load(songlistRecommendstBean.getPic()).resize(300,300).into(holder.bgIv);
        return convertView;
    }


    class GridViewHolder{
        private ImageView bgIv;
        private TextView titleTv,numTv;
        public GridViewHolder(View view) {
            bgIv = (ImageView) view.findViewById(R.id.item_gridview_bg_iv);
            titleTv = (TextView) view.findViewById(R.id.item_gridview_title_tv);
            numTv = (TextView) view.findViewById(R.id.item_gridview_num_tv);
        }
    }
}
