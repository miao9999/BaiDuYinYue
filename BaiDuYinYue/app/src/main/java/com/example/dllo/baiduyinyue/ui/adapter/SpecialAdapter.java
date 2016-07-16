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
public class SpecialAdapter extends BaseAdapter {
    private List<RecommendBean.ResultBean.Mod7Bean.SpecialBean> specialBeen;
    private Context context;

    public SpecialAdapter(Context context) {
        this.context = context;
    }

    public void setSpecialBeen(List<RecommendBean.ResultBean.Mod7Bean.SpecialBean> specialBeen) {
        this.specialBeen = specialBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return specialBeen == null ? 0 : specialBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return specialBeen == null ? null : specialBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RadioViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_speceal,parent,false);
            holder = new RadioViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (RadioViewHolder) convertView.getTag();
        }
        RecommendBean.ResultBean.Mod7Bean.SpecialBean specialBean = specialBeen.get(position);
        holder.titleTv.setText(specialBean.getTitle());
        holder.introTv.setText(specialBean.getDesc());
        Picasso.with(context).load(specialBean.getPic()).resize(100,100).into(holder.imageView);
        return convertView;
    }

    class RadioViewHolder{
        private TextView titleTv,introTv;
        private ImageView imageView;
        public RadioViewHolder(View view) {
            titleTv = (TextView) view.findViewById(R.id.item_listview__special_title_tv);
            introTv = (TextView) view.findViewById(R.id.item_listview_special_intro_tv);
            imageView = (ImageView) view.findViewById(R.id.item_listview_special_iv);
        }
    }
}
