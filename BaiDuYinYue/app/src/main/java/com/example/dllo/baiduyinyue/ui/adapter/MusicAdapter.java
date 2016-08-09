package com.example.dllo.baiduyinyue.ui.adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.ui.application.MyApp;

import java.util.List;

/**
 * Created by Limiao on 16/7/13.
 * 乐库的adapter
 */
public class MusicAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private Resources resources = MyApp.getContext().getResources();
    private String[] titles = resources.getStringArray(R.array.music_tab_text);

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
