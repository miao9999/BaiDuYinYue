package com.example.dllo.baiduyinyue.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;

/**
 * Created by Limiao on 16/7/14.
 * 自定义的把imageView和TextView合在一起的 自定义View
 */
public class CustomImgBtn extends LinearLayout {
    private ImageView imageView;
    private TextView textView;
    public CustomImgBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_img_btn,this,true);
        imageView = (ImageView) findViewById(R.id.custom_img_btn_iv);
        textView = (TextView) findViewById(R.id.custom_img_btn_tv);
    }

    public void setImgBtnImg(int resId){
        imageView.setImageResource(resId);
    }

    public void setImgBtnText(String text){
        textView.setText(text);
    }
}
