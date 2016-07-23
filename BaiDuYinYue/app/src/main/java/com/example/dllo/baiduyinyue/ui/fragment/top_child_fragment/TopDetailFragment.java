package com.example.dllo.baiduyinyue.ui.fragment.top_child_fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.EventBean;
import com.example.dllo.baiduyinyue.mode.bean.TopDetailBean;
import com.example.dllo.baiduyinyue.mode.net.NetValues;
import com.example.dllo.baiduyinyue.ui.activity.MainActivity;
import com.example.dllo.baiduyinyue.ui.adapter.TopDetailAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.AbsBaseFragment;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.example.dllo.baiduyinyue.views.MyListView;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Limiao on 16/7/20.
 * 乐库---排行---每一条的详情的Fragment
 */
public class TopDetailFragment extends AbsBaseFragment {
    private CoordinatorLayout coordinatorLayout;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private MyListView myListView;
    private TextView dateTv;
    private RelativeLayout headLayout;// 显示日期和播放按钮的布局
    private TopDetailBean topDetailBean;
    private List<TopDetailBean.SongListBean> songListBeen;
    private TopDetailBean.BillboardBean billboardBean;
    private ImageView titleBgIv,playIv;
    private TopDetailAdapter topDetailAdapter;
    private EventBus eventBus;


    @Override
    protected int setLayout() {
        return R.layout.fragment_top_detail;
    }

    @Override
    protected void initView() {
        collapsingToolbarLayout = findView(R.id.top_detail_fragment_ctl);
        coordinatorLayout = findView(R.id.top_detail_fragment_cl);
        appBarLayout = findView(R.id.top_detail_fragment_abl);
        toolbar = findView(R.id.top_detail_fragment_abl_toolbar);
        myListView = findView(R.id.top_detail_fragment_cl_lv);
        dateTv = findView(R.id.top_detail_fragment_ctl_date_tv);
        headLayout = findView(R.id.top_detail_fragment_ctl_rl);
        titleBgIv = findView(R.id.top_detail_fragment_ctl_bg_iv);
        playIv = findView(R.id.top_detail_fragment_ctl_play_iv);


    }

    @Override
    protected void initData() {
        eventBus = EventBus.getDefault();
        topDetailAdapter = new TopDetailAdapter(context);
        // 获取传来的值
        Bundle bundle = getArguments();
        String url = (String) bundle.get(NetValues.TOP_DETAIL_URL);
        // 设置toolbar必须强转成AppcompatActivity才能调用set方法
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        // 为左上角设置一个默认的图标
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 为图标设置点击事件,点击返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        // 解析数据
        VolleySingle.getInstance(context).startRequest(url, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                L.e("topDetailFragment", "解析成功");
                Gson gson = new Gson();
                topDetailBean = gson.fromJson(url, TopDetailBean.class);
                songListBeen = topDetailBean.getSong_list();
                billboardBean = topDetailBean.getBillboard();
                L.e("songListBeen", songListBeen.size() + " -------");
                dateTv.setText(billboardBean.getUpdate_date());
                // 设置下方的listview
                topDetailAdapter.setSongListBeen(songListBeen);
                myListView.setAdapter(topDetailAdapter);
                // 设置整个标题的属性
                appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        // 当向上滑动到标题的3/4时下面的字和图片消失,标题显示
                        if (verticalOffset <= -headLayout.getHeight() / 4 * 3) {
                            headLayout.setVisibility(View.INVISIBLE);
                            playIv.setVisibility(View.INVISIBLE);
                            collapsingToolbarLayout.setTitle(topDetailBean.getBillboard().getName());
                        } else {
                            headLayout.setVisibility(View.VISIBLE);
                            collapsingToolbarLayout.setTitle("");
                            playIv.setVisibility(View.VISIBLE);
                        }
                    }
                });
                // 设置模糊效果
                Glide.with(context).load(topDetailBean.getBillboard().getPic_s210()).bitmapTransform(new BlurTransformation(context,10)).into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        headLayout.setBackground(resource);
                    }
                });

            }

            @Override
            public void failure() {
                L.e("topDetailFragment" , "解析失败");
            }
        });





        // 为listView设置点击事件
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TopDetailBean.SongListBean songListBean = songListBeen.get(position);
                EventBean eventBean = new EventBean();
                String songUrl = NetValues.SONG_ULR.replace("参数",songListBean.getSong_id());
                eventBean.setSongUrl(songUrl);
                eventBean.setType(Contant.INTERNER_TYPE);
                eventBus.post(eventBean);
            }
        });

    }
}
