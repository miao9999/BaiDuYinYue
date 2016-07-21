package com.example.dllo.baiduyinyue.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.ProgressBar;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.net.NetValues;
import com.example.dllo.baiduyinyue.ui.fragment.MainFragment;
import com.example.dllo.baiduyinyue.ui.fragment.SearchFragment;
import com.example.dllo.baiduyinyue.ui.fragment.mine_child_fragment.LocalMusicFragment;
import com.example.dllo.baiduyinyue.ui.fragment.recommend_chlid_fragment.RecommendSortFragment;
import com.example.dllo.baiduyinyue.ui.fragment.top_child_fragment.TopDetailFragment;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.utils.OnSkipFragment;

public class MainActivity extends AbsBaseActivity implements OnSkipFragment {


    private ProgressBar mProgress;
    private MainFragment mainFragment;
    private FragmentManager manager;
    private SearchFragment searchFragment;
    private LocalMusicFragment localMusicFragment;
    private RecommendSortFragment recommendSortFragment;
    private TopDetailFragment topDetailFragment;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mProgress = findView(R.id.main_progressbar);
//        mainFragment.setOnSkipFragment(this);
        initFragment();
    }


    @Override
    protected void initData() {

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_content_frame, mainFragment);
        transaction.addToBackStack("mainFragment");
        transaction.commit();
    }

    @Override
    public void toFragment(int type,String url) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Bundle bundle = new Bundle();
        switch (type) {
            case Contant.SEARCH_FRAGMENT:
                transaction.replace(R.id.main_content_frame, searchFragment).addToBackStack(null);
                break;
            case Contant.MAIN_FRAGMENT:
                transaction.replace(R.id.main_content_frame, mainFragment).addToBackStack(null);
                break;
            case Contant.LOCAL_MUSIC_FRAGMENT:
                transaction.replace(R.id.main_content_frame, localMusicFragment).addToBackStack(null);
                break;
            case Contant.RECOMMEND_SORT_FRAGMENT:
                transaction.replace(R.id.main_content_frame, recommendSortFragment).addToBackStack(null);
                break;
            case Contant.TOP_DETAIL_FRAGMENT:
                bundle.putString(NetValues.TOP_DETAIL_URL,url);
                topDetailFragment.setArguments(bundle);
                transaction.replace(R.id.main_content_frame,topDetailFragment).addToBackStack(null);
                L.e("mainaty",url);
        }
        transaction.commit();
    }

    /**
     * 监听返回键,当只有一个fragment在栈中时,按返回键直接finishMainActivity
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        super.onKeyDown(keyCode, event);
        return true;
    }

    private void initFragment() {
        mainFragment = new MainFragment();
        searchFragment = new SearchFragment();
        localMusicFragment = new LocalMusicFragment();
        recommendSortFragment = new RecommendSortFragment();
        topDetailFragment = new TopDetailFragment();
    }
}
