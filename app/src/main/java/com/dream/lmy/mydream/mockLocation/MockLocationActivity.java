package com.dream.lmy.mydream.mockLocation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.lmy.mydream.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class MockLocationActivity extends AppCompatActivity implements View.OnClickListener {
    LocationManager locationManager;

    private EditText mEtLatitude;
    private EditText mEtLongitude;
    private Button mBtnStart;
    private Button mBtnClear;

    private EditText mEtLocation;
    private Button mBtnSelectLocation;

    private TextView mTvCurrentLat;
    private TextView mTvCurrentLong;

    private double latitude = 39.939859;
    private double longitude = 116.4912;

    private double addStep = 0.0003;// 每次移动的跨度
    private EditText mEtMockStep;

    private Button mBtnOpen;
    private Button mBtnClose;

    private TextView mTvFloatUp;
    private TextView mTvFloatLeft;
    private TextView mTvFloatRight;
    private TextView mTvFloatDown;
    private TextView mTvFloatMove;
    boolean hasAddTestProvider = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_location);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        initView();
        initData();
        initListener();
    }

    void initView() {
        mEtLatitude = findViewById(R.id.et_latitude);
        mEtLongitude = findViewById(R.id.et_longitude);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnClear = findViewById(R.id.btn_clear);
        mEtLocation = findViewById(R.id.et_location);
        mBtnSelectLocation = findViewById(R.id.btn_select_mock_location);
        mTvCurrentLat = findViewById(R.id.tv_current_latitude);
        mTvCurrentLong = findViewById(R.id.tv_current_longitude);
        mEtMockStep = findViewById(R.id.et_mock_step);
        mBtnOpen = findViewById(R.id.btn_open);
        mBtnClose = findViewById(R.id.btn_close);
    }

    void initData() {
        String filePath = Environment.getExternalStorageDirectory() + "/test.txt";
        File file = new File(filePath);
        if (file.exists()) {
            return;
        }
//        InputStreamReader reader = new InputStreamReader(inputStream);
//        BufferedReader bufferedReader = new BufferedReader(reader);
//        File file = new File(filePath);
//        file.createNewFile();
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = getAssets().open("test.txt");
            fileOutputStream = new FileOutputStream(filePath);
            byte[] line = new byte[1024];
            int length = inputStream.read(line);

            while (length != -1) {
                fileOutputStream.write(line, 0, length);
                length = inputStream.read(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    void initListener() {
        mBtnClear.setOnClickListener(this);
        mBtnStart.setOnClickListener(this);
        mBtnSelectLocation.setOnClickListener(this);
        mBtnOpen.setOnClickListener(this);
        mBtnClose.setOnClickListener(this);
        try {
            hasAddTestProvider();
        } catch (Exception e) {
            LogUtils.writeLogToSd(e.getMessage());
        }
    }

    private boolean hasAddTestProvider() {
        LogUtils.writeLogToSd("provider_begin");
        boolean canMockPosition = true;
        try {
            canMockPosition = Settings.Secure.getInt(getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION, 0) != 0 || Build.VERSION.SDK_INT > 22;
            LogUtils.writeLogToSd("canMockPosition");
        } catch (Exception e) {
            LogUtils.writeLogToSd("setting" + e.getMessage());
        }
        if (canMockPosition) {
            String providerStr = locationManager.GPS_PROVIDER;
            LocationProvider provider = null;
            try {
                LogUtils.writeLogToSd("TEST_gps" + locationManager.isProviderEnabled(locationManager.GPS_PROVIDER));
                LogUtils.writeLogToSd("TEST_net" + locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER));
                provider = locationManager.getProvider(providerStr);
            } catch (Exception e) {
                LogUtils.writeLogToSd(e.getMessage());
            }
            if (provider != null) {
                try {
                    LogUtils.writeLogToSd("provider not null");
                    locationManager.addTestProvider(
                            provider.getName()
                            , provider.requiresNetwork()
                            , provider.requiresSatellite()
                            , provider.requiresCell()
                            , provider.hasMonetaryCost()
                            , provider.supportsAltitude()
                            , provider.supportsSpeed()
                            , provider.supportsBearing()
                            , provider.getPowerRequirement()
                            , provider.getAccuracy());
                } catch (Exception e) {
                    LogUtils.writeLogToSd("add" + e.getMessage());
                }
            } else {
                try {
                    LogUtils.writeLogToSd("provider is null");
                    locationManager.addTestProvider(providerStr, true, true, false, false, true, true, true, Criteria.POWER_HIGH,
                            Criteria.ACCURACY_FINE);
                } catch (Exception e) {
                    LogUtils.writeLogToSd(e.getMessage());
                }
            }
            locationManager.setTestProviderEnabled(providerStr, true);
            locationManager.setTestProviderStatus(providerStr, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
            LogUtils.writeLogToSd("provider_end_true");
            hasAddTestProvider = true;
            return true;
        }
        LogUtils.writeLogToSd("provider_end_false");
        hasAddTestProvider = false;
        return false;
    }

    //解析类型  1.111,2.222
    private void parseLocation() {
        if (!TextUtils.isEmpty(mEtLocation.getText())) {
            String location = mEtLocation.getText().toString().trim();
            String[] locationArry = location.split(",");
            if (locationArry.length == 2) {
                latitude = changeString(locationArry[0]);
                longitude = changeString(locationArry[1]);
            } else {
                Toast.makeText(MockLocationActivity.this, "输入有误", Toast.LENGTH_SHORT).show();
            }
        } else {
            latitude = changeString(mEtLatitude.getText().toString().trim());
            longitude = changeString(mEtLongitude.getText().toString().trim());
        }
    }

    // 解析类型  名字:1.111,2.222
    private void parseLocation(String locationData) {
        if (!TextUtils.isEmpty(locationData)) {
            String[] dataArray;
            if (locationData.contains(":")) {
                dataArray = locationData.split(":");
            } else {
                dataArray = locationData.split("：");
            }
            String[] locationArry = dataArray[1].split(",");
            if (locationArry.length == 2) {
                latitude = changeString(locationArry[0].trim());
                longitude = changeString(locationArry[1].trim());
            } else {
                Toast.makeText(MockLocationActivity.this, "输入有误", Toast.LENGTH_SHORT).show();
            }
        } else {
            latitude = changeString(mEtLatitude.getText().toString().trim());
            longitude = changeString(mEtLongitude.getText().toString().trim());
        }
    }

    private Double changeString(String string) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            Toast.makeText(MockLocationActivity.this, "输入有误", Toast.LENGTH_SHORT).show();
            return 0.0;
        }
    }

    /**
     * 开启线程
     */
    private void startMockLocation() {
        mTvCurrentLat.setText("当前模拟纬度: " + latitude);
        mTvCurrentLong.setText("当前模拟经度: " + longitude);
        if (hasAddTestProvider) {
            Thread thread = new Thread(new RunnableMockLocation());
            thread.setName("mock_location");
            thread.start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                clearInput();
                break;
            case R.id.btn_start:
                parseLocation();
                startMockLocation();
                break;
            case R.id.btn_select_mock_location:
                jumpSelectLocation();
                break;
            case R.id.btn_open:
                openWindow();
                break;
            case R.id.btn_close:
                closeWinView();
                break;
            case R.id.img_float_up:
                addStep = changeString(mEtMockStep.getText().toString().trim());
                latitude += addStep;
                break;
            case R.id.img_float_left:
                addStep = changeString(mEtMockStep.getText().toString().trim());
                longitude -= addStep;
                break;
            case R.id.img_float_right:
                addStep = changeString(mEtMockStep.getText().toString().trim());
                longitude += addStep;
                break;
            case R.id.img_float_down:
                addStep = changeString(mEtMockStep.getText().toString().trim());
                latitude -= addStep;
                break;
            default:
        }
    }

    /**
     * 跳转选择界面
     */
    private void jumpSelectLocation() {
        Intent intent = new Intent(this, SelectLocationActivity.class);
        startActivityForResult(intent, 12);
    }

    /**
     * 清除输入信息
     */
    private void clearInput() {
        mEtLatitude.setText("");
        mEtLongitude.setText("");
        mEtLocation.setText("");
        //        mEtMockStep.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 12) {
            parseLocation(data.getStringExtra("location"));
            startMockLocation();
        } else if (requestCode == 12 && resultCode == RESULT_CANCELED) {
            Toast.makeText(MockLocationActivity.this, "未选择地址", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 22) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            } else {
                createWinView();
            }
        }
    }

    /**
     * 设置模拟location
     */
    private class RunnableMockLocation implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    String providerStr = LocationManager.GPS_PROVIDER;
                    Location mockLocation = new Location(providerStr);
                    mockLocation.setLatitude(latitude);//维度
                    mockLocation.setLongitude(longitude);//经度
                    mockLocation.setAltitude(30);//高度
                    mockLocation.setBearing(180);// 方向
                    mockLocation.setSpeed(10);// 速度
                    mockLocation.setAccuracy(0.1f);// 精度（米）
                    mockLocation.setTime(new Date().getTime());
                    mockLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                    locationManager.setTestProviderLocation(providerStr, mockLocation);
                    LogUtils.writeLogToSd("mock_begin");

                    @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(providerStr);
                    Log.e("LOCATION", "MOCK_LOCATION" + location.getLatitude());
                    Log.e("LOCATION", "MOCK_LOCATION" + location.getLongitude());

                    LogUtils.writeLogToSd("MOCK_LOCATION" + location.getLatitude());
                } catch (Exception e) {
                    LogUtils.writeLogToSd("run_is" + e.getMessage());
                    Log.e("LOCATION", "locationManager" + locationManager);
                    Log.e("LOCATION", "MOCK_LOCATION_ERROR");
                }
            }
        }
    }


    private void openWindow() {
        if (!VIEW_IS_SHOW) {
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 22);
        } else {
            Toast.makeText(this, "浮窗已开启", Toast.LENGTH_SHORT).show();
        }
    }

    WindowManager.LayoutParams layoutParams;
    WindowManager windowManager;
    View view;
    int x = 0;
    int y = 0;
    public static boolean VIEW_IS_SHOW = false;

    private void createWinView() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        view = LayoutInflater.from(this).inflate(R.layout.view_float_layout, null);

        mTvFloatUp = view.findViewById(R.id.img_float_up);
        mTvFloatLeft = view.findViewById(R.id.img_float_left);
        mTvFloatRight = view.findViewById(R.id.img_float_right);
        mTvFloatDown = view.findViewById(R.id.img_float_down);
        mTvFloatMove = view.findViewById(R.id.img_float_move);
        initFloatListener();
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        //设置图片格式，效果为背景透明
        layoutParams.format = PixelFormat.RGBA_8888;
        //设置悬浮窗口长宽数据
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.x = 200;
        layoutParams.y = 200;
        // 将悬浮窗控件添加到WindowManager
        windowManager.addView(view, layoutParams);
        VIEW_IS_SHOW = true;
    }

    private void closeWinView() {
        if (windowManager != null) {
            windowManager.removeView(view);
        }
        VIEW_IS_SHOW = false;
    }

    @SuppressLint("ClickableViewAccessibility")
    void initFloatListener() {
        mTvFloatUp.setOnClickListener(this);
        mTvFloatLeft.setOnClickListener(this);
        mTvFloatRight.setOnClickListener(this);
        mTvFloatDown.setOnClickListener(this);
        mTvFloatMove.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = (int) event.getRawX();
                        y = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int nowX = (int) event.getRawX();
                        int nowY = (int) event.getRawY();
                        int movedX = nowX - x;
                        int movedY = nowY - y;
                        x = nowX;
                        y = nowY;
                        layoutParams.x = layoutParams.x + movedX;
                        layoutParams.y = layoutParams.y + movedY;

                        // 更新悬浮窗控件布局(移动布局的灵魂API)
                        windowManager.updateViewLayout(view, layoutParams);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void removeNetLocation() {
        try {
            String providerStr = LocationManager.NETWORK_PROVIDER;
            if (locationManager.isProviderEnabled(providerStr)) {
                locationManager.removeTestProvider(providerStr);
            }
        } catch (Exception e) {
            Toast.makeText(MockLocationActivity.this, "移除网络定位失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void setNetLocation() {
        String providerStr = LocationManager.NETWORK_PROVIDER;
        try {
            locationManager.addTestProvider(providerStr, false, false, false, false,
                    false, false, false, 1, Criteria.ACCURACY_FINE);
        } catch (SecurityException e) {
            Toast.makeText(MockLocationActivity.this, "添加网络定位失败", Toast.LENGTH_SHORT).show();
        }
        if (!locationManager.isProviderEnabled(providerStr)) {
            locationManager.setTestProviderEnabled(providerStr, true);
        }
    }

    /**
     * 获取网络信息
     */
    @SuppressLint("MissingPermission")
    private void getNetInfo() {
        String MCC;//移动国家码（中国460）
        String MNC;//移动网络码（移动0；联通1；电信2）
        String LAC;//位置区域码
        String CID;//基站编号
        String BSSS;//基站信号强度
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        List<CellInfo> cellInfoList;
        CellInfo cellInfo;
        if (telephonyManager != null) {
            cellInfoList = telephonyManager.getAllCellInfo();
            String operator = telephonyManager.getNetworkOperator();
//            MCC = operator.substring(0,3);
//            MNC = operator.substring(3);

//            CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfoList.get(0);
//            cellInfoGsm.getCellIdentity().getLac();

//            CellInfoCdma cellInfoCdma= (CellInfoCdma) cellInfoList.get(0);
//            cellInfoCdma.getCellIdentity().getSystemId();

            telephonyManager.getCellLocation();
            telephonyManager.getNeighboringCellInfo();
            Log.e("test", cellInfoList.toString());
        }

    }
}
