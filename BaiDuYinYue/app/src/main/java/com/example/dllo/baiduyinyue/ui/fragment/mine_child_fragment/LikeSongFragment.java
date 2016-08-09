package com.example.dllo.baiduyinyue.ui.fragment.mine_child_fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.db.CollectionBean;
import com.example.dllo.baiduyinyue.mode.db.DBTool;
import com.example.dllo.baiduyinyue.ui.adapter.LikeSongAdapter;
import com.example.dllo.baiduyinyue.ui.fragment.AbsBaseFragment;
import com.example.dllo.baiduyinyue.views.MyListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limiao on 16/7/27.
 * 收藏的喜欢的歌曲的fragment界面
 */
public class LikeSongFragment extends AbsBaseFragment implements View.OnClickListener {
    private TextView songNumTv;
    private ImageView backIv;
    private MyListView myListView;
    private LikeSongAdapter likeSongAdapter;
    private ImageView img1,img2,img3;
    private ImageView[] imgs = {img1, img2, img3};
    private List<CollectionBean> collectionBeen;
    private Button delBtn;
    private int [] imgIds = {R.id.likesong_fragment_img1,R.id.likesong_fragment_img2,R.id.likesong_fragment_img3};


    @Override
    protected int setLayout() {
        return R.layout.fragment_child_likesong;
    }

    @Override
    protected void initView() {
        songNumTv = findView(R.id.likesong_fragment_num);
        delBtn = findView(R.id.likesong_fragment_del_btn);
        backIv = findView(R.id.likesong_fragment_back_iv);
        myListView = findView(R.id.likesong_fragment_lv);
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = findView(imgIds[i]);
        }
        collectionBeen = new ArrayList<>();
        likeSongAdapter = new LikeSongAdapter(context);
        backIv.setOnClickListener(this);
        delBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        collectionBeen = DBTool.getDbInstance().queryAll();
        likeSongAdapter.setCollectionBeen(collectionBeen);
        myListView.setAdapter(likeSongAdapter);
        List<String> imgUrls = new ArrayList<>();
        // 如果收藏的歌曲有图片的url数据的话就存入imgUrls集合中
        for (int i = 0; i < collectionBeen.size(); i++) {
            if (collectionBeen.get(i).getPicUrl() !=null) {
                String imgUrl = collectionBeen.get(i).getPicUrl();
                imgUrls.add(imgUrl);
            }
        }
        // 当imgUrls集合中有数据时，最多只显示3条
        if (imgUrls.size() > 0) {
            int imgUrlSize = imgUrls.size() > 3 ? 3 : imgUrls.size();
            for (int i = 0; i < imgUrlSize; i++) {
                if (imgUrls.get(i).length() > 0) {
                    Picasso.with(context).load(imgUrls.get(i)).error(R.mipmap.view_loading).into(imgs[i]);
                }
            }
        }
        songNumTv.setText(String.valueOf(collectionBeen.size()));
        // 长按删除
        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                likeSongAdapter.delData(position);
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        // 返回
        switch (v.getId()){
            case R.id.likesong_fragment_back_iv:
                getActivity().onBackPressed();
                break;

        }
    }


}
