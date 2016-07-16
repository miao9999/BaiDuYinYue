package com.example.dllo.baiduyinyue.ui.activity;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.ui.adapter.MainAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.LiveFragment;
import com.example.dllo.baiduyinyue.ui.fragment.MineFragment;
import com.example.dllo.baiduyinyue.ui.fragment.MusicFragment;
import com.example.dllo.baiduyinyue.ui.fragment.KSingingFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AbsBaseActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainAdapter mainAdapter;
    private List<Fragment> fragments;
    private ImageView searchIv,hostIv;

    ////////////////////////////
    private static final int PROGRESS = 0x1;

    private ProgressBar mProgress;
    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();
/////////////////////////////////////
    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        tabLayout = findView(R.id.main_tab_layout);
        viewPager = findView(R.id.main_viewpager);
        //////////////
        mProgress = findView(R.id.main_progressbar);

        searchIv = findView(R.id.search_iv);
        hostIv = findView(R.id.main_user_iv);

    }

    @Override
    protected void initData() {
        mainAdapter = new MainAdapter(getSupportFragmentManager());
        initFragment();
        mainAdapter.setFragments(fragments);
        viewPager.setAdapter(mainAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // search和host的点击事件
        searchIv.setOnClickListener(this);
        hostIv.setOnClickListener(this);
        ////////////////////////////////////


        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus ++;

                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                }
            }
        }).start();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new MineFragment());
        fragments.add(new MusicFragment());
        fragments.add(new KSingingFragment());
        fragments.add(new LiveFragment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // search的点击事件
            case R.id.search_iv:
                
                break;
            // host的点击事件
            case R.id.main_user_iv:
                break;
        }
    }
}
