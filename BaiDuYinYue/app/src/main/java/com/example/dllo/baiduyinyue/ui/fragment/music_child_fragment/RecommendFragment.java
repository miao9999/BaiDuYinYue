package com.example.dllo.baiduyinyue.ui.fragment.music_child_fragment;

import android.support.v4.view.ViewPager;

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
    private TextView songlistTitleTv, songlistMoreTv, newsongTitleTv, newsongMoreTv, hotsealTitleTv, hotsealMoreTv;
    private TextView hotMvTitleTv, hotMvMoreTv, radioPlayTitleTv, radioPlayMoreTv, specialTitleTv, specialMoreTv;
    private ImageView songlistIv, newsongIv, hotsealIv, hotMvIv, radioPlayIv, specialIv;
    private List<RecommendBean.SmallIconBean> smallIconBeen;


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
        songlistIv = findView(R.id.songlist_title_iv);
        songlistTitleTv = findView(R.id.songlist_title_tv);
        songlistMoreTv = findView(R.id.songlist_more_tv);
        newsongIv = findView(R.id.newsong_title_iv);
        newsongTitleTv = findView(R.id.newsong_title_tv);
        newsongMoreTv = findView(R.id.newsong_more_tv);
        hotsealIv = findView(R.id.hotmv_title_iv);
        hotsealTitleTv = findView(R.id.hotseal_title_tv);
        hotsealMoreTv = findView(R.id.hotseal_more_tv);
        hotMvIv = findView(R.id.hotmv_title_iv);
        hotMvMoreTv = findView(R.id.hotmv_more_tv);
        hotMvTitleTv = findView(R.id.hotmv_title_tv);
        radioPlayIv = findView(R.id.radio_play_title_iv);
        radioPlayTitleTv = findView(R.id.radio_play_title_tv);
        radioPlayMoreTv = findView(R.id.radio_play_more_tv);
        specialIv = findView(R.id.special_title_iv);
        specialTitleTv = findView(R.id.special_title_tv);
        specialMoreTv = findView(R.id.special_more_tv);

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
                // title
                smallIconBeen = recommendBean.getModule();
                setTitle();

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
//        recommendViewAdapter.setImageView(imageViews);
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

    /**
     * title
     */
    public void setTitle() {
        songlistTitleTv.setText(recommendBean.getModule().get(3).getTitle());
        songlistMoreTv.setText(recommendBean.getModule().get(3).getTitle_more());
        Picasso.with(context).load(recommendBean.getModule().get(3).getPicurl()).into(songlistIv);

        newsongTitleTv.setText(recommendBean.getModule().get(5).getTitle());
        newsongMoreTv.setText(recommendBean.getModule().get(5).getTitle_more());
        Picasso.with(context).load(recommendBean.getModule().get(5).getPicurl()).into(newsongIv);

        hotsealTitleTv.setText(recommendBean.getModule().get(6).getTitle());
        hotsealMoreTv.setText(recommendBean.getModule().get(6).getTitle_more());
        Picasso.with(context).load(recommendBean.getModule().get(6).getPicurl()).into(newsongIv);

        hotMvTitleTv.setText(recommendBean.getModule().get(11).getTitle());
        hotMvMoreTv.setText(recommendBean.getModule().get(11).getTitle_more());
        Picasso.with(context).load(recommendBean.getModule().get(11).getPicurl()).into(hotMvIv);

        radioPlayTitleTv.setText(recommendBean.getModule().get(12).getTitle());
        radioPlayMoreTv.setText(recommendBean.getModule().get(12).getTitle_more());
        Picasso.with(context).load(recommendBean.getModule().get(12).getPicurl()).into(radioPlayIv);

        specialTitleTv.setText(recommendBean.getModule().get(13).getTitle());
        specialMoreTv.setText(recommendBean.getModule().get(13).getTitle_more());
//        Picasso.with(context).load(recommendBean.getModule().get(13).getPicurl()).into(specialIv);


    }

}
