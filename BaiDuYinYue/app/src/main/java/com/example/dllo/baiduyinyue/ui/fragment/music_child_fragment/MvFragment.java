package com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.MvBean;
import com.example.dllo.baiduyinyue.ui.adapter.recommend.MvAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.AbsBaseFragment;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.mode.net.NetValues;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.example.dllo.baiduyinyue.views.MyGridView;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Limiao on 16/7/13.
 * 乐库——MV 的fragment
 */
public class MvFragment extends AbsBaseFragment {
    private MvAdapter mvAdapter;
    private MyGridView mvGridView;
    private MvBean mvBean;
    private List<MvBean.ResultBean.MvListBean> mvListBeen;

    @Override
    protected int setLayout() {
        return R.layout.fragment_child_music_mv;
    }

    @Override
    protected void initView() {
        mvGridView = findView(R.id.mv_fragment_gv);
    }

    @Override
    protected void initData() {
        mvAdapter = new MvAdapter(context);
        // 解析数据
        VolleySingle.getInstance(context).startRequest(NetValues.MV_URL, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                Gson gson = new Gson();
                mvBean = gson.fromJson(url,MvBean.class);
                mvListBeen = mvBean.getResult().getMv_list();
                L.e("mvFragment","解析成功");
            }

            @Override
            public void failure() {
                L.e("mvFragment","解析失败");
            }
        });
        // 设置数据
        mvAdapter.setMvListBeen(mvListBeen);
        mvGridView.setAdapter(mvAdapter);

    }
}
