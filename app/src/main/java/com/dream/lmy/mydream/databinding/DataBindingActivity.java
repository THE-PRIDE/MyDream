package com.dream.lmy.mydream.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dream.lmy.mydream.R;

public class DataBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_databinding);

        DataBindingActivity bindingActivity;

        DataBindingUtil.setContentView(this,R.layout.activity_databinding);
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
}
