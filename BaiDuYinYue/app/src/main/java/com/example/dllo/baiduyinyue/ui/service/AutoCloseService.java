package com.example.dllo.baiduyinyue.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.dllo.baiduyinyue.utils.Contant;

/**
 * Created by Limiao on 16/8/7.
 * 定时关闭程序的服务类
 */
public class AutoCloseService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendBroadcast(new Intent(Contant.CLOSE_APP_RECEIVER));
        return super.onStartCommand(intent, flags, startId);


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
