package com.example.dllo.baiduyinyue.ui.fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.KSingListBean;
import com.example.dllo.baiduyinyue.mode.bean.KSingingCyclePicBean;
import com.example.dllo.baiduyinyue.ui.adapter.KSingListViewAdapter;
import com.example.dllo.baiduyinyue.ui.adapter.KSingViewPagerAdapter;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.mode.net.NetValues;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.example.dllo.baiduyinyue.views.MyListView;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Limiao on 16/7/11.
 * K歌的fragment
 */
public class KSingingFragment extends AbsBaseFragment {
    private KSingingCyclePicBean kSingingCyclePicBean;
    private List<KSingingCyclePicBean.KSingBean> kSingBeen ;
    private ViewPager viewPager;
    private KSingViewPagerAdapter kSingViewPagerAdapter;
    private MyListView listView;
    private KSingListViewAdapter kSingListViewAdapter;
    private KSingListBean kSingListBean;
    private List<KSingListBean.ResultBean.ItemsBean> itemsBeen;
    @Override
    protected int setLayout() {
        return R.layout.fragment_singing;
    }

    @Override
    protected void initView() {
        listView = findView(R.id.k_sing_fragment_lv);
        viewPager = findView(R.id.k_sing_fragment_head_vp);

    }

    @Override
    protected void initData() {
        kSingListViewAdapter = new KSingListViewAdapter(context);
        kSingViewPagerAdapter = new KSingViewPagerAdapter(context);

        // 解析K歌数据
        VolleySingle.getInstance(context).startRequest(NetValues.K_SING_URL, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                Gson gson = new Gson();
                kSingListBean = gson.fromJson(url,KSingListBean.class);
                itemsBeen = kSingListBean.getResult().getItems();
                L.e("aaaaaa",itemsBeen.size() + " ");
                L.e("ksingFragment","解析成功");
            }

            @Override
            public void failure() {
                L.e("ksingFragment","解析失败");
            }
        });

        // 设置数据
        kSingListViewAdapter.setItemsBeen(itemsBeen);
        listView.setAdapter(kSingListViewAdapter);

        // 解析数据
        VolleySingle.getInstance(context).startRequest(NetValues.K_CYCLE_PIC_URL, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                Gson gson = new Gson();
                kSingingCyclePicBean = gson.fromJson(url,KSingingCyclePicBean.class);
                kSingBeen = kSingingCyclePicBean.getResult();
                L.e("ksingFragment","解析成功");
            }

            @Override
            public void failure() {
                L.e("ksingFragment","解析失败");
            }
        });

        // 设置数据--轮播图


        kSingViewPagerAdapter.setkSingBeen(kSingBeen);
        viewPager.setAdapter(kSingViewPagerAdapter);



    }
}
