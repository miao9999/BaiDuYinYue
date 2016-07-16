package com.example.dllo.baiduyinyue.ui.fragment;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.ui.adapter.MainAdapter;
import com.example.dllo.baiduyinyue.utils.OnSkipFragment;
import com.example.dllo.baiduyinyue.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limiao on 16/7/16.
 */
public class MainFragment extends AbsBaseFragment implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainAdapter mainAdapter;
    private List<Fragment> fragments;
    private ImageView searchIv, hostIv;
//    private SwitchFragment switchFragment;
    private OnSkipFragment onSkipFragment;


    public void setOnSkipFragment(OnSkipFragment onSkipFragment) {
        this.onSkipFragment = onSkipFragment;
    }

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
        initFragment();
        mainAdapter.setFragments(fragments);
        viewPager.setAdapter(mainAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // search和host的点击事件
        searchIv.setOnClickListener(this);
        hostIv.setOnClickListener(this);

    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new MineFragment());
        fragments.add(new MusicFragment());
        fragments.add(new KSingingFragment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // search的点击事件
            case R.id.main_search_iv:
                T.shortMsg("search");
                if (onSkipFragment!=null){
                    onSkipFragment.toFragment(0);
                }

                break;
            // host的点击事件
            case R.id.main_user_iv:
                T.longMsg("user");
                break;
        }
    }


//    public interface SwitchFragment {
//        void toFragment();
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onSkipFragment = (OnSkipFragment) context;

        // 这是为了保证Activity容器实现了用以回调的接口。如果没有，它会抛出一个异常。
//        try {
//            switchFragment = (SwitchFragment) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement OnHeadlineSelectedListener");
//        }
    }
}
