package com.dream.lmy.mydream.database;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.dream.lmy.mydream.R;

public class DataBaseActivity extends AppCompatActivity {

    private Button mBtnCreateDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_database_layout);
        initView();
        initData();
        initListener();
    }

    void initView(){
        mBtnCreateDatabase = findViewById(R.id.btn_create_database);
    }

    void initData(){

    }

    void initListener(){

    }

    private void createDatabase(){
        MySQLiteHelper helper = new MySQLiteHelper(this,"news_data.db",null,1);
        SQLiteDatabase database = helper.getWritableDatabase();// 获取database实例，数据库即会创建


    }

}
