package com.wondersgroup.testsdk.bubblechart;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.wondersgroup.testsdk.bubblechart.modle.HashOval;
import com.wondersgroup.testsdk.bubblechart.view.Hash0valView;

import java.util.ArrayList;
import java.util.List;

public class HashOvalActivity extends AppCompatActivity {
    Hash0valView hash0valView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_oval);
        hash0valView=(Hash0valView)findViewById(R.id.hashOvalView);
        List<HashOval> hashOvals=new ArrayList<>();
        for (int i=0;i<20;i++){
            HashOval hashOval;
            if (i%2==0) {
                hashOval = new HashOval(Color.RED,  "嘉定" + i, Color.WHITE, 12,1);
            }else {
                hashOval = new HashOval(getResources().getColor(R.color.colorPrimary),  "徐汇" + i, Color.WHITE, 12,0);
            }
            hashOvals.add(hashOval);
        }
        hash0valView.setHashOvalList(hashOvals);
        hash0valView.setmRectX(60);
        hash0valView.setmRectY(30);
        hash0valView.setOnClickChartItem(new Hash0valView.onClickChartItem() {
            @Override
            public void setOnClickChartItem(HashOval hashOval) {
                Toast.makeText(HashOvalActivity.this,hashOval.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
