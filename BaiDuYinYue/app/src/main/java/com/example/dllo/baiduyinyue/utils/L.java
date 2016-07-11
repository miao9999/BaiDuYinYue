package com.example.dllo.baiduyinyue.utils;

import android.util.Log;

/**
 * Created by Limiao on 16/7/11.
 * log的工具类,用final修饰,不能被继承
 */
public final class L {
    // 私有化构造方法,外部不能创建该类的对象
    private L(){

    }
    // tag标签
    private static  String TAG = "BaiDuYinYue";
    // 是否为调试模式
    private static boolean isDebug = true;

    /**
     * 使用默认tag标签
     * @param msg
     */
    public static void e(String msg){
        if (isDebug){
        Log.e(TAG,msg);
        }
    }

    /**
     * 使用自定义tag标签
     * @param tag
     * @param msg
     */
    public static void e(String tag,String msg){
        if (isDebug) {
        Log.e(tag,msg);
        }
    }
}
