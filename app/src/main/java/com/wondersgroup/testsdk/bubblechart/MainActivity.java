package com.wondersgroup.testsdk.bubblechart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        RoundProgressBar roundProgressBar=(RoundProgressBar)findViewById(R.id.roundProgressBar2);
//        roundProgressBar.setProgress(70);
        RingChart ringChart=(RingChart)findViewById(R.id.roundProgressBar2);
        List<Float> datas=new ArrayList<>();
        datas.add(45f);
        datas.add(45f);
        datas.add(45f);
        datas.add(45f);
        ringChart.setDataList(datas);
    }
}
