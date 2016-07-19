package com.example.dllo.baiduyinyue.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Limiao on 16/7/18.
 */
public class KSingAdapter extends BaseAdapter {
    private List<ImageView> imageViews;
    private Context context;

    public KSingAdapter(Context context) {
        this.context = context;
    }

    public void setImageViews(List<ImageView> imageViews) {
        this.imageViews = imageViews;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imageViews == null ? 0 : imageViews.size();
    }

    @Override
    public Object getItem(int position) {
        return imageViews == null ? null : imageViews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


}
