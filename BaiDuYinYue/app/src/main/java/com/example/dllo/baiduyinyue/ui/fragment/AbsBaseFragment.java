package com.example.dllo.baiduyinyue.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dllo.baiduyinyue.ui.activity.AbsBaseActivity;
import com.example.dllo.baiduyinyue.utils.OnSkipFragment;

/**
 * Created by Limiao on 16/7/11.
 * fragment的基类
 */
public abstract class AbsBaseFragment extends Fragment {
    protected Context context;
    protected OnSkipFragment onSkipFragment;

    public void setOnSkipFragment(OnSkipFragment onSkipFragment) {
        this.onSkipFragment = onSkipFragment;
    }

    /**
     * 当activity与fragment发生关联时回调
     *
     * @param context 这里的context就是相关的activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.onSkipFragment = (OnSkipFragment) context;


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setLayout(), container, false);
    }

    /**
     * 绑定布局
     *
     * @return
     */
    protected abstract int setLayout();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    /**
     * 初始化组件
     */
    protected abstract void initView();

    /**
     * 简化findViewById
     *
     * @param resId
     * @param <T>
     * @return
     */
    protected <T extends View> T findView(int resId) {
        return (T) getView().findViewById(resId);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 设置数据
     */
    protected abstract void initData();

    /**
     * 简化intent跳转
     *
     * @param from
     * @param to
     */
    protected void goTo(Context from, Class<? extends AbsBaseActivity> to) {
        Intent intent = new Intent(from, to);
        context.startActivity(intent);
    }

}
