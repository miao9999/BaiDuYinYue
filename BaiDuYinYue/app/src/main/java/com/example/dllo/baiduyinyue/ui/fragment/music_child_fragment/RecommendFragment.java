package com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.RecommendCyclePicBean;
import com.example.dllo.baiduyinyue.ui.adapter.RecommendViewAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.AbsBaseFragment;
import com.example.dllo.baiduyinyue.utils.Values;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limiao on 16/7/13.
 */
public class RecommendFragment extends AbsBaseFragment {
    private List<ImageView> imageViews;
    private ViewPager viewPager;
    private RecommendViewAdapter recommendViewAdapter;
    private ImageView imageView;
    @Override
    protected int setLayout() {
        return R.layout.fragment_music_child_recommend;
    }

    @Override
    protected void initView() {
        viewPager = findView(R.id.recommend_fragment_viewpager);
    }

    @Override
    protected void initData() {
        imageViews = new ArrayList<>();
        recommendViewAdapter = new RecommendViewAdapter();
        viewPager.setPageMargin(20);
        viewPager.setOffscreenPageLimit(3);
        imageView = new ImageView(context);
        VolleySingle.getInstance(context).startRequest(Values.RECOMMEND_CYCLE_PIC_URL, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                Gson gson = new Gson();
                RecommendCyclePicBean recommendCyclePicBean = gson.fromJson(url,RecommendCyclePicBean.class);
                List<RecommendCyclePicBean.PicBean> picBeen = recommendCyclePicBean.getPic();
//                for (int i = 0; i < picBeen.size(); i++) {
//                    Picasso.with(context).load(picBeen.get(i).getRandpic()).into(imageView);
//                    imageViews.add(imageView);
//                }
                recommendViewAdapter.setImageViews(imageViews);
                viewPager.setAdapter(recommendViewAdapter);
            }

            @Override
            public void failure() {

            }
        });
    }
}
