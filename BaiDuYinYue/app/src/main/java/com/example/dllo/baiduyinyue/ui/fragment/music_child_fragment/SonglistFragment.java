package com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.SonglistBean;
import com.example.dllo.baiduyinyue.ui.adapter.SongListAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.AbsBaseFragment;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.mode.net.NetValues;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.example.dllo.baiduyinyue.views.MyGridView;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Limiao on 16/7/13.
 * 乐库——歌单的fragment
 */
public class SonglistFragment extends AbsBaseFragment {
    private MyGridView songlistGridView;
    private SongListAdapter songListAdapter;
    private SonglistBean  songlistBean;
    private List<SonglistBean.SongListBean> songListBeen;

    @Override
    protected int setLayout() {
        return R.layout.fragment_child_music_songlist;
    }

    @Override
    protected void initView() {
        songlistGridView = findView(R.id.songlist_fragment_gv);


    }

    @Override
    protected void initData() {
        songListAdapter = new SongListAdapter(context);
        // 解析数据
        VolleySingle.getInstance(context).startRequest(NetValues.SONGLIST_URL, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                Gson gson = new Gson();
                songlistBean = gson.fromJson(url,SonglistBean.class);
                songListBeen = songlistBean.getContent();
                L.e("songlistFragment","解析成功");
                // 设置数据
                songListAdapter.setSongListBeen(songListBeen);
                songlistGridView.setAdapter(songListAdapter);
            }

            @Override
            public void failure() {
                L.e("songlistFragment","解析失败");
            }
        });


    }
}
