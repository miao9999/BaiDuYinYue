package com.example.dllo.baiduyinyue.ui.fragment.recommend_chlid_fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.RecommendSortContentBean;
import com.example.dllo.baiduyinyue.mode.bean.RecommendSortHeadBean;
import com.example.dllo.baiduyinyue.mode.net.NetValues;
import com.example.dllo.baiduyinyue.ui.adapter.recommend.RecommendSortContentAdapter;
import com.example.dllo.baiduyinyue.ui.adapter.recommend.RecommendSortHeadAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.AbsBaseFragment;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.example.dllo.baiduyinyue.views.MyGridView;
import com.example.dllo.baiduyinyue.views.MyListView;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Limiao on 16/7/19.
 * 乐库--推荐---歌曲分类详情界面的fragment
 */
public class RecommendSortFragment extends AbsBaseFragment implements View.OnClickListener {
    // 上面图片的id
    private int imgIds [] = {R.mipmap.ic_classify_img01,R.mipmap.ic_classify_img02,R.mipmap.ic_classify_img03,R.mipmap.ic_classify_img04,R.mipmap.ic_classify_img05,R.mipmap.ic_classify_img06,R.mipmap.ic_classify_img07,R.mipmap.ic_classify_img08};
    private List<RecommendSortHeadBean.TaglistBean> taglistBeen;
    private RecommendSortHeadBean recommendSortHeadBean;
    private RecommendSortHeadAdapter recommendSortHeadAdapter;
    private MyGridView myGridView;
    private TextView titleTv;
    private RecommendSortContentBean recommendSortContentBean;
    private MyListView listView;
    private RecommendSortContentAdapter contentAdapter;
    @Override
    protected int setLayout() {
        return R.layout.fragment_child_recommend_sort;
    }

    @Override
    protected void initView() {
        myGridView = findView(R.id.recommend_child_fragment_sort_head_gv);
        titleTv = findView(R.id.recommend_child_fragment_sort_title_tv);
        listView = findView(R.id.recommend_child_fragment_sort_content_lv);

    }

    @Override
    protected void initData() {
        recommendSortHeadAdapter = new RecommendSortHeadAdapter(context);
        contentAdapter = new RecommendSortContentAdapter(context);

        // 解析上方图片的数据
        VolleySingle.getInstance(context).startRequest(NetValues.RECOMMEND_SORT_HEAD_URL, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                L.e("recommendSortFragment","解析成功");
                Gson gson =  new Gson();
                recommendSortHeadBean = gson.fromJson(url,RecommendSortHeadBean.class);
                taglistBeen = recommendSortHeadBean.getTaglist();
            }

            @Override
            public void failure() {
                L.e("recommendSortFragment","解析失败");
            }
        });

        // 设置上方部分的文字
        recommendSortHeadAdapter.setTaglistBeen(taglistBeen);
        recommendSortHeadAdapter.setImgIds(imgIds);
        myGridView.setAdapter(recommendSortHeadAdapter);

        //解析具体分类的数据
        VolleySingle.getInstance(context).startRequest(NetValues.RECOMMEND_SORT_CONTENT_URL, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                L.e("recommendSortFragment","解析成功");
                Gson gson = new Gson();
                recommendSortContentBean = gson.fromJson(url,RecommendSortContentBean.class);
                L.e("recommendSortContentBean",recommendSortContentBean.getTags().size() + "");
            }

            @Override
            public void failure() {
                L.e("recommendSortFragment","解析失败");
            }
        });
//        // 设置具体的内容
        contentAdapter.setRecommendSortContentBean(recommendSortContentBean);
        listView.setAdapter(contentAdapter);
        L.e("contentAdapter");

        // 设置title返回的监听
        titleTv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recommend_child_fragment_sort_title_tv:
                if (onSkipFragment != null){
                    onSkipFragment.toFragment(Contant.MAIN_FRAGMENT,null);
                }
                break;
        }
    }
}
