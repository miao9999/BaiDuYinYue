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
 * Created by Limiao on 16/7/15.
 * 乐库--推荐——新碟上架的Adapter
 */
public class NewSongAdapter extends BaseAdapter {
    private Context context;
    private List<RecommendBean.ResultBean.Mix1Bean.NewSongBean> newSongBeen;

    public NewSongAdapter(Context context) {
        this.context = context;
    }

    public void setNewSongBeen(List<RecommendBean.ResultBean.Mix1Bean.NewSongBean> newSongBeen) {
        this.newSongBeen = newSongBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return newSongBeen == null ? null : newSongBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewSongViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview,parent,false);
            holder = new NewSongViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (NewSongViewHolder) convertView.getTag();
        }
        RecommendBean.ResultBean.Mix1Bean.NewSongBean newSongBean = newSongBeen.get(position);
        Picasso.with(context).load(newSongBean.getPic()).resize(300,300).into(holder.bgIv);

        holder.nameTv.setText(newSongBean.getAuthor());
        holder.titleTv.setText(newSongBean.getTitle());
        return convertView;
    }


    class NewSongViewHolder{
        private TextView titleTv,nameTv;
        private ImageView bgIv;
        public NewSongViewHolder(View view) {
            titleTv = (TextView) view.findViewById(R.id.item_gridview_title_tv);
            nameTv = (TextView) view.findViewById(R.id.item_gridview_name_tv);
            bgIv = (ImageView) view.findViewById(R.id.item_gridview_bg_iv);
        }
    }
}
