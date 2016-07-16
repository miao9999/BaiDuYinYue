package com.example.dllo.baiduyinyue.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Limiao on 16/7/11.
 * Activity的基类
 */
public abstract class AbsBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置全屏显示
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(setLayout());
        // 初始化组件
        initView();
        // 设置数据
        initData();
    }



    /**
     * 绑定布局
     * @return
     */
    protected abstract int  setLayout();

    /**
     * 绑定组件
     */
    protected abstract void initView();

    /**
     * 设置数据
     */
    protected abstract void initData();

    /**
     * 简化findViewById
     * @param resId id
     * @param <T> 泛型
     * @return
     */
    protected  <T extends View> T findView(int resId){

        return (T) findViewById(resId);
    }

    /**
     * intent跳转
     * @param from
     * @param to
     */
    protected void goTo(Context from,Class<? extends AbsBaseActivity> to){
        Intent intent = new Intent(from,to);
        startActivity(intent);
    }

    /**
     * 带值跳转
     * @param from
     * @param to
     * @param values  Bundle类型的值
     */
    protected void goTo(Context from,Class<? extends AbsBaseActivity> to,Bundle values){
        Intent intent = new Intent(from,to);
        intent.putExtras(values);
        startActivity(intent);
    }

}
