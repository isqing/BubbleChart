package com.wondersgroup.testsdk.bubblechart.util;

import android.util.Log;

import com.wondersgroup.testsdk.bubblechart.modle.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by liyaqing on 2017/5/9.
 */

public class ChartUtil {


    /**
     *
     * @param w 布局宽度
     * @param h 布局高度
     * @param mRectX 图形宽度
     * @param mRectY 图形高度
     * @param mSpaceH 行直接的间隔
     * @param amount 图形个数
     * @return 每个图形的(x,y)集合
     */
    public static List<Point> getPoints(int w, int h, int mRectX, int mRectY, int mSpaceH, int amount){
        List<Point> pointList=new ArrayList<>();
        int maxCol =(int)Math.floor(w/mRectX);//最大列数
        int maxRows =(int)Math.ceil(h/(mRectY+mSpaceH));//最大行数
        int mSum=0;//累计每次生成的个数
        Map<Integer,Integer> numberMap=new HashMap<>();
        while (mSum<amount) {
            for (int i = 0; i < maxRows; i++) {
                int mPreRandom=0;//上次循环生成的值
                if (numberMap.containsKey(i)){
                    mPreRandom=numberMap.get(i);
                }
//                Log.i("maxCol", "getPoints: "+maxCol);
//                Log.i("mPreRandom", "getPoints: "+mPreRandom);
//                int mRandom = new Random().nextInt(0) * (maxCol-mPreRandom + 1);//生成0到maxCol，随机生成的每行的个数
                int mRandom = new Random().nextInt(maxCol-mPreRandom)+1 ;//生成0到maxCol，随机生成的每行的个数
                //当生成的随机数和之前的和大于图形总个数时
                if (mSum + mRandom >= amount) {
                    mRandom = amount - mSum;
                    mSum = amount;
                    numberMap.put(i,mRandom+mPreRandom);
                    break ;
                } else {
                    mSum += mRandom;
                    numberMap.put(i,mRandom+mPreRandom);
                }
            }
        }
//        Iterator it = numberMap.entrySet().iterator();
//        while(it.hasNext()){
//            Map.Entry entry = (java.util.Map.Entry)it.next();
//            entry.getKey() ; //返回对应的键
////            entry.getValue();//返回对应的值
//        }
        for (int i=0;i<numberMap.size();i++){
            int col=numberMap.get(i);//第i行的个数
            for (int j=0;j<col;j++) {
                float averageW = w / col;//取平均值
                //计算x的范围
                float startX=j*averageW;
                float endX=(j+1)*averageW-mRectX;
                //计算y的范围
                float startY=i*(mRectY+mSpaceH);
                float endY=(i+1)*(mRectY+mSpaceH)-mRectY;

                float mRandomX =startX + ((endX - startX) * new Random().nextFloat());//x坐标
                float mRandomY =startY + ((endY - startY) * new Random().nextFloat());//x坐标
                Point point=new Point(mRandomX,mRandomY);
                pointList.add(point);
            }
        }
        return pointList;
    }
    public static List<Point> getPointsT(int w, int h, int mRectX, int mRectY,int mSpaceH, int amount){
        List<Point> pointList=new ArrayList<>();
        int maxCol =(int)Math.floor(w/mRectX);//最大列数
        int maxRows =(int)Math.ceil(h/(mRectY+mSpaceH));//最大行数
        int mSum=0;//累计每次生成的个数
        Map<Integer,Integer> numberMap=new HashMap<>();
        Log.i("maxCol",maxCol+"");
        Log.i("maxRows",maxRows+"");
        while (mSum<amount) {
            for (int i = 0; i < maxRows; i++) {
                int mPreRandom=0;//上次循环生成的值
                if (numberMap.containsKey(i)){
                    mPreRandom=numberMap.get(i);
                }
//                Log.i("maxCol", "getPoints: "+maxCol);
//                Log.i("mPreRandom", "getPoints: "+mPreRandom);
//                int mRandom = new Random().nextInt(0) * (maxCol-mPreRandom + 1);//生成0到maxCol，随机生成的每行的个数
                int mRandom = new Random().nextInt(maxCol-mPreRandom)+1 ;//生成0到maxCol，随机生成的每行的个数
                //当生成的随机数和之前的和大于图形总个数时
                if (mSum + mRandom >= amount) {
                    mRandom = amount - mSum;
                    mSum = amount;
                    numberMap.put(i,mRandom+mPreRandom);
                    break ;
                } else {
                    mSum += mRandom;
                    numberMap.put(i,mRandom+mPreRandom);
                }
            }
        }
//        Iterator it = numberMap.entrySet().iterator();
//        while(it.hasNext()){
//            Map.Entry entry = (java.util.Map.Entry)it.next();
//            entry.getKey() ; //返回对应的键
////            entry.getValue();//返回对应的值
//        }
        for (int i=0;i<numberMap.size();i++){
            int col=numberMap.get(i);//第i行的个数
            for (int j=0;j<col;j++) {
                float averageW = w / col;//取平均值
                //计算x的范围
                float startX=j*averageW;
                float endX=(j+1)*averageW-mRectX;
                //计算y的范围
                float startY=i*(mRectY+mSpaceH);
                float endY=(i+1)*(mRectY+mSpaceH)-mRectY;

                float mRandomX =startX + ((endX - startX) * new Random().nextFloat());//x坐标
                float mRandomY =startY + ((endY - startY) * new Random().nextFloat());//x坐标
                Point point=new Point(mRandomX,mRandomY);
                pointList.add(point);
            }
        }
        return pointList;
    }
}
