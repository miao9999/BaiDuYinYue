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
import com.example.dllo.baiduyinyue.utils.L;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Limiao on 16/7/16.
 * 乐库--电台的Adapter
 */
public class RadioAdapter extends BaseAdapter {
    private List<RecommendBean.ResultBean.SceneAllBean.SceneBean.ActionBean> actionBeen;
    private Context context;

    public RadioAdapter(Context context) {
        this.context = context;
    }

    public void setActionBeen(List<RecommendBean.ResultBean.SceneAllBean.SceneBean.ActionBean> actionBeen) {
        this.actionBeen = actionBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return actionBeen == null ? 0 : actionBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return actionBeen == null ? null : actionBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RadioViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_radio_gv,parent,false);
            holder = new RadioViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (RadioViewHolder) convertView.getTag();
        }
        RecommendBean.ResultBean.SceneAllBean.SceneBean.ActionBean actionBean = actionBeen.get(position);
        holder.textView.setText(actionBean.getScene_name());
        Picasso.with(context).load(actionBean.getIcon_android()).into(holder.imageView);
        return convertView;
    }

    class RadioViewHolder{
        private TextView textView;
        private ImageView imageView;
        public RadioViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.item_radio_recommend_tv);
            imageView = (ImageView) view.findViewById(R.id.item_radio_recommend_iv);
        }
    }
}
