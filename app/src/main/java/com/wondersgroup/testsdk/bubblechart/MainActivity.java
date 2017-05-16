package com.wondersgroup.testsdk.bubblechart;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.wondersgroup.testsdk.bubblechart.modle.HashOval;
import com.wondersgroup.testsdk.bubblechart.modle.Point;
import com.wondersgroup.testsdk.bubblechart.util.ChartUtil;
import com.wondersgroup.testsdk.bubblechart.view.BallView;
import com.wondersgroup.testsdk.bubblechart.view.Hash0valView;
import com.wondersgroup.testsdk.bubblechart.view.RingChart;
import com.wondersgroup.testsdk.bubblechart.view.RingEntity;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    Hash0valView hash0valView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hash0valView=(Hash0valView)findViewById(R.id.hashOvalView);
        List<HashOval> hashOvals=new ArrayList<>();
        for (int i=0;i<60;i++){
            HashOval hashOval;
            if (i%2==0) {
                 hashOval = new HashOval(Color.RED,  "嘉定" + i, Color.WHITE, 12);
            }else {
                hashOval = new HashOval(Color.BLUE,  "徐汇" + i, Color.WHITE, 12);
            }
            hashOvals.add(hashOval);
        }
        hash0valView.setHashOvalList(hashOvals);
        hash0valView.setmRectX(60);
        hash0valView.setmRectY(40);
        hash0valView.setOnClickChartItem(new Hash0valView.onClickChartItem() {
            @Override
            public void setOnClickChartItem(HashOval hashOval) {
                Toast.makeText(MainActivity.this,hashOval.getText(),Toast.LENGTH_SHORT).show();
            }
        });
 //        List<Point> pointList=new ArrayList<>();
//        pointList.addAll(ChartUtil.getPoints(600,800,60,40,10,50));
//        Log.i("pointSize", ""+pointList.size());
//        for (int i=0;i<pointList.size();i++){
//            Point p=pointList.get(i);
//           Log.i("pointC"+i, ""+p.getX()+"==="+p.getY());
//        }

//        RoundProgressBar roundProgressBar=(RoundProgressBar)findViewById(R.id.roundProgressBar2);
//        roundProgressBar.setProgress(70);


//        BallView ringChart=(BallView)findViewById(R.id.roundProgressBar2);
//        List<RingEntity> datas=new ArrayList<RingEntity>();
//        RingEntity r1=new RingEntity(getResources().getColor(R.color.colorAccent),(float)78,"78",getResources().getColor(R.color.colorPrimary));
//        RingEntity r2=new RingEntity(getResources().getColor(R.color.colorPrimary),(float)178,"178",getResources().getColor(R.color.d1));
//        datas.add(r1);
//        datas.add(r2);
//        ringChart.setDataList(datas);
//        ringChart.setCenterText("测试");
//        ringChart.setCenterTextColor(getResources().getColor(R.color.d2));
//        ringChart.setDataTextColor(getResources().getColor(R.color.d2));
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        // TODO Auto-generated method stub
//        super.onTouchEvent(event);
//        Log.i("myevent",event.getAction()+"");
//        float x=0,y=0;
//        float moveX=0,moveY=0;
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                x = event.getX();//float DownX
//                y = event.getY();//float DownY
//                break;
//            case MotionEvent.ACTION_MOVE:
//                moveX = event.getX() - x;//X轴距离
//                moveY = event.getY() - y;//y轴距离
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        hash0valView.scrollTo((int)0,(int)moveY);
//
//        Log.i("myscroll:","x:"+moveX+",y:"+moveX);
//        return true;
//    }
}
