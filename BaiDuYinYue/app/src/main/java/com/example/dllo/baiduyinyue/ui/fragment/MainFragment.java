package com.example.dllo.baiduyinyue.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.ui.activity.UserActivity;
import com.example.dllo.baiduyinyue.ui.adapter.MainAdapter;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limiao on 16/7/16.
 * 首页的fragment
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
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String userIcon = sharedPreferences.getString("usericon", "");
        if (TextUtils.isEmpty(userIcon)) {
            hostIv.setImageResource(R.mipmap.user);
        } else {
            Picasso.with(context).load(userIcon).error(R.mipmap.user).into(hostIv);
        }
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
                if (onSkipFragment != null) {
                    onSkipFragment.toFragment(Contant.SEARCH_FRAGMENT, null);
                }
                break;
            // host的点击事件
            case R.id.main_user_iv:
                goTo(context, UserActivity.class);
                break;

        }
    }

}
