package com.example.dllo.baiduyinyue.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.T;

/**
 * Created by Limiao on 16/7/11.
 * 我的 的fragment
 */
public class MineFragment extends AbsBaseFragment implements View.OnClickListener {
    private LinearLayout playLl;
    @Override
    protected int setLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        playLl = findView(R.id.mine_local);
    }

    @Override
    protected void initData() {
        playLl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_local:
                T.shortMsg("local music");
                if (onSkipFragment != null){
                    onSkipFragment.toFragment(Contant.LOCAL_MUSIC_FRAGMENT);
                }
                break;
        }
    }
}
