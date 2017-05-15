package com.wondersgroup.testsdk.bubblechart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.ScrollView;
import android.widget.Toast;

import com.wondersgroup.testsdk.bubblechart.modle.Point;
import com.wondersgroup.testsdk.bubblechart.util.ChartUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyaqing on 2017/5/11.
 */

public class ScrollHashOvalView extends ScrollView {
    private int width;
    private int height;
    private int mRectX=140;
    private int mRectY=100;
    private int   mSpaceH=100;
    private int   mSpaceW=5;
    private RectF mRect;
    private Paint mPait;
    private List<Point> pointList;

    public ScrollHashOvalView(Context context) {
        this(context,null);
    }

    public ScrollHashOvalView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public ScrollHashOvalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=getWidth();
        height=getHeight()*2;
        mPait=new Paint();
        pointList=new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        this.width=w;
//        this.height=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRect(canvas);
    }
    private void drawRect(Canvas canvas){
        Log.i("width", "drawRect: "+width);
        Log.i("height", "drawRect: "+height);
//        mRectX=width-(width/mRectX)*mSpaceW;
        Log.i("mRectX", "drawRect: "+mRectX);
        List<Point> points= ChartUtil.getPointsT(width,height,mRectX,mRectY,mSpaceH,50);
        if (points!=null&&points.size()>0){
            pointList.addAll(points);
        }
        for (int i=0;i<pointList.size();i++){
            Point point=pointList.get(i);
            float left=point.getX();
            float rigth=point.getX()+mRectX;
            float top=point.getY();
            float bottom=point.getY()+mRectY;
            mRect=new RectF(left,top,rigth,bottom);

            //画圆角矩形
            mPait.setStyle(Paint.Style.FILL);//充满
            mPait.setColor(Color.BLUE);
            mPait.setAntiAlias(true);// 设置画笔的锯齿效果
            canvas.drawRoundRect(mRect, 20, 15, mPait);//第二个参数是x半径，第三个参数是y半径
//            HorizontalScrollView

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        this.smoothScrollTo(0, getHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        Toast.makeText(getContext(),"motion",Toast.LENGTH_SHORT).show();
//        Log.i("MYEVENT",ev.getAction()+","+MotionEvent.ACTION_MOVE+","+MotionEvent.ACTION_DOWN);
//            scrollTo(0,2000);
//        if(ev.getAction() == MotionEvent.ACTION_UP) {
//            float x=getScrollX();
//            float y=getScaleY();
//            Toast.makeText(getContext(),"x:"+x+",y:"+y,Toast.LENGTH_SHORT).show();
//            scrollTo((int)x,(int)(getHeight()+y));
//        }
        return super.onTouchEvent(ev);
    }
}
