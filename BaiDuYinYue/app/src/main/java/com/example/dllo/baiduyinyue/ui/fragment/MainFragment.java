package com.example.dllo.baiduyinyue.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.ui.activity.UserActivity;
import com.example.dllo.baiduyinyue.ui.adapter.MainAdapter;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.utils.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limiao on 16/7/16.
 */
public class MainFragment extends AbsBaseFragment implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainAdapter mainAdapter;
    private List<Fragment> fragments;
    private ImageView searchIv, hostIv;
    private MineFragment mineFragment;


    @Override
    protected int setLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {
        tabLayout = findView(R.id.main_tab_layout);
        viewPager = findView(R.id.main_viewpager);
        searchIv = findView(R.id.main_search_iv);
        hostIv = findView(R.id.main_user_iv);

    }

    @Override
    protected void initData() {
        mainAdapter = new MainAdapter(getChildFragmentManager());
        mineFragment = new MineFragment();
        initFragment();
        mainAdapter.setFragments(fragments);
        viewPager.setAdapter(mainAdapter);
        tabLayout.setupWithViewPager(viewPager);


        // search和host的点击事件
        searchIv.setOnClickListener(this);
        hostIv.setOnClickListener(this);

    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(mineFragment);
        fragments.add(new MusicFragment());
        fragments.add(new KSingingFragment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // search的点击事件
            case R.id.main_search_iv:
                T.shortMsg("search");
                if (onSkipFragment != null) {
                    onSkipFragment.toFragment(Contant.SEARCH_FRAGMENT, null);
                }

                break;
            // host的点击事件
            case R.id.main_user_iv:
                T.longMsg("user");
                goTo(context, UserActivity.class);
                break;

        }
    }

}
