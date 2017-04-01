package com.wondersgroup.testsdk.bubblechart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyaqing on 2017/3/22.
 */

public class RingChart extends View {
    private String TAG = "RINGCHART";


    private int firstColor;//第一种颜色
    private int twoColor;//第二种颜色
    private int textColor;//字体颜色

    private int mRingWidth = 40;//圆环宽度

    private int mStrokeWidth = 30;//圆的粗细

    private RectF oval;//圆的区域
    private int center;  //获取圆心的x坐标
    private int mRadius;//圆环的半径
    private Paint cyclePaint;//圆环画笔

    //    private Rect centTextRect;//中间字的区域
    private Paint centerTextPaint;//中间字画笔
    private String centerText = "嘉定区";
    private float centerTextSize = 40;//字体大小
    private Rect centerTextRect;

    private float firstData = 89;//第一种占的数量
    private float twoData = 24;//第二种占的数量

    private Paint dataPaint;//圆环上数字画笔
    private float dataTextSize = 30;//数字字体大小

    //数据
    private List<Float> dataList = new ArrayList<Float>();
    private float dataSum = 0;

    private int color[] = {Color.argb(200, 56, 718, 249), Color.argb(200, 156, 78, 49), Color.argb(200, 00, 78, 49),
            Color.argb(200, 56, 78, 9), Color.argb(200, 4, 78, 49), Color.argb(200, 56, 78, 0)};

    private float animatedRingValue;//环形动画
    private int animatedCenterTextValue;//字体动画
    private int animatedDataValue;

    public RingChart(Context context) {
        this(context, null);
    }

    public RingChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dataList.add(124f);
        dataList.add(189f);

        Log.i("ds", "onMeasure: " + dataList.size());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        cyclePaint = new Paint();
        center = getWidth() / 2;
        mRadius = (int) (center - mStrokeWidth / 2);
        oval = new RectF(center - mRadius, center - mRadius, center + mRadius, center + mRadius);
        centerTextPaint = new Paint();


        centerTextRect = new Rect();
        centerTextPaint.getTextBounds(centerText, 0, centerText.length(), centerTextRect);

        dataPaint = new Paint();
        initRingAnimator();
        initCenterAnimator();
        initDataAnimator();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawRing(canvas);
        drawCenterText(canvas);
        drawDataText(canvas);
    }

    private void drawDataText(Canvas canvas) {
        dataPaint.setAntiAlias(true);
        dataPaint.setStyle(Paint.Style.FILL);
        dataPaint.setTextSize(dataTextSize);
        dataPaint.setStrokeWidth(1);
        dataPaint.setARGB(animatedDataValue, 127, 115, 212);

        float dataPre = 0;
        float dataCurr = 0;

        for (int i = 0; i < dataList.size(); i++) {
            if (i == 0) {
                dataPre = 0;
            } else {
                dataPre += (dataList.get(i - 1) / sumData(dataList)) * 360;
            }
            Log.i(TAG, "dataPre: " + dataPre);
            dataCurr = (dataList.get(i) / sumData(dataList)) * 360 + dataPre;
            Log.i(TAG, "dataCurr: " + dataCurr);

            //一四象限
            if (((dataPre + (dataCurr - dataPre) / 2)>=0&&(dataPre + (dataCurr - dataPre) / 2)<90)||((dataPre + (dataCurr - dataPre) / 2)>=270&&(dataPre + (dataCurr - dataPre) / 2)<360)){
                PointF point2 = MathHelper.getInstance().calcArcEndPointXY(
                        center, center, center -mStrokeWidth , dataPre + (dataCurr - dataPre) / 2);
                //标识2
                DrawHelper.getInstance().drawRotateText(String.valueOf(dataList.get(i)), point2.x, point2.y, 0,
                        canvas, dataPaint);
            }else {
                //二三象限
                PointF point2 = MathHelper.getInstance().calcArcEndPointXY(
                        center,center, center, dataPre + (dataCurr - dataPre) / 2);
                //标识2
                DrawHelper.getInstance().drawRotateText(String.valueOf(dataList.get(i)), point2.x, point2.y, 0,
                        canvas, dataPaint);
            }
        }

    }

    private void drawCenterText(Canvas canvas) {
        centerTextPaint.setAntiAlias(true);
        centerTextPaint.setStyle(Paint.Style.FILL);
        centerTextPaint.setTextSize(centerTextSize);
        centerTextPaint.setStrokeWidth(1);
        centerTextPaint.setARGB(animatedCenterTextValue, 127, 115, 212);
        canvas.drawText(centerText, center - centerTextPaint.measureText(centerText) / 2, center, centerTextPaint);
    }

    private void drawRing(Canvas canvas) {

        float startPercent = 0;
        float sweepPercent = 0;

        for (int i = 0; i < dataList.size(); i++) {

            float bfb = dataList.get(i) / sumData(dataList);
            Log.i(TAG, dataList.get(i) + ", " + sumData(dataList) + "," + dataList.get(i) / sumData(dataList));
            sweepPercent = bfb * 360;
            Log.i(TAG, "sweepPercent: " + sweepPercent);
            //第一段
            cyclePaint.setAntiAlias(true);
            cyclePaint.setStyle(Paint.Style.STROKE);
            cyclePaint.setStrokeWidth(mStrokeWidth);
            cyclePaint.setColor(color[i]);
            if (Math.min(sweepPercent - 1, animatedRingValue - startPercent) >= 0) {
                canvas.drawArc(oval, startPercent, Math.min(sweepPercent - 1, animatedRingValue - startPercent), false, cyclePaint);
//                drawText(canvas,sweepPercent/2+startPercent,dataList.get(i)+"",sweepPercent);
            }
            startPercent += sweepPercent;
        }
    }

    private float sumData(List<Float> list) {
        dataSum = 0;
        for (int i = 0; i < list.size(); i++) {
            dataSum += list.get(i);
        }
        return dataSum;
    }

    public void setDataList(List<Float> dataList) {
        Log.i("ds", dataList.size() + "");
        this.dataList.clear();
        this.dataList.addAll(dataList);
        Log.i("ds", dataList.size() + "");
    }

    private void initRingAnimator() {

        //环形动画
        ValueAnimator anim = ValueAnimator.ofFloat(0, 360);
        anim.setDuration(1000);
        anim.setStartDelay(2000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatedRingValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }
    private void initCenterAnimator() {

        //环形动画
        ValueAnimator alphaAnimation=ValueAnimator.ofInt(0,255);
//        AlphaAnimation  alphaAnimation=new AlphaAnimation(0.1f,1.0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatedCenterTextValue = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        alphaAnimation.start();

    }
    private void initDataAnimator() {

        //环形动画
        ValueAnimator alphaAnimation=ValueAnimator.ofInt(0,255);
//        AlphaAnimation  alphaAnimation=new AlphaAnimation(0.1f,1.0f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setStartDelay(2000);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatedDataValue = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        alphaAnimation.start();

    }

}