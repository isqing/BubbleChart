package com.wondersgroup.testsdk.bubblechart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button hashOvalView;
    Button ballview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hashOvalView=(Button) findViewById(R.id.hashOvalView);
        ballview=(Button) findViewById(R.id.ballview);
        hashOvalView.setOnClickListener(this);
        ballview.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hashOvalView :
                Intent intent=new Intent(MainActivity.this,HashOvalActivity.class);
                startActivity(intent);
            break;
            case R.id.ballview :
                Intent intent1=new Intent(MainActivity.this,BallActivity.class);
                startActivity(intent1);
            break;
        }
    }

}
