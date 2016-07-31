package com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.MvBean;
import com.example.dllo.baiduyinyue.mode.bean.MvDetailBean;
import com.example.dllo.baiduyinyue.ui.activity.MvActivity;
import com.example.dllo.baiduyinyue.ui.adapter.recommend.MvAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.AbsBaseFragment;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.mode.net.NetValues;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.example.dllo.baiduyinyue.views.MyGridView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

/**
 * Created by Limiao on 16/7/13.
 * 乐库——MV 的fragment
 */
public class MvFragment extends AbsBaseFragment implements View.OnClickListener {
    private MvAdapter mvAdapter;
    private MyGridView mvGridView;
    private MvBean mvBean;
    private List<MvBean.ResultBean.MvListBean> mvListBeen, mvHotListBeen;
    private TextView newTv, hotTv;
    private MediaPlayer player;
    private MvDetailBean mvDetailBean;
    private boolean newTvIsCheck = false;// 监听newTv是否选中,默认为false
    private boolean hotTvIsCheck = false;// 监听hotTv是否选中,默认为false

    @Override
    protected int setLayout() {
        return R.layout.fragment_child_music_mv;
    }

    @Override
    protected void initView() {
        mvGridView = findView(R.id.mv_fragment_gv);
        hotTv = findView(R.id.mv_fragment_hot_tv);
        newTv = findView(R.id.mv_fragment_new_tv);
        hotTv.setOnClickListener(this);
        newTv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        player = new MediaPlayer();
        mvAdapter = new MvAdapter(context);
        // 解析数据--最新
        VolleySingle.getInstance(context).startRequest(NetValues.MV_URL, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                Gson gson = new Gson();
                mvBean = gson.fromJson(url, MvBean.class);
                mvListBeen = mvBean.getResult().getMv_list();
                L.e("mvFragment", "解析成功");
                // 默认打开时显示最新
                mvAdapter.setMvListBeen(mvListBeen);
                mvGridView.setAdapter(mvAdapter);
            }

            @Override
            public void failure() {
                L.e("mvFragment", "解析失败");
            }
        });


        // 解析数据---最热
        VolleySingle.getInstance(context).startRequest(NetValues.MV_HOT_URL, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                L.e("mvFragment", Contant.SUCCESS);
                Gson gson = new Gson();
                mvBean = gson.fromJson(url, MvBean.class);
                mvHotListBeen = mvBean.getResult().getMv_list();
            }

            @Override
            public void failure() {
                L.e("mvFragment", Contant.FAILURE);
            }
        });
        // gridView的点击事件,实现播放视频
        mvGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mvId = mvBean.getResult().getMv_list().get(position).getMv_id();
                // 最新的视频
                    String url = NetValues.MV_DETAIL_URL.replace("参数", mvId);
                    VolleySingle.getInstance(context).startRequest(url, new VolleySingle.VolleyResult() {
                        @Override
                        public void success(String url) {
                            L.e("mvFragment", Contant.SUCCESS);
                            Gson gson = new Gson();
                            mvDetailBean = gson.fromJson(url, MvDetailBean.class);
                            Intent toMvAtyIntent = new Intent(context, MvActivity.class);
//                            Log.d("MvFragment", mvDetailBean.getResult().getFiles().getValue21().getFile_link());
                            toMvAtyIntent.putExtra(Contant.MV_INTNET_URL, mvDetailBean.getResult().getFiles().getValue21().getFile_link());
                            context.startActivity(toMvAtyIntent);
                        }
                        @Override
                        public void failure() {
                            L.e("mvFragment", Contant.FAILURE);
                        }
                    });
                }
        });

    }

    /**
     * 最新最热切换
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mv_fragment_new_tv:
                mvAdapter.setMvListBeen(mvListBeen);
                mvGridView.setAdapter(mvAdapter);
                break;
            case R.id.mv_fragment_hot_tv:
                mvAdapter.setMvListBeen(mvHotListBeen);
                mvGridView.setAdapter(mvAdapter);
                break;
        }
    }


}
