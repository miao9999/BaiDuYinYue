package com.example.dllo.baiduyinyue.ui.fragment.mine_child_fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.ui.adapter.localmusic.LocalMusicVpAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.AbsBaseFragment;
import com.example.dllo.baiduyinyue.utils.Contant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limiao on 16/7/18.
 * 我的--本地音乐的fragment
 */
public class LocalMusicFragment extends AbsBaseFragment implements View.OnClickListener {
    private ViewPager viewPager;
    private LocalMusicVpAdapter localMusicVpAdapter ;
    private TabLayout tabLayout;
    private List<Fragment> fragments;
    private TextView titleBackTv;


    @Override
    protected int setLayout() {
        return R.layout.fragment_child_mine_local;

    }

    @Override
    protected void initView() {
        viewPager = findView(R.id.local_music_viewpager);
        tabLayout = findView(R.id.local_music_tab_layout);
        titleBackTv = findView(R.id.local_music_title_tv);

    }

    @Override
    protected void initData() {
        // 设置tabLayout
        localMusicVpAdapter = new LocalMusicVpAdapter(getChildFragmentManager());
        initFragment();
        localMusicVpAdapter.setFragments(fragments);
        viewPager.setAdapter(localMusicVpAdapter);
        tabLayout.setupWithViewPager(viewPager);
        // 为返回设置监听
        titleBackTv.setOnClickListener(this);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new LocalMusicSongFragment());
        fragments.add(new LocalMusicSongFragment());
        fragments.add(new LocalMusicSongFragment());
        fragments.add(new LocalMusicSongFragment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.local_music_title_tv:
                if (onSkipFragment != null){
                    onSkipFragment.toFragment(Contant.MAIN_FRAGMENT,null);
                }
                break;
        }
    }
}
