package com.wondersgroup.testsdk.bubblechart.view;

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
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.github.lzyzsd.randomcolor.RandomColor;
import com.wondersgroup.testsdk.bubblechart.util.DrawHelper;
import com.wondersgroup.testsdk.bubblechart.util.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by liyaqing on 2017/4/1.
 * 根据自定义圆环，完成随机漂浮的圆环
 */

public class BallView extends View {
    private String TAG = "RINGCHART";


    private int mStrokeWidth = 30;//圆的粗细

    private RectF oval;//圆的区域
    private int center;  //获取圆心的x坐标
    private int mRadius;//圆环的半径
    private Paint cyclePaint;//圆环画笔

    //    private Rect centTextRect;//中间字的区域
    private Paint centerTextPaint;//中间字画笔
    private String centerText = "嘉定区";
    private int centerTextSize = 40;//字体大小
    private Rect centerTextRect;
    private int centerTextColor;


    private Paint dataPaint;//圆环上数字画笔
    private int dataTextSize = 30;//数字字体大小
    private int dataTextColor= Color.argb(200, 56, 78, 9) ;//数字字体大小

    //数据
    private List<RingEntity> dataList = new ArrayList<RingEntity>();
    private float dataSum = 0;

    private int color[] = {Color.argb(200, 56, 718, 249), Color.argb(200, 156, 78, 49), Color.argb(200, 00, 78, 49),
            Color.argb(200, 56, 78, 9), Color.argb(200, 4, 78, 49), Color.argb(200, 56, 78, 0)};

    private float animatedRingValue;//环形动画
    private int animatedCenterTextValue;//字体动画
    private int animatedDataValue=0;
    private final Random mRandom;

    class Ball {
        int radius; // 半径
        float cx;   // 圆心
        float cy;   // 圆心
        float vx; // X轴速度
        float vy; // Y轴速度
        Paint paint;

        // 移动
        void move() {
            //向角度的方向移动，偏移圆心
            cx += vx;
            cy += vy;
        }

        int left() {
            return (int) (cx - radius);
        }

        int right() {
            return (int) (cx +radius);
        }

        int bottom() {
            return (int) (cy + radius);
        }

        int top() {
            return (int) (cy - radius);
        }
    }

    private int mCount = 10;   // 小球个数
    private int maxRadius;  // 小球最大半径
    private int minRadius; // 小球最小半径
    private int minSpeed = 5; // 小球最小移动速度
    private int maxSpeed = 20; // 小球最大移动速度

    private int mWidth = 200;
    private int mHeight = 200;


    public Ball[] mBalls;   // 用来保存所有小球的数组

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        RingEntity ringEntity=new RingEntity(color[0],1f,"",color[0]);
        dataList.add(ringEntity);
        // 初始化所有球(设置颜色和画笔, 初始化移动的角度)
        mRandom = new Random();
        RandomColor randomColor = new RandomColor(); // 随机生成好看的颜色，github开源库。
        mBalls = new Ball[mCount];

        for(int i=0; i< mCount; i++) {
            mBalls[i] = new Ball();
            // 设置画笔
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(randomColor.randomColor());
            paint.setStyle(Paint.Style.FILL);
            paint.setAlpha(180);
            paint.setStrokeWidth(0);

            // 设置速度
            float speedX = (mRandom.nextInt(maxSpeed -minSpeed +1)+5)/10f;
            float speedY = (mRandom.nextInt(maxSpeed -minSpeed +1)+5)/10f;
            mBalls[i].paint = paint;
            mBalls[i].vx = mRandom.nextBoolean() ? speedX : -speedX;
            mBalls[i].vy = mRandom.nextBoolean() ? speedY : -speedY;
        }

