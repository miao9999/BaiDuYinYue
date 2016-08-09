package com.example.dllo.baiduyinyue.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.KSingListBean;
import com.example.dllo.baiduyinyue.utils.L;

import java.util.List;

/**
 * Created by Limiao on 16/7/16.
 * k歌记录的Adapter
 */
public class KSingListViewAdapter extends BaseAdapter {
    private Context context;
    private List<KSingListBean.ResultBean.ItemsBean> itemsBeen;

    public void setItemsBeen(List<KSingListBean.ResultBean.ItemsBean> itemsBeen) {
        this.itemsBeen = itemsBeen;
        notifyDataSetChanged();
    }

    public KSingListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return itemsBeen == null ? 0 : itemsBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsBeen == null ? null : itemsBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        KSingViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_k_sing_listview,parent,false);
            holder = new KSingViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (KSingViewHolder) convertView.getTag();
        }
        KSingListBean.ResultBean.ItemsBean itemsBean = itemsBeen.get(position);
        holder.numTv.setText(itemsBean.getPlay_num());
        holder.titleTv.setText(itemsBean.getSong_title());
        holder.nameTv.setText(itemsBean.getArtist_name());
        return convertView;
    }

    class KSingViewHolder{
        private TextView titleTv,nameTv,numTv;
        public KSingViewHolder(View view) {
            titleTv = (TextView) view.findViewById(R.id.item_k_sing_title_tv);
            nameTv = (TextView) view.findViewById(R.id.item_k_sing_name_tv);
            numTv = (TextView) view.findViewById(R.id.item_k_sing_num_tv);
        }
    }
}
