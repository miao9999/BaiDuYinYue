package com.example.dllo.baiduyinyue.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Limiao on 16/7/13.
 * Volley的单例类
 */
public class VolleySingle  {
    private static VolleySingle instance;
    private RequestQueue queue;
    private Context context;

    private VolleySingle(Context context){
        queue = Volley.newRequestQueue(context);
    }


    /**
     * 为外部提供一个方法
     * @param context
     * @return
     */
    public static VolleySingle getInstance(Context context){
        if (instance == null) {
            synchronized (VolleySingle.class){
                if (instance == null) {
                    instance = new VolleySingle(context);
                }
            }
        }
        return instance;
    }


    /**
     * 使用volley的方法
     * @param url
     * @param result
     */
   public void startRequest(String url, final VolleyResult result){
       StringRequest request = new StringRequest(url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               result.success(response);
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               result.failure();
           }
       });

       queue.add(request);

   }
    /**
     * 传值的接口
     */
   public interface VolleyResult{
        void success(String url);
        void failure();
    }
}
