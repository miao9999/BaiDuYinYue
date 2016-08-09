package com.example.dllo.baiduyinyue.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.utils.L;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Limiao on 16/7/31.
 * 旋转图片的view
 */
public class RunningView extends View {
    private int imgSrc;
    private Paint paint;
    private int radius;
    private Matrix matrix;
    private int bitmapWidth;
    private int viewWidth;
    private int rotateAngle = 0;
    private float scaleRatio;
    private Bitmap bitmap;
    private BitmapShader bitmapShader;
    private Thread startThread;
    private String imgUrl;
    private OnRunningListener runningListener;
    private boolean isRunning = true;


    /**
     * 控制开始旋转和暂停旋转的接口
     */
    public interface OnRunningListener{
        void onStart();
        void onStop();
    }



    public RunningView(Context context) {
        super(context);
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.RunningView);
        imgSrc = typedArray.getResourceId(R.styleable.RunningView_imgSrc, -1);
        typedArray.recycle();
        inits();
    }


    public RunningView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RunningView);
        imgSrc = typedArray.getResourceId(R.styleable.RunningView_imgSrc, -1);
        typedArray.recycle();
        inits();
    }

    private void inits() {
        paint = new Paint();
        matrix = new Matrix();
        paint.setAntiAlias(true);// 抗锯齿
        if (imgSrc != -1) {
            bitmap = BitmapFactory.decodeResource(getResources(), imgSrc);
        }
    }

    /**
     * 子类view的这两个参数,由viewGroup中的layout_width,layout_heigth和padding,以及
     * view自身的layout_margin共同决定的.weight也是尤其需要考虑的因素,有它的存在的情况
     * 稍微复杂点
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measrueWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        viewWidth = Math.min(width, height);// 返回两个值中较小的值
        setMeasuredDimension(viewWidth, viewWidth);// 这个方法决定了当前view的大小
        radius = viewWidth / 2;
    }
    /**
     * @param heightMeasureSpec 这个值由高32位和低16位组成,高32位保存的值叫specMode,可以通过代码中所求的Measure.getMode()
     *                          获取,低16位为spceSize,同样可以由MeasureSpce.getSize()获取
     *                          EXACTLY:对应match_parent和其他值
     *                          AT_MOST:对应wrap_content
     *                          UNSPECIFIED:随意指定视图的大小
     * @return
     */
    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int restult = 0;
        if (mode == MeasureSpec.EXACTLY) {
            restult = size;
        } else {
            restult = 500;
            if (mode == MeasureSpec.AT_MOST) {
                restult = Math.min(size, restult);
            }
        }
        return restult;
    }

    private int measrueWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int restult = 0;
        if (mode == MeasureSpec.EXACTLY) {
            restult = size;
        } else {
            restult = 500;
            if (mode == MeasureSpec.AT_MOST) {
                restult = Math.min(size, restult);
            }
        }
        return restult;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap != null) {
            bitmapWidth = Math.min(bitmap.getWidth(), bitmap.getHeight());
            // bitmapShader:位图渲染器
            // tileMode:有三种
            //  CLAMP:如果渲染器超出原始边界范围,会复制范围内边缘染色
            // REPEAT: 横向和纵向的重复渲染器图片,平铺
            // MIRROR: 横向和纵向的重复渲染器图片,以镜像方式平铺.
            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            // 设置shard的本地矩阵
            bitmapShader.setLocalMatrix(matrix);
            paint.setShader(bitmapShader);
            scaleRatio = viewWidth * 1.0f / bitmapWidth;// 计算图片比例
            canvas.drawCircle(radius, radius, radius, paint);
            starRunning();
        }
    }

    public void starRunning() {

            startThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 设置图片的缩放变换,x轴和轴上的缩比例
                    matrix.setScale(scaleRatio, scaleRatio);
                    matrix.preRotate(rotateAngle, bitmapWidth / 2, bitmapWidth / 2);
                    if (isRunning) {
                        rotateAngle++;
                        if (rotateAngle == 360) {
                            rotateAngle = 0;
                        }
                        postInvalidate();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
        startThread.start();
    }


    public void start(){
        isRunning = true;
        if (runningListener != null){
            runningListener.onStart();
            starRunning();
        }
    }
    public void stop() {
        isRunning = false;
        if (runningListener != null){
            runningListener.onStop();
        }
        L.e("stop");
    }

    public void setRunningListener(OnRunningListener runningListener) {
        this.runningListener = runningListener;
    }

    public void setImgSrc(int imgSrc1) {
        imgSrc = imgSrc1;
        bitmap = BitmapFactory.decodeResource(getResources(), imgSrc);
        invalidate();
    }

    public void setImgUrl(String url) {
        imgUrl = url;
        new ImgAsycTask().execute(imgUrl);
    }

    class ImgAsycTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            Bitmap bitmap1;
            try {
                URL iUrl = new URL(url);
                try {
                    connection = (HttpURLConnection) iUrl.openConnection();
                    inputStream = connection.getInputStream();
                    bitmap1 = BitmapFactory.decodeStream(inputStream);
                    return bitmap1;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bt) {
            super.onPostExecute(bt);
            bitmap = bt;
            L.e("bitmap");

        }
    }


}
