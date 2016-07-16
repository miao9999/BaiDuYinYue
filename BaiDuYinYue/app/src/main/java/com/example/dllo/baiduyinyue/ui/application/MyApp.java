package com.example.dllo.baiduyinyue.ui.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Limiao on 16/7/11.
 * 整个application
 */
public class MyApp extends Application {
    // 用private修饰,在整个应用中只有这一个context;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    /**
     * 对外提供一个静态get方法,来获得此context
     * @return
     */
    public static Context getContext(){
        return context;
    }
}
