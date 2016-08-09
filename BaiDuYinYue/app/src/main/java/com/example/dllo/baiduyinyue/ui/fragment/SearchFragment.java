package com.example.dllo.baiduyinyue.ui.fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.LocalMusicSongBean;
import com.example.dllo.baiduyinyue.mode.bean.SearchBean;
import com.example.dllo.baiduyinyue.mode.bean.SearchResultBean;
import com.example.dllo.baiduyinyue.ui.adapter.SearchAdapter;
import com.example.dllo.baiduyinyue.ui.adapter.SearchResultAdapter;
import com.example.dllo.baiduyinyue.ui.adapter.localmusic.LocalMusicSongAdapter;
import com.example.dllo.baiduyinyue.ui.adapter.localmusic.LocalMusicVpAdapter;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.L;
import com.example.dllo.baiduyinyue.mode.net.NetValues;
import com.example.dllo.baiduyinyue.utils.T;
import com.example.dllo.baiduyinyue.utils.VolleySingle;
import com.example.dllo.baiduyinyue.views.MyGridView;
import com.example.dllo.baiduyinyue.views.MyListView;
import com.google.gson.Gson;

import java.util.ArrayList;
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
    private ImageView backIv, searchIv;
    private EditText searchEt;
    private String rightUrl;
    private String urlLeft;
    private MyListView myListView;
    private SearchResultAdapter resultAdapter;
    private SearchResultBean resultBean;
    private TextView hotSearchTv;

    @Override
    protected int setLayout() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView() {
        myGridView = findView(R.id.search_fragment_gv);
        backIv = findView(R.id.search_fragment_return_iv);
        searchIv = findView(R.id.search_fragment_search_iv);
        searchEt = findView(R.id.search_fragment_et);
        myListView = findView(R.id.search_fragment_lv);
        hotSearchTv = findView(R.id.search_fragment_hot_search_tv);


    }

    @Override
    protected void initData() {
        urlLeft = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.search.merge&query=";
        rightUrl = "&page_size=50&page_no=1&type=-1&format=json&from=ios&version=5.2.5&from=ios&channel=appstore";
        resultAdapter = new SearchResultAdapter(context);
        searchAdapter = new SearchAdapter(context);
        //解析数据
        VolleySingle.getInstance(context).startRequest(NetValues.SEARCH_URL, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                Gson gson = new Gson();
                searchBean = gson.fromJson(url, SearchBean.class);
                mySearchBeen = searchBean.getResult();
                // 设置数据
                searchAdapter.setMySearchBeen(mySearchBeen);
                myGridView.setAdapter(searchAdapter);
            }

            @Override
            public void failure() {
            }
        });

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    search(s);
                }else {
                    myListView.setVisibility(View.GONE);
                    myGridView.setVisibility(View.VISIBLE);
                    hotSearchTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    search(s);
                }else {
                    myListView.setVisibility(View.GONE);
                    myGridView.setVisibility(View.VISIBLE);
                    hotSearchTv.setVisibility(View.VISIBLE);
                }
            }
        });

        //  back和search的点击事件
        backIv.setOnClickListener(this);
        searchIv.setOnClickListener(this);
        String str = searchEt.getText().toString();
        if (TextUtils.isEmpty(str)){
            myListView.setVisibility(View.GONE);
            myGridView.setVisibility(View.VISIBLE);
            hotSearchTv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 搜索的方法
     * @param s
     */
    private void search(CharSequence s) {
        String url = urlLeft + s + rightUrl;
        VolleySingle.getInstance(context).startRequest(url, new VolleySingle.VolleyResult() {
            @Override
            public void success(String url) {
                Gson gson = new Gson();
                resultBean = gson.fromJson(url, SearchResultBean.class);
                List<SearchResultBean.ResultBean.SongInfoBean.SongListBean> songListBeen = resultBean.getResult().getSong_info().getSong_list();
                resultAdapter.setSongListBeen(songListBeen);
                myListView.setAdapter(resultAdapter);
                myGridView.setVisibility(View.GONE);
                hotSearchTv.setVisibility(View.GONE);
            }

            @Override
            public void failure() {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_fragment_return_iv:
                if (onSkipFragment != null) {
                    onSkipFragment.toFragment(Contant.MAIN_FRAGMENT, null);
                }
                break;
            case R.id.search_fragment_search_iv:
                String str = searchEt.getText().toString();
                search(str);
                break;
        }
    }
}
