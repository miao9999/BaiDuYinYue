package com.example.dllo.baiduyinyue.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Limiao on 16/7/13.
 * 乐库--推荐——轮播图的Adapter
 */
public class RecommendViewAdapter extends PagerAdapter {

    private List<ImageView> imageView;

    public void setImageView(List<ImageView> imageView) {
        this.imageView = imageView;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imageView.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        int index = position % imageView.size();
        ViewGroup parent = (ViewGroup) imageView.get(index).getParent();
        if (parent != null) {
            parent.removeView(imageView.get(index));
        }
        container.addView(imageView.get(index));
        return imageView.get(index);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageView.get(position % imageView.size() ));
    }
}
