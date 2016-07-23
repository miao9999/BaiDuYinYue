package com.example.dllo.baiduyinyue.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.utils.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Limiao on 16/7/11.
 * 我的 的fragment
 */
public class MineFragment extends AbsBaseFragment implements View.OnClickListener {
    private LinearLayout playLl;
    private TextView songNumTv;

    @Override
    protected int setLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        playLl = findView(R.id.mine_local);
        songNumTv = findView(R.id.main_content_song_num_tv);
    }

    @Override
    protected void initData() {
        playLl.setOnClickListener(this);
        EventBus.getDefault().register(this);
        L.e("evenbus");


    }
    // 接收从LocalMusicSongFragment传来的歌曲数量
    @Subscribe
    public void onReceiver(Integer songNum){
        L.e("MineFragment",songNum + "333333");
        songNumTv.setText(String.valueOf(songNum));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_local:
                T.shortMsg("local music");
                if (onSkipFragment != null) {
                    onSkipFragment.toFragment(Contant.LOCAL_MUSIC_FRAGMENT, null);
                }
                break;
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
