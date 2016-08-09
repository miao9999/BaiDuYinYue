package com.example.dllo.baiduyinyue.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dllo.baiduyinyue.mode.bean.KSingingCyclePicBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Limiao on 16/7/16.
 * k歌界面轮播图的Adapter
 */
public class KSingViewPagerAdapter extends PagerAdapter {
    private List<KSingingCyclePicBean.KSingBean> kSingBeen;
    private Context context;

    public KSingViewPagerAdapter(Context context) {
        this.context = context;
    }

    public void setkSingBeen(List<KSingingCyclePicBean.KSingBean> kSingBeen) {
        this.kSingBeen = kSingBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return kSingBeen == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        KSingingCyclePicBean.KSingBean kSingBean = kSingBeen.get(position % kSingBeen.size());
        Picasso.with(context).load(kSingBean.getPicture()).into(imageView);
        ViewGroup parent = (ViewGroup) imageView.getParent();
        if (parent != null) {
            parent.removeView(imageView);
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
