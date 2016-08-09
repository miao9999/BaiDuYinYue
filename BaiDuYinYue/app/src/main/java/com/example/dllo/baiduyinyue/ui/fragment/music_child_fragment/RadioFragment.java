package com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.RecommendBean;
import com.example.dllo.baiduyinyue.ui.adapter.RadioAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.AbsBaseFragment;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.mode.net.NetValues;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.example.dllo.baiduyinyue.views.MyGridView;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Limiao on 16/7/13.
 * 乐库——电台的fragment
 */
public class RadioFragment extends AbsBaseFragment {
    private List<RecommendBean.ResultBean.SceneAllBean.SceneBean.ActionBean> actionBeen;
    private MyGridView radioGridView;
    private RadioAdapter radioAdapter;
    private RecommendBean recommendBean;

    @Override
    protected int setLayout() {
        return R.layout.fragment_child_music_raido;
    }

    @Override
    protected void initView() {
        radioGridView = findView(R.id.radio_fragment_gv);
    }

    @Override
    protected void initData() {
        radioAdapter = new RadioAdapter(context);
        // 解析数据
        VolleySingle.getInstance(context).startRequest(NetValues.RECOMMEND_URL, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                Gson gson = new Gson();
                recommendBean = gson.fromJson(url,RecommendBean.class);
                actionBeen = recommendBean.getResult().getScene().getResult().getAction();

            }

            @Override
            public void failure() {
            }
        });

        // 设置数据
        radioAdapter.setActionBeen(actionBeen);
        radioGridView.setAdapter(radioAdapter);

    }
}
