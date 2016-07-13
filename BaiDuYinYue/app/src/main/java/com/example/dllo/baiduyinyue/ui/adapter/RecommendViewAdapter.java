package com.example.dllo.baiduyinyue.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dllo.baiduyinyue.mode.bean.RecommendCyclePicBean;

import java.util.List;

/**
 * Created by Limiao on 16/7/13.
 */
public class RecommendViewAdapter extends PagerAdapter {
    private List<ImageView> imageViews;
    private List<RecommendCyclePicBean.PicBean> picBeen;

    public void setImageViews(List<ImageView> imageViews) {
        this.imageViews = imageViews;
        notifyDataSetChanged();
    }

    public void setPicBeen(List<RecommendCyclePicBean.PicBean> picBeen) {
        this.picBeen = picBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imageViews == null ? 0 : imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViews.get(position));
        return imageViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViews.get(position));
    }
}
