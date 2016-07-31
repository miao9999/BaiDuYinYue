package com.example.dllo.baiduyinyue.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.ui.application.MyApp;
import com.example.dllo.baiduyinyue.utils.StreamRequest;
import com.example.dllo.baiduyinyue.utils.VolleySingle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

//    public void setImgBtnImg(int resId){
//        imageView.setImageResource(resId);
//    }
//
//    public void setImgBtnText(String text){
//        textView.setText(text);
//    }
//
//
//    private float textSizef, dividerHeightf, anmiOffsetf;
//    private int currentLine = 0;
//    private List<Long> lrcTime;
//    private List<String> lrcContent;
//    private Paint normalPaint;
//    private Paint currentPaint;
//    private long nextTime = 0l, animationDruation;
//    private boolean isEnd = false;
//    private String label = "暂无歌词";
//
//
//    public LrcView(Context context) {
//        super(context);
//
//    }
//
//    public LrcView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(attrs);
//    }
//
//    public LrcView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(attrs);
//    }
//
//    // 初始化
//    private void init(AttributeSet attributeSet) {
//        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.LrcView);
//        textSizef = typedArray.getDimension(R.styleable.LrcView_textSize, sp2px(14));
//        dividerHeightf = typedArray.getDimension(R.styleable.LrcView_divider_height, dp2px(22));
//        animationDruation = typedArray.getInt(R.styleable.LrcView_animation_duration, 1000);
//        animationDruation = animationDruation < 0 ? 1000 : animationDruation;
//        int normalColor = typedArray.getColor(R.styleable.LrcView_textColor_normal, 0xffffffff);
//        int currentColor = typedArray.getColor(R.styleable.LrcView_textColor_current, 0xff345678);
//
//        //回收TypedArray，以便后面重用
//        typedArray.recycle();
//        lrcTime = new ArrayList<>();
//        lrcContent = new ArrayList<>();
//        normalPaint = new Paint();
//        currentPaint = new Paint();
//        Log.d("LrcView", "paint");
//        normalPaint.setAntiAlias(true);// 抗锯尺
//        normalPaint.setTextSize(textSizef);
//        normalPaint.setColor(normalColor);
//        currentPaint.setColor(currentColor);
//        currentPaint.setAntiAlias(true);
//        currentPaint.setTextSize(textSizef);
//
//
//
//    }
//
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        float centerY = getHeight() / 2 + textSizef / 2 + anmiOffsetf;
////        // 如果没有歌词
//        if (!hasLrc()) {
//            float centerX = (getWidth() - currentPaint.measureText(label)) / 2;
//            canvas.drawText(label, centerX, centerY, currentPaint);
//            return;
//        }
//
//        // 当前行
//        String currentStr = lrcContent.get(currentLine);
//        float currentX = (getWidth() - currentPaint.measureText(currentStr)) / 2;
//        canvas.drawText(currentStr, currentX, centerY, currentPaint);
//        // 当前行的上一行
//        for (int i = currentLine - 1; i >= 0; i--) {
//            String upStr = lrcContent.get(i);
//            float upX = (getWidth() - normalPaint.measureText(upStr));
//            float upY = centerY - (textSizef + dividerHeightf) * (currentLine - i);
//            //超出屏幕停止绘制
//            if (upY - textSizef < 0) {
//                break;
//            }
//            canvas.drawText(upStr, upX, upY, normalPaint);
//
//        }
//
//        // 当前行的下一行
//        for (int i = currentLine + 1; i < lrcTime.size(); i++) {
//            String downStr = lrcContent.get(i);
//            float downX = (getWidth() - normalPaint.measureText(downStr)) / 2;
//            float downY = centerY + (textSizef + dividerHeightf) * (i - currentLine);
//            // 超出屏幕停止绘制
//            if (downY > getHeight()) {
//                break;
//            }
//            canvas.drawText(downStr, downX, downY, normalPaint);
//        }
//    }
//
//    /**
//     * 搜索歌词
//     */
//    public void searchLrc() {
//        reset();
//        label = "正在搜索歌词";
//    }
//
//    /**
//     * 在加载歌词时判断是网络歌词还是本地歌词,然后再进行相应的解析
//     */
//    private String temp = null;
//    public void loadLrc(String path){
//
//        if (path != null){
//            if (!path.equals(temp)){
//                if (path.length() > 3 && path.substring(0,4).equals("http")){
//                    loadNetLrc(path);
//                    temp = path;
//                }else {
//                    loadLocalLrc(path);
//                    temp = path;
//                }
//            }
//        }
//    }
//
//    /**
//     * 当前拖动进度条时更新歌词的进度
//     * @param progress
//     */
//    public void onDrag(int progress){
//        for (int i = 0; i < lrcTime.size(); i++) {
//            if (lrcTime.get(i)>progress){
//                nextTime = lrcTime.get(i);
//                currentLine = i < 1? 0 : i - 1;
//                isEnd = false;
//                newLineAnim();
//                break;
//            }
//        }
//    }
//    /**
//     * 加载网络歌词
//     *
//     * @param url
//     */
//    public void loadNetLrc(String url) {
//        reset();
//        if (TextUtils.isEmpty(url)) {
//            label = "暂无歌词";
//            postInvalidate();
//            return;
//        }
//        StreamRequest streamRequest = new StreamRequest(url, new Response.Listener<InputStream>() {
//            @Override
//            public void onResponse(InputStream response) {
//                BufferedReader br = null;
//                try {
//                    br = new BufferedReader(new InputStreamReader(response));
//                    String line = null;
//                    while ((line = br.readLine()) != null) {
//                        String[] arr = parseLine(line);
//                        if (arr != null) {
//                            lrcTime.add(Long.parseLong(arr[0]));
//                            lrcContent.add(arr[1]);
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        if (br != null) {
//                            br.close();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                label = "暂无歌词";
//                postInvalidate();
//            }
//        });
//
//        VolleySingle.getInstance(MyApp.getContext()).getQueue().add(streamRequest);
//    }
//
//    /**
//     * 加载本地歌词
//     *
//     * @param path
//     */
//    public void loadLocalLrc(String path) {
//        reset();
//        path = Environment.getDownloadCacheDirectory() + "/music/lrc/" + path + ".lrc";
//        if (TextUtils.isEmpty(path) || !new File(path).exists()) {
//            label = "暂无歌词";
//            postInvalidate();
//            return;
//        }
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
//            String line;
//            try {
//                while ((line = br.readLine()) != null) {
//                    String[] lrc = parseLine(line);
//                    if (lrc != null) {
//                        lrcContent.add(lrc[1]);
//                        lrcTime.add(Long.parseLong(lrc[0]));
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (br != null) {
//                    try {
//                        br.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 更新进度
//     *
//     * @param time 当前时间
//     */
//    public synchronized void updateTime(long time) {
//        // 避免重新绘制
//        if (time < nextTime || isEnd) {
//            return;
//        }
//        for (int i = 0; i < lrcTime.size(); i++) {
//            if (lrcTime.get(i) > time) {
//                nextTime = lrcTime.get(i);
//                currentLine = i < 1 ? 0 : i - 1;
//                newLineAnim();
//                break;
//            }else if (i == lrcTime.size() -1){
//                // 最后一行
//                currentLine= lrcTime.size() -1;
//                isEnd = true;
//                newLineAnim();
//                bringToFront();
//            }
//
//        }
//    }
//
//    /**
//     * 换行动画,属性动画只能在主线程中使用
//     */
//    private void newLineAnim() {
//        ValueAnimator animator = ValueAnimator.ofFloat((textSizef + dividerHeightf),0.0f);
//        animator.setDuration(animationDruation);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                anmiOffsetf = (float) animation.getAnimatedValue();
//                invalidate();
//            }
//        });
//        animator.start();
//    }
//
//
//    /**
//     * 解析一行
//     *
//     * @param line
//     * @return
//     */
//    private String[] parseLine(String line) {
//        Matcher matcher = Pattern.compile("\\\\[(\\\\d)+:(\\\\d)+(\\\\.)(\\\\d+)\\\\].+").matcher(line);
//        if (!matcher.matches()) {
//            return null;
//        }
//        line = line.replaceAll("\\[", "");
//        String[] result = line.split("\\]");
//        result[0] = parseTime(result[0]);
//        return new String[0];
//    }
//
//    /**
//     * 解析时间
//     *
//     * @param time
//     * @return
//     */
//    private String parseTime(String time) {
//        time = time.replaceAll(":", "\\.");
//        String[] times = time.split("\\.");
//        long l = 0;
//        try {
//            long min = Long.parseLong(times[0]);
//            long sec = Long.parseLong(times[1]);
//            long mil = Long.parseLong(times[2]);
//            l = min * 60 * 1000 + sec * 1000 + mil * 10;
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//
//        return String.valueOf(l);
//    }
//
//
//    /**
//     * 重置歌词
//     */
//    private void reset() {
//        lrcContent.clear();
//        lrcTime.clear();
//        currentLine = 0;
//        nextTime = 0;
//        isEnd = false;
//    }
//
//    /**
//     * 判断是否有歌词
//     *
//     * @return
//     */
//    public boolean hasLrc() {
//        return lrcContent != null && !lrcContent.isEmpty();
//    }
//
//    /**
//     * sp转px
//     *
//     * @param spValue
//     * @return
//     */
//    public static int sp2px(float spValue) {
//        // 获取屏幕参数
//        final float fontScale = MyApp.getContext().getResources().getDisplayMetrics().density;
//        return (int) (spValue * fontScale + 0.5f);
//    }
//
//    /**
//     * dp转px
//     *
//     * @param dpValue
//     * @return
//     */
//    public static int dp2px(float dpValue) {
//        final float scaple = MyApp.getContext().getResources().getDisplayMetrics().density;
//        return (int) (dpValue * scaple + 0.5f);
//    }
}
