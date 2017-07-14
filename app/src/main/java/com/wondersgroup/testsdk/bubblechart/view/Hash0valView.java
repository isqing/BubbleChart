package com.wondersgroup.testsdk.bubblechart.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;


import com.wondersgroup.testsdk.bubblechart.modle.HashOval;
import com.wondersgroup.testsdk.bubblechart.modle.Point;
import com.wondersgroup.testsdk.bubblechart.util.ChartUtil;
import com.wondersgroup.testsdk.bubblechart.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyaqing on 2017/5/8.
 */


public class Hash0valView extends View {
    private int mRectX = 140;
    private int mRectY = 100;
    private int pading = 15;
    private int mSpaceH = 100;
    private int charColor=Color.BLUE;
    private int textColor=Color.WHITE;
    private int textSize=14;
    private RectF mRect;
    private Paint chartPait;
    private Paint textPaint;
    private List<Point> pointList;
    private List<HashOval> hashOvalList;
    private onClickChartItem onClickChartItem;
    private int animatedAplaRed;//红色动画
    private int animatedAplaGreen;//绿色动画
    private int heightZ;
    private boolean isHalfScreen=false;//个数太少时
    private int marginTop=50;//半屏时距离顶部100

    public Hash0valView(Context context) {
        this(context, null);
    }

