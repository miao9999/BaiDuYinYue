package com.example.dllo.baiduyinyue.ui.adapter;

import android.graphics.pdf.PdfDocument;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Limiao on 16/7/26.
 * 播放界面的viewpager的Adapter
 */
public class MusicAtyVpAdapter extends PagerAdapter {
    private List<View> views;

    public void setViews(List<View> views) {
        this.views = views;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return views == null ? 0: views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
