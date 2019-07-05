package com.dream.lmy.mydream.screenShot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dream.lmy.mydream.R;

public class ScreenShotListenerActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshot);
        initView();
        initData();
        initListener();
    }

    void initView(){

    }

    void initData(){

    }

    void initListener(){

    }


    @Override
    public void onClick(View v) {

    }
}