    public Hash0valView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Hash0valView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        pointList = new ArrayList<>();
        hashOvalList=new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthP = DensityUtil.getScreenWidth(getContext());
        int heightP = DensityUtil.getScreenHeight(getContext());
        int heightZ1=((int)Math.ceil(hashOvalList.size()/4))*(mRectY+mSpaceH);
        int heightZ2= (int)Math.ceil(hashOvalList.size()/(widthP/mRectX))*(mRectY+mSpaceH);
        heightZ = Math.max(heightZ1,heightZ2)+mSpaceH;
        if (heightZ <=0|| heightZ <heightP/2||hashOvalList.size()<=10){
            heightZ =heightP;
//            isHalfScreen=true;
        }
        setMeasuredDimension(widthP, heightZ);
        chartPait = new Paint();
        textPaint = new Paint();
        initAnimatorRed();
        initAnimatorGreen();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRect(canvas);
    }

    private void drawRect(Canvas canvas) {

        for (int i = 0; i < pointList.size(); i++) {
            HashOval hashOval=hashOvalList.get(i);
            Point point = pointList.get(i);

            //画圆角矩形
            float left =  point.getX();
            float rigth = point.getX() + mRectX;
            float  top = point.getY();
            float bottom = point.getY() + mRectY;
            if (isHalfScreen) {
                top = point.getY()+marginTop;
                bottom = point.getY() + mRectY+marginTop;
            }

            mRect = new RectF(left+pading, top+pading, rigth-pading, bottom-pading);

            chartPait.setStyle(Paint.Style.FILL);//充满
            chartPait.setColor(hashOval.getChartColor());
            chartPait.setAntiAlias(true);// 设置画笔的锯齿效果
            int red = (hashOval.getChartColor() & 0xff0000) >> 16;
            int green = (hashOval.getChartColor() & 0x00ff00) >> 8;
            int blue = (hashOval.getChartColor() & 0x0000ff);

            if (hashOval.getIsAlpha()==0) {
                chartPait.setARGB(animatedAplaGreen, red, green, blue);
            }else {
                chartPait.setARGB(animatedAplaRed, red, green, blue);
            }
            canvas.drawRoundRect(mRect, 20, 20, chartPait);//第二个参数是x半径，第三个参数是y半径
            //写字
            Rect bounds = new Rect();
            textPaint.setStyle(Paint.Style.FILL);//充满
            textPaint.setColor(hashOval.getTextColor());
            textPaint.setTextSize(DensityUtil.dip2px(getContext(),hashOval.getTextSize()));
            textPaint.setAntiAlias(true);// 设置画笔的锯齿效果

            textPaint.getTextBounds(hashOval.getText(), 0, hashOval.getText().length(), bounds);
            int textW = bounds.width();
            int textH = bounds.height();
//            Log.i("textw",textW+","+mRectX);
//            Log.i("textH",textH+","+mRectY);
            float tLeft=left+(mRectX-textW)/2;
            float tTop=top+(mRectY-textH);
            String text=hashOval.getText();
            if (text.length()>4){
                text=text.substring(0,4);
                textPaint.getTextBounds(text, 0, text.length(), bounds);
                textW = bounds.width();
                textH = bounds.height();

                tLeft=left+(mRectX-textW)/2;
                tTop=top+(mRectY-textH);
            }
            int textRed = (Color.WHITE & 0xff0000) >> 16;
            int textGreen = (Color.WHITE & 0x00ff00) >> 8;
            int textBlue = (Color.WHITE & 0x0000ff);
            if (hashOval.getIsAlpha()==0) {
                textPaint.setARGB(animatedAplaGreen, textRed, textGreen, textBlue);
            }else {
                textPaint.setARGB(animatedAplaRed, textRed, textGreen, textBlue);
            }
            canvas.drawText(text,tLeft,tTop,textPaint);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//         TODO Auto-generated method stub
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setClick(event.getX(),event.getY());
                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
        }

        return true;
    }

    public List<HashOval> getHashOvalList() {
        return hashOvalList;
    }

    public void setHashOvalList(List<HashOval> hashOvalList) {
        this.hashOvalList = hashOvalList;
//        pointList.clear();
        int widthP =DensityUtil.getScreenWidth(getContext());
        int heightP = DensityUtil.getScreenHeight(getContext());
        int heightZ1=((int)Math.ceil(hashOvalList.size()/4))*(mRectY+mSpaceH);
        int heightZ2= (int)Math.ceil(hashOvalList.size()/(widthP/mRectX))*(mRectY+mSpaceH);
        heightZ = Math.max(heightZ1,heightZ2)+mSpaceH;
        if (heightZ <=0|| heightZ <heightP/2||hashOvalList.size()<=10){
            heightZ =heightP;
            isHalfScreen=true;
        }
        pointList = ChartUtil.getPoints(widthP, heightZ, mRectX, mRectY, mSpaceH, hashOvalList.size());
//        invalidate();
        requestLayout();

    }
    private void setClick(float x,float y){
        for (int i = 0; i < pointList.size(); i++) {
            HashOval hashOval = hashOvalList.get(i);
            Point point = pointList.get(i);
            //矩形
            float left =  point.getX();
            float rigth = point.getX() + mRectX;
            float top = point.getY();
            float bottom = point.getY() + mRectY;
            if (isHalfScreen){
                 top = point.getY()+marginTop;
                 bottom = point.getY() + mRectY+marginTop;
            }

            if (x>left&x<rigth&y>top&y<bottom){
//                Toast.makeText(getContext(),hashOval.getText(),Toast.LENGTH_SHORT).show();
                onClickChartItem.setOnClickChartItem(hashOval);
            }

        }
    }

    public void setOnClickChartItem(Hash0valView.onClickChartItem onClickChartItem) {
        this.onClickChartItem = onClickChartItem;
    }

    public interface onClickChartItem{
        void setOnClickChartItem(HashOval hashOval);
    }

    public int getmRectX() {
        return mRectX;
    }

    public void setmRectX(int mRectX) {
        this.mRectX = DensityUtil.dip2px(getContext(),mRectX);
    }

    public int getmRectY() {
        return mRectY;
    }

    public void setmRectY(int mRectY) {
        this.mRectY = DensityUtil.dip2px(getContext(),mRectY);
    }

    public int getmSpaceH() {
        return mSpaceH;
    }

    public void setmSpaceH(int mSpaceH) {
        this.mSpaceH = mSpaceH;
    }

    public int getCharColor() {
        return charColor;
    }

    public void setCharColor(int charColor) {
        this.charColor = charColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
    private void initAnimatorRed() {

        //动画
        ValueAnimator alphaAnimation=ValueAnimator.ofInt(0,255);
//        AlphaAnimation alphaAnimation=new AlphaAnimation(0.1f,1.0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatedAplaRed = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        alphaAnimation.start();

    }
    private void initAnimatorGreen() {
        //动画
        ValueAnimator alphaAnimation=ValueAnimator.ofInt(0,80);
//        AlphaAnimation alphaAnimation=new AlphaAnimation(0.1f,1.0f);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatedAplaGreen = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        alphaAnimation.start();

    }
}

