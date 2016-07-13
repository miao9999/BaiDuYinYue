package com.example.dllo.baiduyinyue.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.ui.adapter.MusicAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment.MvFragment;
import com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment.RaidoFragment;
import com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment.RecommendFragment;
import com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment.TopFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limiao on 16/7/11.
 */
public class MusicFragment extends AbsBaseFragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragments;
    private MusicAdapter musicAdapter;
    @Override
    protected int setLayout() {
        return R.layout.fragment_music;
    }

    @Override
    protected void initView() {
        viewPager = findView(R.id.music_fragment_viewpager);
        tabLayout = findView(R.id.music_fragment_tab_layout);

    }

    @Override
    protected void initData() {
        musicAdapter = new MusicAdapter(getChildFragmentManager());
        initFragment();
        musicAdapter.setFragments(fragments);
        viewPager.setAdapter(musicAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new RecommendFragment());
        fragments.add(new TopFragment());
        fragments.add(new SingingFragment());
        fragments.add(new RaidoFragment());
        fragments.add(new MvFragment());
    }
}
