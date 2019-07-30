package com.dream.lmy.mydream;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dream.lmy.mydream.mockLocation.MockLocationActivity;
import com.dream.lmy.mydream.permissionUtils.PermissionRequester;
import com.dream.lmy.mydream.permissionUtils.PermissionResultListener;
import com.dream.lmy.mydream.permissionUtils.bean.Permission;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        initView();
        initData();
    }

    void initView() {
    }

    void initData() {
        PermissionRequester.getInstance().with(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE).request(new PermissionResultListener() {
            @Override
            public void onPermissionResult(Permission[] permissions) {
                if (permissions[0].isGranted() && permissions[1].isGranted()) {
                    Intent intent = new Intent(LaunchActivity.this, MockLocationActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LaunchActivity.this, "请于设置界面，添加相应权限", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
