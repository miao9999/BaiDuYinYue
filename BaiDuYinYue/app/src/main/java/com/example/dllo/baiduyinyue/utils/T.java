package com.example.dllo.baiduyinyue.utils;

import android.widget.Toast;

import com.example.dllo.baiduyinyue.ui.application.MyApp;

/**
 * Created by Limiao on 16/7/11.
 * toast工具类
 */
public final class T  {
    private static boolean isDebug = true;
    //构造方法私有化,外部不能创建对象
    private T(){
    }

    /**
     * 短时间显示的toast
     * @param msg
     */
    public static void shortMsg(String msg){
        Toast.makeText(MyApp.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示的toast
     * @param msg
     */
    public static void longMsg (String msg){
        Toast.makeText(MyApp.getContext(), msg, Toast.LENGTH_LONG).show();
    }

}
