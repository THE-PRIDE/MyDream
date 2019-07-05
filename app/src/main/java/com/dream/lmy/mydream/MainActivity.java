package com.dream.lmy.mydream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dream.lmy.mydream.adapter.QueryLocationAdapter;
import com.dream.lmy.mydream.common.DeviceParamUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtInput;
    private Button mBtnConfirm;
    private Button mBtnGetLocation;
    private RecyclerView mRlyShowData;
    private ImageView imageview;

    private List<String> allLocationData;
    private List<String> queryLocationData;
    private QueryLocationAdapter queryLocationAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qurey_layout);

        initView();
        initData();
        initListener();
//        DeviceParamUtils.getDeviceParam(this);
    }

    void initView() {
        mEtInput = findViewById(R.id.et_input);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mBtnGetLocation = findViewById(R.id.btn_get_location);
        mRlyShowData = findViewById(R.id.ryl_local_data);
        imageview = findViewById(R.id.imageview);
    }

    void initData() {
        allLocationData = new ArrayList<>();
        queryLocationData = new ArrayList<>();
        try {
            readTextFile(Environment.getExternalStorageDirectory() + "/地址列表.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRlyShowData.setLayoutManager(layoutManager);
        queryLocationAdapter = new QueryLocationAdapter(allLocationData);
        mRlyShowData.setAdapter(queryLocationAdapter);
    }

    void initListener() {
        mBtnConfirm.setOnClickListener(this);
        mBtnGetLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                queryDimData();
                break;
            case R.id.btn_get_location:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
//                getLocation();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");
        imageview.setImageBitmap(bitmap);
    }

    private void queryDimData() {
        queryLocationData.clear();
        String inputData = mEtInput.getText().toString().trim();

        for (String data : allLocationData) {
            String subData;
            if (!"".equals(data.trim()) && data.contains("--")) {
                subData = data.substring(0, data.indexOf("--")).trim();
                if (subData.contains(inputData) || inputData.contains(subData)) {
                    queryLocationData.add(data);
                }
            }
        }
        if ("".equals(inputData.trim())){
            queryLocationAdapter.notifyListUpdate(allLocationData);
        } else {
            queryLocationAdapter.notifyListUpdate(queryLocationData);
        }
        mRlyShowData.setAdapter(queryLocationAdapter);

    }

    /**
     * 获取位置信息
     */
    @SuppressLint("MissingPermission")
    private void getLocation() {
        LocationListener locationListener;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location;
        String provider;
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);//高精度
        criteria.setAltitudeRequired(false);//不要求海拔
        criteria.setBearingRequired(false);//不要求方位
        criteria.setCostAllowed(true);//允许有花费
        criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗

        //从可用的位置提供器中，匹配以上标准的最佳提供器
        provider = locationManager.getBestProvider(criteria, true);
        //获得最后一次变化的位置
        location = locationManager.getLastKnownLocation(provider);
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        Log.e("TEST", "lat:" + lat);
        Log.e("TEST", "lon:" + lon);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                Log.e("TEST", "lat--listener:" + lat);
                Log.e("TEST", "lon--listener:" + lon);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);

    }


    /**
     * 读取本地Text文件
     *
     * @param filePath 本地Text文件路径
     */
    private void readTextFile(String filePath) throws IOException {
//        String encoding = "GBK";
        String encoding = "UTF-8";
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), encoding);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineText;
            while ((lineText = bufferedReader.readLine()) != null) {
                stringBuilder.append(lineText);
                if (!"".equals(lineText.trim())) {
                    allLocationData.add(lineText);
                }
            }
            reader.close();
        }
    }


}
