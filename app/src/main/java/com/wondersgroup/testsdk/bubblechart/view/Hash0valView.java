package com.wondersgroup.testsdk.bubblechart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.wondersgroup.testsdk.bubblechart.modle.HashOval;
import com.wondersgroup.testsdk.bubblechart.modle.Point;
import com.wondersgroup.testsdk.bubblechart.util.ChartUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyaqing on 2017/5/8.
 */

public class Hash0valView extends View {
    private int width;
    private int height;
    private int mRectX = 140;
    private int mRectY = 100;
    private int mSpaceH = 100;
    private int mSpaceW = 5;
    private int charColor=Color.BLUE;
    private int textColor=Color.WHITE;
    private int textSize=14;
    private RectF mRect;
    private Paint chartPait;
    private Paint textPaint;
    private List<Point> pointList;
    private List<HashOval> hashOvalList;
    private onClickChartItem onClickChartItem;

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
        setMeasuredDimension(1075, 3000);
        width = getWidth();
        height = getHeight();
//        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);// 获得控件的宽度
//        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);//获得控件的高度
//        setMeasuredDimension(width, height);//设置宽和高
        chartPait = new Paint();
        textPaint = new Paint();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h ;
        Log.i("width",width+"");
        Log.i("height",height+"");
        pointList = ChartUtil.getPointsT(width, height, mRectX, mRectY, mSpaceH, hashOvalList.size());
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
            float top = point.getY();
            float bottom = point.getY() + mRectY;
            mRect = new RectF(left, top, rigth, bottom);

            chartPait.setStyle(Paint.Style.FILL);//充满
            chartPait.setColor(hashOval.getChartColor());
            chartPait.setAntiAlias(true);// 设置画笔的锯齿效果
            canvas.drawRoundRect(mRect, 20, 20, chartPait);//第二个参数是x半径，第三个参数是y半径
            //写字
            Rect bounds = new Rect();
            TextPaint paint=new TextPaint();

            paint.getTextBounds(hashOval.getText(), 0, hashOval.getText().length(), bounds);
            int textW = bounds.width();
            int textH = bounds.height();
            float tLeft=left+(mRectX-textW)/2;
            float tTop=top+(mRectY-textH)/2;
            textPaint.setStyle(Paint.Style.FILL);//充满
            textPaint.setColor(hashOval.getTextColor());
            textPaint.setTextSize(hashOval.getTextSize());
            textPaint.setAntiAlias(true);// 设置画笔的锯齿效果
            canvas.drawText(hashOval.getText(),tLeft,tTop,textPaint);

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
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    public List<HashOval> getHashOvalList() {
        return hashOvalList;
    }

    public void setHashOvalList(List<HashOval> hashOvalList) {
        this.hashOvalList = hashOvalList;
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
        void setOnClickChartItem( HashOval hashOval);
    }
}
