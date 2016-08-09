package com.example.dllo.baiduyinyue.ui.adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.ui.application.MyApp;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Limiao on 16/7/12.
 * main_activity的Adapter
 */
public class MainAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private Resources resources = MyApp.getContext().getResources();
    private String [] titles = resources.getStringArray(R.array.main_tab_text);

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
