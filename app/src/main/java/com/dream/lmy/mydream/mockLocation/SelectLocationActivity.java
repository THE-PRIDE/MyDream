package com.dream.lmy.mydream.mockLocation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Toast;

import com.dream.lmy.mydream.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectLocationActivity extends AppCompatActivity implements IMocKClickListener {

    private RecyclerView mRecySelect;
    private LocationAdapter locationAdapter;

    private List<String> locationList = new ArrayList<>();
    private String selectLocation = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        initView();
        initData();
    }

    void initView() {
        mRecySelect = findViewById(R.id.recy_select_location);
    }

    void initData() {
        locationAdapter = new LocationAdapter(this, locationList);
        locationAdapter.setMockClickListener(this);
        mRecySelect.setLayoutManager(new LinearLayoutManager(this));
        mRecySelect.setAdapter(locationAdapter);
        getLocationData();
    }

    /**
     * 解析数据
     */
    void getLocationData() {
        if (getDataFromSD()) {
            locationAdapter.notifyMockData(locationList);
        } else {
           Toast.makeText(SelectLocationActivity.this,"位置文件不存在",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void mockListener(int position) {
        selectLocation = locationList.get(position);
        if (!TextUtils.isEmpty(selectLocation)) {
            Intent intent = new Intent();
            intent.putExtra("location", selectLocation);
            setResult(RESULT_OK, intent);
            SelectLocationActivity.this.finish();
        } else {
            setResult(RESULT_CANCELED);
            SelectLocationActivity.this.finish();
        }
    }

    private boolean getDataFromSD() {
        String path = Environment.getExternalStorageDirectory() + "/test.txt";
        File file = new File(path);
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        String lineStr = null;
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            while ((lineStr = reader.readLine()) != null) {
                locationList.add(lineStr);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fileInputStream) fileInputStream.close();
                if (null != inputStreamReader) inputStreamReader.close();
                if (null != reader) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
