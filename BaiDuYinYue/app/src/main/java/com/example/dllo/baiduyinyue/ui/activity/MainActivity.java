package com.example.dllo.baiduyinyue.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.ui.adapter.ViewPagerAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.LiveFragment;
import com.example.dllo.baiduyinyue.ui.fragment.MineFragment;
import com.example.dllo.baiduyinyue.ui.fragment.MusicFragment;
import com.example.dllo.baiduyinyue.ui.fragment.SingingFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AbsBaseActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragments;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        tabLayout = findView(R.id.main_tab_layout);
        viewPager = findView(R.id.main_viewpager);

    }

    @Override
    protected void initData() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        initFragment();
        viewPagerAdapter.setFragments(fragments);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new MineFragment());
        fragments.add(new MusicFragment());
        fragments.add(new SingingFragment());
        fragments.add(new LiveFragment());
    }
}
