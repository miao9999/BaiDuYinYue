package com.example.dllo.baiduyinyue.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dllo.baiduyinyue.mode.bean.RecommendBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Limiao on 16/7/13.
 * 乐库--推荐——轮播图的Adapter
 */
public class RecommendViewAdapter extends PagerAdapter {
    private Context context;

    public RecommendViewAdapter(Context context) {
        this.context = context;
    }

    private List<ImageView> imageView;
    List<RecommendBean.ResultBean.FocusBean.CyclePicBean> cyclePicBeen;

    public void setCyclePicBeen(List<RecommendBean.ResultBean.FocusBean.CyclePicBean> cyclePicBeen) {
        this.cyclePicBeen = cyclePicBeen;
        notifyDataSetChanged();
    }

    public void setImageView(List<ImageView> imageView) {
        this.imageView = imageView;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cyclePicBeen == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

//        int index = position % imageView.size();
//        ViewGroup parent = (ViewGroup) imageView.get(index).getParent();
//        if (parent != null) {
//            parent.removeView(imageView.get(index));
//        }
//        container.addView(imageView.get(index));
//        return imageView.get(index);
        ImageView imageView  = new ImageView(context);
        Picasso.with(context).load(cyclePicBeen.get(position % (cyclePicBeen.size())).getRandpic()).into(imageView);
        ViewGroup parent = (ViewGroup) imageView.getParent();
        if (parent != null) {
            parent.removeView(imageView);
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       // container.removeView(imageView.get(position % imageView.size() ));
    }
}
