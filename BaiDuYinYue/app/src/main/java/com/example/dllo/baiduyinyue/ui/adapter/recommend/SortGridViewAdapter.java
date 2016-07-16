package com.example.dllo.baiduyinyue.ui.adapter.recommend;

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
 * 乐库--推荐——分类按钮的 Adapter
 */
public class SortGridViewAdapter extends BaseAdapter {
    private List<RecommendBean.ResultBean.EntryBean.ImgBtnBean> imgBtnBeen;
    private Context context;

    public void setImgBtnBeen(List<RecommendBean.ResultBean.EntryBean.ImgBtnBean> imgBtnBeen) {
        this.imgBtnBeen = imgBtnBeen;
        notifyDataSetChanged();
    }

    public SortGridViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgBtnBeen == null ? 0 :imgBtnBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return imgBtnBeen == null ? null : imgBtnBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SortGridViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sort_gridview,parent,false);
            holder = new SortGridViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (SortGridViewHolder) convertView.getTag();
        }
        RecommendBean.ResultBean.EntryBean.ImgBtnBean imgBtnBean = imgBtnBeen.get(position);
        holder.textView.setText(imgBtnBean.getTitle());
        holder.dayTv.setText(imgBtnBean.getDay());
        Picasso.with(context).load(imgBtnBean.getIcon()).into(holder.imageView);
        return convertView;
    }

    class SortGridViewHolder{
        private ImageView imageView;
        private TextView textView,dayTv;
        public SortGridViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.item_sort_gridview_iv);
            textView = (TextView) view.findViewById(R.id.item_sort_gridview_tv);
            dayTv = (TextView) view.findViewById(R.id.item_sort_gridview_day_tv);
        }
    }
}
