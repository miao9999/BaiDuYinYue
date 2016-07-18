package com.example.dllo.baiduyinyue.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ProgressBar;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.ui.fragment.MainFragment;
import com.example.dllo.baiduyinyue.ui.fragment.SearchFragment;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.OnSkipFragment;

public class MainActivity extends AbsBaseActivity implements OnSkipFragment {


    private ProgressBar mProgress;
    private MainFragment mainFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private SearchFragment searchFragment;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mProgress = findView(R.id.main_progressbar);
        mainFragment = new MainFragment();
        mainFragment.setOnSkipFragment(this);
        searchFragment = new SearchFragment();
    }

    @Override
    protected void initData() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.hide(new MainFragment());
        transaction.replace(R.id.main_content_frame, mainFragment);
//        transaction.addToBackStack("mainFragment");
        transaction.commit();
    }

    @Override
    public void toFragment(int type) {
        FragmentTransaction transaction = manager.beginTransaction();
        switch (type) {
            case Contant.SEARCH_FRAGMENT:
                transaction.replace(R.id.main_content_frame, searchFragment);
                transaction.addToBackStack("searchFragment");
                break;
            case Contant.MAIN_FRAGMENT:
                transaction.replace(R.id.main_content_frame,mainFragment);
                break;
        }
        transaction.commit();
    }


}
