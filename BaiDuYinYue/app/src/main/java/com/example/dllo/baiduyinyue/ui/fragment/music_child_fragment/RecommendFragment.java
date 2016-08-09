package com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment;

import android.support.v4.view.ViewPager;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.RecommendBean;
import com.example.dllo.baiduyinyue.ui.adapter.recommend.SongListRecommendAdapter;
import com.example.dllo.baiduyinyue.ui.adapter.recommend.HotMvAdapter;
import com.example.dllo.baiduyinyue.ui.adapter.recommend.HotSealAdapter;
import com.example.dllo.baiduyinyue.ui.adapter.recommend.NewSongAdapter;
import com.example.dllo.baiduyinyue.ui.adapter.recommend.RadioPlayAdapter;
import com.example.dllo.baiduyinyue.ui.adapter.RecommendViewAdapter;
import com.example.dllo.baiduyinyue.ui.adapter.recommend.SortGridViewAdapter;
import com.example.dllo.baiduyinyue.ui.adapter.recommend.SpecialAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.AbsBaseFragment;
import com.example.dllo.baiduyinyue.mode.net.NetValues;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.example.dllo.baiduyinyue.views.MyGridView;
import com.example.dllo.baiduyinyue.views.MyListView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limiao on 16/7/13.
 * 乐库--推荐的fragment
 */
public class RecommendFragment extends AbsBaseFragment {
    private List<ImageView> imageViews;
    private ViewPager viewPager;
    private MyGridView songlistRecommendGridView, imgBtnGridView, newSongGridView, hotSealGridView, hotMvGridView, radioPlayGridView;
    private SongListRecommendAdapter songListRecommendAdapter;
    private SortGridViewAdapter sortGridViewAdapter;
    private NewSongAdapter newSongAdapter;
    private RecommendViewAdapter recommendViewAdapter;
    private RecommendBean recommendBean;
    private HotSealAdapter hotSealAdapter;
    private HotMvAdapter hotMvAdapter;
    private RadioPlayAdapter radioPlayAdapter;
    private MyListView specialListView;
    private SpecialAdapter specialAdapter;


    @Override
    protected int setLayout() {
        return R.layout.fragment_child_music_recommend;

    }

    @Override
    protected void initView() {
        viewPager = findView(R.id.recommend_fragment_viewpager);
        imgBtnGridView = findView(R.id.sort_grid_view);
        songlistRecommendGridView = findView(R.id.songlist_recommend_grid_view);
        newSongGridView = findView(R.id.newsong_grid_view);
        hotSealGridView = findView(R.id.hotseal_grid_view);
        hotMvGridView = findView(R.id.hotmv_grid_view);
        radioPlayGridView = findView(R.id.radio_play_grid_view);
        specialListView = findView(R.id.special_listview);

    }

    @Override
    protected void initData() {
        imageViews = new ArrayList<>();
        recommendViewAdapter = new RecommendViewAdapter(context);
        songListRecommendAdapter = new SongListRecommendAdapter(context);
        songlistRecommendGridView.setFocusable(false);
        sortGridViewAdapter = new SortGridViewAdapter(context);
        newSongAdapter = new NewSongAdapter(context);
        hotSealAdapter = new HotSealAdapter(context);
        hotMvAdapter = new HotMvAdapter(context);
        radioPlayAdapter = new RadioPlayAdapter(context);
        specialAdapter = new SpecialAdapter(context);


        // 解析数据
        VolleySingle.getInstance(context).startRequest(NetValues.RECOMMEND_URL, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                Gson gson = new Gson();
                recommendBean = gson.fromJson(url, RecommendBean.class);
                // 轮播图
                setCyclePic();
                // 分类按钮
                setImgBtn();
                // 歌单推荐
                setSonglistRecommend();
                // 新碟上架
                setNewSong();
                // 热销专辑
                setHotSeal();
                // 最热MV推荐
                setHotMv();
                // 乐播节目
                setRadioPlay();
                // 专栏
                setSpecial();

                // 为歌曲分类的gv设置监听
                imgBtnGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                if (onSkipFragment != null) {
                                    onSkipFragment.toFragment(Contant.RECOMMEND_SORT_FRAGMENT, null);
                                }
                        }
                    }
                });

            }

            @Override
            public void failure() {

            }
        });
    }


    /**
     * 轮播图
     */
    private void setCyclePic() {
        List<RecommendBean.ResultBean.FocusBean.CyclePicBean> cyclePicBeen = recommendBean.getResult().getFocus().getResult();
        for (int i = 0; i < cyclePicBeen.size(); i++) {
            ImageView imageView = new ImageView(context);
            String imgUrl = cyclePicBeen.get(i).getRandpic();
            Picasso.with(context).load(imgUrl).into(imageView);
            imageViews.add(imageView);
        }
        recommendViewAdapter.setCyclePicBeen(cyclePicBeen);
        viewPager.setAdapter(recommendViewAdapter);
    }

    /**
     * 分类按钮
     */
    private void setImgBtn() {
        List<RecommendBean.ResultBean.EntryBean.ImgBtnBean> imgBtnBeen = recommendBean.getResult().getEntry().getResult();
        sortGridViewAdapter.setImgBtnBeen(imgBtnBeen);
        imgBtnGridView.setAdapter(sortGridViewAdapter);
    }


    /**
     * 歌单推荐
     */
    private void setSonglistRecommend() {
        List<RecommendBean.ResultBean.DiyBean.SonglistRecommendstBean> songlistRecommendstBeen = recommendBean.getResult().getDiy().getResult();
        songListRecommendAdapter.setSonglistRecommendstBeen(songlistRecommendstBeen);
        songlistRecommendGridView.setAdapter(songListRecommendAdapter);
    }

    /**
     * 新碟上架
     */
    private void setNewSong() {
        List<RecommendBean.ResultBean.Mix1Bean.NewSongBean> newSongBeen = recommendBean.getResult().getMix_1().getResult();
        newSongAdapter.setNewSongBeen(newSongBeen);
        newSongGridView.setAdapter(newSongAdapter);
    }

    /**
     * 热销专辑
     */
    private void setHotSeal() {
        List<RecommendBean.ResultBean.Mix22Bean.HotSealBean> hotSealBeen = recommendBean.getResult().getMix_22().getResult();
        hotSealAdapter.setHotSealBeen(hotSealBeen);
        hotSealGridView.setAdapter(hotSealAdapter);
    }

    /**
     * 最热MV推荐
     */
    private void setHotMv() {
        List<RecommendBean.ResultBean.Mix5Bean.HotMvBean> hotMvBeen = recommendBean.getResult().getMix_5().getResult();
        hotMvAdapter.setHotMvBeen(hotMvBeen);
        hotMvGridView.setAdapter(hotMvAdapter);
    }

    /**
     * 乐播节目
     */
    private void setRadioPlay() {
        List<RecommendBean.ResultBean.RadioBean.MusicPlayBean> musicPlayBeen = recommendBean.getResult().getRadio().getResult();
        radioPlayAdapter.setMusicPlayBeen(musicPlayBeen);
        radioPlayGridView.setAdapter(radioPlayAdapter);
    }

    /**
     * 专栏
     */
    private void setSpecial() {
        List<RecommendBean.ResultBean.Mod7Bean.SpecialBean> specialBeen = recommendBean.getResult().getMod_7().getResult();
        specialAdapter.setSpecialBeen(specialBeen);
        specialListView.setAdapter(specialAdapter);
    }
}
