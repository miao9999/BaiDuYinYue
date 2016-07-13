package com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.toolbox.Volley;
import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.SongTopBean;
import com.example.dllo.baiduyinyue.ui.adapter.SongtopRvAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.AbsBaseFragment;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.utils.Values;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Limiao on 16/7/13.
 */
public class TopFragment extends AbsBaseFragment {
    private RecyclerView recyclerView;
    private SongtopRvAdapter songtopRvAdapter;
    @Override
    protected int setLayout() {
        return R.layout.fragment_child_music_song_top;
    }

    @Override
    protected void initView() {
        recyclerView = findView(R.id.top_fragment_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    protected void initData() {
        songtopRvAdapter = new SongtopRvAdapter(context);

        VolleySingle.getInstance(context).startRequest(Values.TOP_URL, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                Gson gson = new Gson();
                SongTopBean songTopBean = gson.fromJson(url,SongTopBean.class);
                List<SongTopBean.ContentBean> contentBeen = songTopBean.getContent();
                songtopRvAdapter.setContentBeen(contentBeen);
                L.e("size",contentBeen.size() + "");
                recyclerView.setAdapter(songtopRvAdapter);
            }

            @Override
            public void failure() {
                L.e("failure");
            }
        });
    }
}
