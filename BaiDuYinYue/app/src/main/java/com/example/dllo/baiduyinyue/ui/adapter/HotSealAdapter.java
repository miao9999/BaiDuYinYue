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
public class HotSealAdapter extends BaseAdapter {
    private List<RecommendBean.ResultBean.Mix22Bean.HotSealBean> hotSealBeen;
    private Context context;

    public HotSealAdapter(Context context) {
        this.context = context;
    }

    public void setHotSealBeen(List<RecommendBean.ResultBean.Mix22Bean.HotSealBean> hotSealBeen) {
        this.hotSealBeen = hotSealBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return hotSealBeen == null ? 0 : 3;
    }

    @Override
    public Object getItem(int position) {
        return hotSealBeen == null ? null : hotSealBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotSealViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview,parent,false);
            holder = new HotSealViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (HotSealViewHolder) convertView.getTag();
        }
        RecommendBean.ResultBean.Mix22Bean.HotSealBean hotSealBean = hotSealBeen.get(position);
        Picasso.with(context).load(hotSealBean.getPic()).resize(300,300).into(holder.imageView);
        holder.titleTv.setText(hotSealBean.getTitle());
        holder.nameTv.setText(hotSealBean.getAuthor());
        return convertView;
    }

    class HotSealViewHolder{
        private TextView titleTv,nameTv;
        private ImageView imageView;
        public HotSealViewHolder(View view) {
            titleTv = (TextView) view.findViewById(R.id.item_gridview_title_tv);
            nameTv = (TextView) view.findViewById(R.id.item_gridview_name_tv);
            imageView = (ImageView) view.findViewById(R.id.item_gridview_bg_iv);

        }
    }
}
