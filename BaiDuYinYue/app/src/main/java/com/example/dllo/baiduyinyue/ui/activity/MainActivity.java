package com.example.dllo.baiduyinyue.ui.activity;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.ui.adapter.MainAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.LiveFragment;
import com.example.dllo.baiduyinyue.ui.fragment.MainFragment;
import com.example.dllo.baiduyinyue.ui.fragment.MineFragment;
import com.example.dllo.baiduyinyue.ui.fragment.MusicFragment;
import com.example.dllo.baiduyinyue.ui.fragment.KSingingFragment;
import com.example.dllo.baiduyinyue.ui.fragment.SearchFragment;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.utils.OnSkipFragment;
import com.example.dllo.baiduyinyue.utils.T;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AbsBaseActivity implements OnSkipFragment {


    private ProgressBar mProgress;
    private MainFragment mainFragment;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mProgress = findView(R.id.main_progressbar);
        mainFragment = new MainFragment();
        mainFragment.setOnSkipFragment(this);
    }

    @Override
    protected void initData() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(new MainFragment());
        transaction.replace(R.id.main_content_frame, new MainFragment());
        transaction.addToBackStack("searchFragment");
        transaction.commit();
    }

    @Override
    public void toFragment(int type) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (type) {
            case 0:
                transaction.replace(R.id.main_content_frame, new SearchFragment());
                break;

        }
        transaction.commit();
    }
}
