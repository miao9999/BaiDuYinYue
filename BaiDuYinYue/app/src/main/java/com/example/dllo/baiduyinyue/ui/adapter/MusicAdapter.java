package com.example.dllo.baiduyinyue.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Limiao on 16/7/13.
 * 乐库的adapter
 */
public class MusicAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private String[] titles = {"推荐", "排行", "歌单", "电台", "MV"};

    public MusicAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments == null ? null : fragments.get(position);
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
