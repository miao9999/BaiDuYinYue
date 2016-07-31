package com.example.dllo.baiduyinyue.ui.activity;

import android.webkit.WebView;

import com.example.dllo.baiduyinyue.R;

/**
 * Created by Limiao on 16/7/29.
 */
public class LoginActivity extends AbsBaseActivity {
    private WebView webView;
    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        webView = findView(R.id.login_activity_webview);

    }

    @Override
    protected void initData() {
        //设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webView.loadUrl("http://www.baidu.com/");

    }
}
