package com.example.dllo.baiduyinyue.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.utils.T;


/**
 * Created by Limiao on 16/7/11.
 * 我的 fragment
 */
public class MineFragment extends AbsBaseFragment implements View.OnClickListener {
    private LinearLayout playLl;
    private TextView songNumTv,likeSongNumTv;
    private RelativeLayout likeSongRl;
    private SongNumReceiver receiver;



    @Override
    protected int setLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        playLl = findView(R.id.mine_local);
        songNumTv = findView(R.id.mine_content_song_num_tv);
        likeSongNumTv = findView(R.id.mine_like_num_tv);
        likeSongRl = findView(R.id.mine_like);
        playLl.setOnClickListener(this);
        likeSongRl.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        IntentFilter filter = new IntentFilter(Contant.SONG_NUM_RECEIVER);
        receiver = new SongNumReceiver();
        context.registerReceiver(receiver,filter);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 跳转到本地音乐fragment
            case R.id.mine_local:
                T.shortMsg("local music");
                if (onSkipFragment != null) {
                    onSkipFragment.toFragment(Contant.LOCAL_MUSIC_FRAGMENT, null);
                }
                break;
            // 跳转到喜欢的单曲
            case R.id.mine_like:
                T.shortMsg("like song");
                if (onSkipFragment != null){
                    onSkipFragment.toFragment(Contant.LIKE_SONG_FRAGMENT,null);
                }
                break;
        }
    }

    class SongNumReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int songNum = intent.getIntExtra("songNum",0);
            songNumTv.setText(String.valueOf(songNum));
            L.e("songNum",songNum + "--------");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(receiver);
    }
}
