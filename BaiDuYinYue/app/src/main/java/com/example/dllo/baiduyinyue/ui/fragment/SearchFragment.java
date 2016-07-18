package com.example.dllo.baiduyinyue.ui.fragment;

import android.view.View;
import android.widget.ImageView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.SearchBean;
import com.example.dllo.baiduyinyue.ui.adapter.SearchAdapter;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.mode.net.NetValues;
import com.example.dllo.baiduyinyue.utils.T;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.example.dllo.baiduyinyue.views.MyGridView;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Limiao on 16/7/14.
 * 查询 的fragment
 */
public class SearchFragment extends AbsBaseFragment implements View.OnClickListener {
    private MyGridView myGridView;
    private SearchAdapter searchAdapter;
    private SearchBean searchBean;
    private List<SearchBean.MySearchBean> mySearchBeen;
    private ImageView backIv,searchIv;

    @Override
    protected int setLayout() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView() {
        myGridView = findView(R.id.search_fragment_gv);
        backIv = findView(R.id.search_fragment_return_iv);
        searchIv = findView(R.id.search_fragment_search_iv);


    }

    @Override
    protected void initData() {
        searchAdapter = new SearchAdapter(context);
        //解析数据
        VolleySingle.getInstance(context).startRequest(NetValues.SEARCH_URL, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                L.e("searchFragment","解析成功");
                Gson gson = new Gson();
                searchBean = gson.fromJson(url,SearchBean.class);
                mySearchBeen = searchBean.getResult();
            }

            @Override
            public void failure() {
                L.e("searchFragment","解析失败");
            }
        });

        // 设置数据
        searchAdapter.setMySearchBeen(mySearchBeen);
        myGridView.setAdapter(searchAdapter);

        //  back和search的点击事件
        backIv.setOnClickListener(this);
        searchIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_fragment_return_iv:
                T.shortMsg("back");
                if (onSkipFragment != null){
                    onSkipFragment.toFragment(Contant.MAIN_FRAGMENT);
                }
                break;
            case R.id.search_fragment_search_iv:
                break;
        }
    }
}