        // 圆心和半径测量的时候才设置
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = resolveSize(mWidth, widthMeasureSpec);
        mHeight = resolveSize(mHeight, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
        maxRadius = mWidth/12;
        minRadius = maxRadius/2;

        // 初始化圆的半径和圆心
        for (int i=0; i<mBalls.length; i++) {
            mBalls[i].radius = mRandom.nextInt(maxRadius+1 - minRadius) +minRadius;
//            mBalls[i].mass = (int) (Math.PI * mBalls[i].radius * mBalls[i].radius);
            // 初始化圆心的位置， x最小为 radius， 最大为mwidth- radius
            mBalls[i].cx = mRandom.nextInt(mWidth - mBalls[i].radius) + mBalls[i].radius;
            mBalls[i].cy = mRandom.nextInt(mHeight - mBalls[i].radius) + mBalls[i].radius;
        }

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
        long startTime = System.currentTimeMillis();

        // 先画出所有圆
        for (int i = 0; i < mCount; i++) {
            Ball ball = mBalls[i];
//            canvas.drawCircle(ball.cx, ball.cy, ball.radius, ball.paint);
            center=(int) ball.cx;
            oval = new RectF(ball.cx - ball.radius,ball.cy - ball.radius, ball.cx + ball.radius, ball.cy+ ball.radius);
            drawRing(canvas);
            drawCenterText(canvas,ball);
            drawDataText(canvas,ball);
        }

        // 球碰撞边界
        for (int i = 0; i < mCount; i++) {
            Ball ball = mBalls[i];
            collisionDetectingAndChangeSpeed(ball); // 碰撞边界的计算
            ball.move(); // 移动
        }

        long stopTime = System.currentTimeMillis();
        long runTime = stopTime - startTime;
        // 16毫秒执行一次
        postInvalidateDelayed(Math.abs(runTime -16));
    }



    // 判断球是否碰撞碰撞边界
    public void collisionDetectingAndChangeSpeed(Ball ball) {
        int left = getLeft();
        int top = getTop();
        int right = getRight();
        int bottom = getBottom();

        float speedX = ball.vx;
        float speedY = ball.vy;

        // 碰撞左右，X的速度取反。 speed的判断是防止重复检测碰撞，然后黏在墙上了=。=
        if(ball.left() <= left && speedX < 0) {
            ball.vx = -ball.vx;
        } else if(ball.top() <= top && speedY < 0) {
            ball.vy = -ball.vy;
        } else if(ball.right() >= right && speedX >0) {
            ball.vx = -ball.vx;
        } else if(ball.bottom() >= bottom && speedY >0) {
            ball.vy = -ball.vy;
        }
    }
    private void drawDataText(Canvas canvas,Ball ball) {
        dataPaint.setAntiAlias(true);
        dataPaint.setStyle(Paint.Style.FILL);
        dataPaint.setTextSize(dataTextSize);
        dataPaint.setStrokeWidth(1);
//        dataPaint.setAlpha(animatedDataValue);
//        dataPaint.setColor(dataTextColor);
        int red = (getDataTextColor() & 0xff0000) >> 16;
        int green = (getDataTextColor() & 0x00ff00) >> 8;
        int blue = (getDataTextColor() & 0x0000ff);
        dataPaint.setARGB(animatedDataValue, red, green, blue);

        float dataPre = 0;
        float dataCurr = 0;

        for (int i = 0; i < dataList.size(); i++) {
            if (i == 0) {
                dataPre = 0;
            } else {
                dataPre += (dataList.get(i - 1).getRingData() / sumData(dataList)) * 360;
            }
            Log.i(TAG, "dataPre: " + dataPre);
            dataCurr = (dataList.get(i).getRingData() / sumData(dataList)) * 360 + dataPre;
            Log.i(TAG, "dataCurr: " + dataCurr);

            //一四象限
            if (((dataPre + (dataCurr - dataPre) / 2)>=0&&(dataPre + (dataCurr - dataPre) / 2)<90)||((dataPre + (dataCurr - dataPre) / 2)>=270&&(dataPre + (dataCurr - dataPre) / 2)<360)){
                PointF point2 = MathHelper.getInstance().calcArcEndPointXY(
                        ball.cx, ball.cy, ball.radius , dataPre + (dataCurr - dataPre) / 2);
                //标识2
                DrawHelper.getInstance().drawRotateText(String.valueOf(dataList.get(i).getRingText()), point2.x, point2.y, 0,
                        canvas, dataPaint);
            }else {
                //二三象限
                PointF point2 = MathHelper.getInstance().calcArcEndPointXY(
                        ball.cx, ball.cy, ball.radius, dataPre + (dataCurr - dataPre) / 2);
                //标识2
                DrawHelper.getInstance().drawRotateText(String.valueOf(dataList.get(i).getRingText()), point2.x, point2.y, 0,
                        canvas, dataPaint);
            }
        }

    }

