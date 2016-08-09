package com.example.dllo.baiduyinyue.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.utils.Contant;


/**
 * Created by Limiao on 16/7/11.
 * 我的 fragment
 */
public class MineFragment extends AbsBaseFragment implements View.OnClickListener {
    private LinearLayout playLl;
    private RelativeLayout likeSongRl;
    @Override
    protected int setLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        playLl = findView(R.id.mine_local);
        likeSongRl = findView(R.id.mine_like);
        playLl.setOnClickListener(this);
        likeSongRl.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 跳转到本地音乐fragment
            case R.id.mine_local:
                if (onSkipFragment != null) {
                    onSkipFragment.toFragment(Contant.LOCAL_MUSIC_FRAGMENT, null);
                }
                break;
            // 跳转到喜欢的单曲
            case R.id.mine_like:
                if (onSkipFragment != null){
                    onSkipFragment.toFragment(Contant.LIKE_SONG_FRAGMENT,null);
                }
                break;
        }
    }


}
