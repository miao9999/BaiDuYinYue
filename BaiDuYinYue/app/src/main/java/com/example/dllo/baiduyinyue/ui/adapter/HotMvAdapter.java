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
 */
public class HotMvAdapter extends BaseAdapter {
    private List<RecommendBean.ResultBean.Mix5Bean.HotMvBean> hotMvBeen;
    private Context context;

    public void setHotMvBeen(List<RecommendBean.ResultBean.Mix5Bean.HotMvBean> hotMvBeen) {
        this.hotMvBeen = hotMvBeen;
        notifyDataSetChanged();
    }

    public HotMvAdapter(Context context) {

        this.context = context;
    }

    @Override
    public int getCount() {
        return hotMvBeen == null ? 0 : hotMvBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return hotMvBeen == null ? null : hotMvBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotMvViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview,parent,false);
            holder = new HotMvViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (HotMvViewHolder) convertView.getTag();
        }
        RecommendBean.ResultBean.Mix5Bean.HotMvBean hotMvBean = hotMvBeen.get(position);
        holder.nameTV.setText(hotMvBean.getAuthor());
        holder.titleTv.setText(hotMvBean.getTitle());
        Picasso.with(context).load(hotMvBean.getPic()).resize(300,300).into(holder.bgIv);
        return convertView;
    }

    class HotMvViewHolder {
        private ImageView bgIv;
        private TextView titleTv, nameTV;
        public HotMvViewHolder(View view) {
            bgIv = (ImageView) view.findViewById(R.id.item_gridview_bg_iv);
            titleTv = (TextView) view.findViewById(R.id.item_gridview_title_tv);
            nameTV = (TextView) view.findViewById(R.id.item_gridview_name_tv);
        }
    }
}
