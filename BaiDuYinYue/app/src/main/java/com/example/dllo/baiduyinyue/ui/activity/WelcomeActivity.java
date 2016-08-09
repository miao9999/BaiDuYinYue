package com.example.dllo.baiduyinyue.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;

/**
 * Created by Limiao on 16/7/30.
 * 欢迎页
 */
public class WelcomeActivity extends AbsBaseActivity {
    private TextView countDownTv;
    private CountDownTimer countDownTimer;

    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        countDownTv = findView(R.id.welcome_aty_tv);
    }

    @Override
    protected void initData() {
        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDownTv.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            }
        }.start();

        countDownTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}
