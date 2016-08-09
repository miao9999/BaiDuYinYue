package com.example.dllo.baiduyinyue.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limiao on 16/7/15.
 * Adapter的基类
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private List<T> datas = new ArrayList<>();
    private Context context;

    public void setDatas(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public MyBaseAdapter(Context context,List<T> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(setLayout(),parent,false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (MyViewHolder) convertView.getTag();
        }
        T data = datas.get(position);

        return convertView;
    }

    protected abstract int  setLayout();

    class MyViewHolder {
        private ImageView bgIv;
        private TextView titleTv,numTv;
        public MyViewHolder(View view) {
            bgIv = (ImageView) view.findViewById(R.id.item_gridview_bg_iv);
            titleTv = (TextView) view.findViewById(R.id.item_gridview_title_tv);
            numTv = (TextView) view.findViewById(R.id.item_gridview_num_tv);
        }
    }



}
