package com.example.dllo.baiduyinyue.ui.fragment.top_child_fragment;

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
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.EventBean;
import com.example.dllo.baiduyinyue.mode.bean.TopDetailBean;
import com.example.dllo.baiduyinyue.mode.net.NetValues;
import com.example.dllo.baiduyinyue.ui.adapter.TopDetailAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.AbsBaseFragment;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.example.dllo.baiduyinyue.views.MyListView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
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
    private ImageView titleBgIv, playIv;
    private TopDetailAdapter topDetailAdapter;
    private EventBus eventBus;
    private ImageView shareIv;


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
        shareIv = findView(R.id.top_detail_fragment_share_iv);


    }

    @Override
    protected void initData() {
        ShareSDK.initSDK(context,"sharesdk的appkey");
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
                        if (verticalOffset <= -headLayout.getHeight() / 2) {
                            headLayout.setVisibility(View.INVISIBLE);
//                            playIv.setVisibility(View.INVISIBLE);
                            collapsingToolbarLayout.setTitle(topDetailBean.getBillboard().getName());
                        } else {
                            headLayout.setVisibility(View.VISIBLE);
                            collapsingToolbarLayout.setTitle("");
//                            playIv.setVisibility(View.VISIBLE);
                        }
                    }
                });
                // 设置模糊效果
                Glide.with(context).load(topDetailBean.getBillboard().getPic_s210()).bitmapTransform(new BlurTransformation(context, 10)).into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        headLayout.setBackground(resource);
                    }
                });

            }

            @Override
            public void failure() {
                L.e("topDetailFragment", "解析失败");
            }
        });


        // 为listView设置点击事件
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TopDetailBean.SongListBean songListBean = (TopDetailBean.SongListBean) parent.getItemAtPosition(position);
                EventBean eventBean = new EventBean();
                String songUrl = NetValues.SONG_ULR.replace("参数", songListBean.getSong_id());
                eventBean.setSongUrl(songUrl);
                eventBean.setType(Contant.TOP_DETAIL_TYPE);
                eventBean.setTopDetailBean(topDetailBean);
                eventBean.setCurrentIndex(position);
                eventBus.post(eventBean);
                L.e(songUrl, " songurl");
            }
        });

        shareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });

    }

    private void showShare() {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(context);
    }
}
