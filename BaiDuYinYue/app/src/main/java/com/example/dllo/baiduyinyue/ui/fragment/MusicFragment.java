package com.example.dllo.baiduyinyue.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.ui.adapter.MusicAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment.MvFragment;
import com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment.RadioFragment;
import com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment.RecommendFragment;
import com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment.SonglistFragment;
import com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment.TopFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limiao on 16/7/11.
 * 乐库 的fragment
 */
public class MusicFragment extends AbsBaseFragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragments;
    private MusicAdapter musicAdapter;

    private RecommendFragment recommendFragment;

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
        recommendFragment = new RecommendFragment();
//        vp = recommendFragment.getViewPager();
        //
//        // 把两个vp的touch事件统一
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (viewPager.getCurrentItem() == 0) {
//
//                    return new ViewPager(context).dispatchTouchEvent(event);
//                }
//                return true;
//            }
//        });

        musicAdapter = new MusicAdapter(getChildFragmentManager());
        initFragment();
        musicAdapter.setFragments(fragments);
        viewPager.setAdapter(musicAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(recommendFragment);
        fragments.add(new TopFragment());
        fragments.add(new SonglistFragment());
        fragments.add(new RadioFragment());
        fragments.add(new MvFragment());
    }
}
