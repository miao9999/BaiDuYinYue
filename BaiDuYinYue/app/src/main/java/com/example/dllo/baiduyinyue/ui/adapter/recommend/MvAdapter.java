package com.example.dllo.baiduyinyue.ui.adapter.recommend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.MvBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Limiao on 16/7/16.
 * 乐库--电台的Adapter
 */
public class MvAdapter extends BaseAdapter {
    private List<MvBean.ResultBean.MvListBean> mvListBeen;
    private Context context;

    public MvAdapter(Context context) {
        this.context = context;
    }

    public void setMvListBeen(List<MvBean.ResultBean.MvListBean> mvListBeen) {
        this.mvListBeen = mvListBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mvListBeen == null ? 0 : mvListBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return mvListBeen == null ? 0 : mvListBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MvViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview,parent,false);
            holder = new MvViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (MvViewHolder) convertView.getTag();
        }
        MvBean.ResultBean.MvListBean mvListBean = mvListBeen.get(position);
        holder.authorTv.setText(mvListBean.getArtist());
        holder.titleTv.setText(mvListBean.getTitle());
        Picasso.with(context).load(mvListBean.getThumbnail()).resize(460,300).into(holder.imageView);
        return convertView;
    }

    class MvViewHolder{
        private ImageView imageView;
        private TextView titleTv,authorTv;
        public MvViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.item_gridview_bg_iv);
            titleTv = (TextView) view.findViewById(R.id.item_gridview_title_tv);
            authorTv = (TextView) view.findViewById(R.id.item_gridview_name_tv);
        }
    }
}
