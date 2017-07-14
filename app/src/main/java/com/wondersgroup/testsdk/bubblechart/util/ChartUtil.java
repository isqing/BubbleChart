package com.wondersgroup.testsdk.bubblechart.util;


import com.wondersgroup.testsdk.bubblechart.modle.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by liyaqing on 2017/5/9.
 */

public class ChartUtil {



    public static List<Point> getPoints(int w, int h, int mRectX, int mRectY, int mSpaceH, int amount){
        List<Point> pointList=new ArrayList<>();
        int maxRows =(int)Math.ceil(h/(mRectY+mSpaceH));//最大行数
        int maxCol =Math.min(5,w/mRectX);//最大列数
        int mSum=0;//累计每次生成的个数
        Map<Integer,Integer> numberMap=new HashMap<>();
        while (mSum<amount) {
            for (int i = 0; i < maxRows; i++) {
                int mPreRandom=0;//上次循环生成的值
                if (numberMap.containsKey(i)){
                    mPreRandom=numberMap.get(i);
                }

                int mRandom =0;//随机生成一个的个数
                if(maxCol>mPreRandom){
                    mRandom = new Random().nextInt(maxCol-mPreRandom + 1-1)+1 ;//生成1到maxCol，随机生成的每行的个数
                }else {
                    mRandom=0;//当前行的个数超过最大个是
                }


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
            int overW=w;//计算剩余的宽度
            float averageW = w / col;//第一次取平均值
            float startX=0;
            float endX=0;
            float mRandomX=0;
            for (int j=0;j<col;j++) {

                if (j==0){
                    startX=0;
                    endX=averageW- mRectX;
                }else {
                    averageW=overW/(col-j);//计算剩余的平均宽度
                    startX = mRandomX+mRectX;
                    endX = mRandomX+mRectX + averageW - mRectX-10;//10为间隔
//                    Log.i("startX",startX+"==="+endX);
                }

                //计算y的范围
                float startY=i*(mRectY+mSpaceH);
                float endY=(i+1)*(mRectY+mSpaceH)-mRectY;

                mRandomX=startX + ((endX - startX) * new Random().nextFloat());//x坐标
                float mRandomY =startY + ((endY - startY) * new Random().nextFloat());//Y坐标
//                Log.i("mRandomX",mRandomX+"==="+mRectX+"==="+overW);
                overW=(int)(w-(mRandomX+mRectX));
//                Log.i("overW",""+overW);
                Point point=new Point(mRandomX,mRandomY);
                pointList.add(point);
            }
        }
        return pointList;
    }
}
