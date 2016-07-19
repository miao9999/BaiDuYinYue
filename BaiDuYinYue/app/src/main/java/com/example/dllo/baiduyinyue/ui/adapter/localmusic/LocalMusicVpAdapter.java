package com.example.dllo.baiduyinyue.ui.adapter.localmusic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Limiao on 16/7/18.
 * 我的--本地音乐的Adapter
 */
public class LocalMusicVpAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private String [] titles = {"歌曲","文件夹","歌手","专辑"};
    public LocalMusicVpAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments == null ? null: fragments.get(position);
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
