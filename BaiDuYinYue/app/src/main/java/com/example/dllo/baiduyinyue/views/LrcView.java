package com.example.dllo.baiduyinyue.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.ui.application.MyApp;
import com.example.dllo.baiduyinyue.utils.L;
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
 * Created by Limiao on 16/7/29.
 * 处理歌词类
 */
public class LrcView extends View {
    private List<Long> mLrcTimes;
    private List<String> mLrcTexts;
    private Paint mNormalPaint;
    private Paint mCurrentPaint;
    private float mTextSize;
    private float mDividerHeight;
    private long mAnimationDuration;
    private float mAnimOffset;
    private long mNextTime = 0L;
    private int mCurrentLine = 0;
    private boolean isEnd = false;
    private String label = "暂无歌词";



    public LrcView(Context context) {
        this(context, null);
    }

    public LrcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LrcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    //初始化
    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.LrcView);
        mTextSize = ta.getDimension(R.styleable.LrcView_textSize, sp2px(14));
        mDividerHeight = ta.getDimension(R.styleable.LrcView_divider_height, dp2px(22));
        mAnimationDuration = ta.getInt(R.styleable.LrcView_animation_duration, 1000);
        mAnimationDuration = mAnimationDuration < 0 ? 1000 : mAnimationDuration;
        int normalColor = ta.getColor(R.styleable.LrcView_textColor_normal, 0xFFFFFFFF);
        int currentColor = ta.getColor(R.styleable.LrcView_textColor_current, 0xFFFF4081);
        ta.recycle();

        mLrcTimes = new ArrayList<>();
        mLrcTexts = new ArrayList<>();
        mNormalPaint = new Paint();
        mCurrentPaint = new Paint();
        mNormalPaint.setAntiAlias(true);
        mNormalPaint.setColor(normalColor);
        mNormalPaint.setTextSize(mTextSize);
        mCurrentPaint.setAntiAlias(true);
        mCurrentPaint.setColor(currentColor);
        mCurrentPaint.setTextSize(mTextSize);

    }

    public static int sp2px(float spValue) {
        final float fontScale = MyApp.getContext().getResources().getDisplayMetrics().density;
        return (int) (spValue * fontScale + 0.5f);

    }

    public static int dp2px(float dpValue) {
        final float scale = MyApp.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 中心Y坐标
        float centerY = getHeight() / 2 + mTextSize / 2 + mAnimOffset;

//         无歌词文件
        if (!hasLrc()) {
            float centerX = (getWidth() - mCurrentPaint.measureText(label)) / 2;
            canvas.drawText(label, centerX, centerY, mCurrentPaint);
            return;
        }

        // 画当前行
        String currStr = mLrcTexts.get(mCurrentLine);
        float currX = (getWidth() - mCurrentPaint.measureText(currStr)) / 2;
        canvas.drawText(currStr, currX, centerY, mCurrentPaint);

        // 画当前行上面的
        for (int i = mCurrentLine - 1; i >= 0; i--) {
            String upStr = mLrcTexts.get(i);
            float upX = (getWidth() - mNormalPaint.measureText(upStr)) / 2;
            float upY = centerY - (mTextSize + mDividerHeight) * (mCurrentLine - i);
            // 超出屏幕停止绘制
            if (upY - mTextSize < 0) {
                break;
            }
            canvas.drawText(upStr, upX, upY, mNormalPaint);
        }

        // 画当前行下面的
        for (int i = mCurrentLine + 1; i < mLrcTimes.size(); i++) {
            String downStr = mLrcTexts.get(i);
            float downX = (getWidth() - mNormalPaint.measureText(downStr)) / 2;
            float downY = centerY + (mTextSize + mDividerHeight) * (i - mCurrentLine);
            // 超出屏幕停止绘制
            if (downY > getHeight()) {
                break;
            }
            canvas.drawText(downStr, downX, downY, mNormalPaint);
        }
    }

    public void searchLrc() {
        reset();

        label = "正在搜索歌词";
        postInvalidate();
    }


    //加载网络歌词
    public void loadNetLrc(String url) {
        reset();
        if (TextUtils.isEmpty(url)) {
            label = "暂无歌词";
            postInvalidate();
            return;
        }
        StreamRequest streamRequest = new StreamRequest(url, new Response.Listener<InputStream>() {
            @Override
            public void onResponse(InputStream response) {
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(response));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        String[] arr = parseLine(line);
                        if (arr != null) {
                            mLrcTimes.add(Long.parseLong(arr[0]));
                            mLrcTexts.add(arr[1]);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (br != null) {
                            br.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                label = "暂无歌词";
                postInvalidate();
            }
        });
        VolleySingle.getInstance(MyApp.getContext()).getQueue().add(streamRequest);

    }

    /**
     * 加载本地歌词文件
     */
    public void loadLocalLrc(String path) {
        reset();
        path = Environment.getExternalStorageDirectory() + "/music/lrc/" + path + ".lrc";
        if (TextUtils.isEmpty(path) || !new File(path).exists()) {
            label = "暂无歌词";

            postInvalidate();
            return;
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = parseLine(line);
                if (arr != null) {
                    mLrcTimes.add(Long.parseLong(arr[0]));
                    mLrcTexts.add(arr[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String temp = null;

    public void loadLrc(String path) {
        if (path != null) {
            if (!path.equals(temp)) {
                if (path.length() > 3 && path.substring(0, 4).equals("http")) {
                    Log.d("LrcView", "if");
                    loadNetLrc(path);
                    temp = path;
                } else {
                    Log.d("LrcView", "else");
                    loadLocalLrc(path);
                    temp = path;
                }
            }
        }
    }

    private void reset() {
        mLrcTexts.clear();
        mLrcTimes.clear();
        mCurrentLine = 0;
        mNextTime = 0L;
        isEnd = false;
    }

    /**
     * 更新进度
     *
     * @param time 当前时间
     */
    public synchronized void updateTime(long time) {
        // 避免重复绘制
        if (time < mNextTime || isEnd) {
//            L.e("lrcView", time + "");
            return;
        }
        for (int i = mCurrentLine; i < mLrcTimes.size(); i++) {
            if (mLrcTimes.get(i) > time) {
                mNextTime = mLrcTimes.get(i);
                mCurrentLine =( i < 1 ? 0 : i - 1);
                L.e("nexttime", mNextTime + "");
                newLineAnim();
                break;

            } else if (i == mLrcTimes.size() - 1) {
                // 最后一行
                mCurrentLine = mLrcTimes.size() - 1;
                isEnd = true;
                newLineAnim();
                break;
            }

        }
    }

    public void onDrag(int progress) {
        for (int i = 0; i < mLrcTimes.size(); i++) {
            if (mLrcTimes.get(i) > progress) {
                mNextTime = mLrcTimes.get(i);
                mCurrentLine = i < 1 ? 0 : i - 1;
                isEnd = false;
                newLineAnim();
                break;
            }
        }
    }

    public boolean hasLrc() {
        return mLrcTexts != null && !mLrcTexts.isEmpty();
    }

    /**
     * 解析一行
     *
     * @param
     * @return {
     */
    private String[] parseLine(String line) {
        Matcher matcher = Pattern.compile("\\[(\\d)+:(\\d)+(\\.)(\\d+)\\].+").matcher(line);
        if (!matcher.matches()) {
            return null;
        }
        line = line.replaceAll("\\[", "");
        String[] result = line.split("\\]");
        result[0] = parseTime(result[0]);
        return result;
    }

    /**
     * 解析时间
     *
     * @param time 00:10.61
     * @return long
     */
    private String parseTime(String time) {
        time = time.replaceAll(":", "\\.");
        String[] times = time.split("\\.");
        long l = 0L;
        try {
            long min = Long.parseLong(times[0]);
            long sec = Long.parseLong(times[1]);
            long mil = Long.parseLong(times[2]);
            l = min * 60 * 1000 + sec * 1000 + mil * 10;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return String.valueOf(l);
    }

    /**
     * 换行动画
     * Note:属性动画只能在主线程使用
     */
    private void newLineAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(mTextSize + mDividerHeight, 0.0f);
        animator.setDuration(mAnimationDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimOffset = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }


}
