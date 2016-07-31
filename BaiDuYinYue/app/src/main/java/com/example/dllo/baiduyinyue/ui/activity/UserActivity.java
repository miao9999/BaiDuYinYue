package com.example.dllo.baiduyinyue.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;

/**
 * Created by Limiao on 16/7/17.
 */
public class UserActivity extends AbsBaseActivity implements View.OnClickListener {
    private TextView titleTv;
    private TextView loginTv;
    @Override
    protected int setLayout() {
        return R.layout.activity_user;
    }

    @Override
    protected void initView() {
        titleTv = findView(R.id.user_aty_title_more_tv);
        loginTv = findView(R.id.user_aty_login_tv);
    }

    @Override
    protected void initData() {
        titleTv.setOnClickListener(this);
        loginTv.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_aty_title_more_tv:
                goTo(this,MainActivity.class);
                break;
            case R.id.user_aty_login_tv:
                goTo(this,LoginActivity.class);
                break;
        }
    }
}