    private void drawCenterText(Canvas canvas,Ball ball) {
        centerTextPaint.setAntiAlias(true);
        centerTextPaint.setStyle(Paint.Style.FILL);
        centerTextPaint.setTextSize(centerTextSize);
        centerTextPaint.setStrokeWidth(1);
        int red = (getCenterTextColor() & 0xff0000) >> 16;
        int green = (getCenterTextColor() & 0x00ff00) >> 8;
        int blue = (getCenterTextColor() & 0x0000ff);
        centerTextPaint.setARGB(animatedCenterTextValue, red, green, blue);
        canvas.drawText(centerText, ball.cx - centerTextPaint.measureText(centerText) / 2, ball.cy,centerTextPaint);
//        canvas.drawT
    }

    private void drawRing(Canvas canvas) {

        float startPercent = 0;
        float sweepPercent = 0;

        for (int i = 0; i < dataList.size(); i++) {

            float bfb = dataList.get(i).getRingData() / sumData(dataList);
            Log.i(TAG, dataList.get(i) + ", " + sumData(dataList) + "," + dataList.get(i).getRingData() / sumData(dataList));
            sweepPercent = bfb * 360;
            Log.i(TAG, "sweepPercent: " + sweepPercent);
            //第一段
            cyclePaint.setAntiAlias(true);
            cyclePaint.setStyle(Paint.Style.STROKE);
            cyclePaint.setStrokeWidth(mStrokeWidth);
            cyclePaint.setColor(dataList.get(i).getRingColor());
            if (Math.min(sweepPercent - 1, animatedRingValue - startPercent) >= 0) {
                canvas.drawArc(oval, startPercent, Math.min(sweepPercent - 1, animatedRingValue - startPercent), false, cyclePaint);
//                drawText(canvas,sweepPercent/2+startPercent,dataList.get(i)+"",sweepPercent);
            }
            startPercent += sweepPercent;
        }
    }
    private float sumData(List<RingEntity> list) {
        dataSum = 0;
        for (int i = 0; i < list.size(); i++) {
            dataSum += list.get(i).getRingData();
        }
        return dataSum;
    }

    public void setDataList(List<RingEntity> dataList) {
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
    public int getmStrokeWidth() {
        return mStrokeWidth;
    }

    public void setmStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
    }

    public Paint getDataPaint() {
        return dataPaint;
    }

    public void setDataPaint(Paint dataPaint) {
        this.dataPaint = dataPaint;
    }

    public Paint getCenterTextPaint() {
        return centerTextPaint;
    }

    public void setCenterTextPaint(Paint centerTextPaint) {
        this.centerTextPaint = centerTextPaint;
    }

    public Paint getCyclePaint() {
        return cyclePaint;
    }

    public void setCyclePaint(Paint cyclePaint) {
        this.cyclePaint = cyclePaint;
    }

    public String getCenterText() {
        return centerText;
    }

    public void setCenterText(String centerText) {
        this.centerText = centerText;
    }

    public float getCenterTextSize() {
        return centerTextSize;
    }

    public void setCenterTextSize(int centerTextSize) {
        this.centerTextSize = centerTextSize;
    }

    public float getDataTextSize() {
        return dataTextSize;
    }

    public void setDataTextSize(int dataTextSize) {
        this.dataTextSize = dataTextSize;
    }

    public int getCenterTextColor() {
        return centerTextColor;
    }

    public void setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
    }

    public int getDataTextColor() {
        return dataTextColor;
    }

    public void setDataTextColor(int dataTextColor) {
        this.dataTextColor = dataTextColor;
    }
}
