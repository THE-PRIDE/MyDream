package com.dream.lmy.mydream.permissionUtils.bean;

import android.content.pm.PackageManager;

public class Permission {

    public String permissionName;
    public int grantResult;
    public boolean shouldRationals;//提示

    public Permission(){

    }

    public Permission(String permission, int grantResult, boolean shouldShowRequestPermissionRationale) {
        this.permissionName = permission;
        this.grantResult = grantResult;
        this.shouldRationals = shouldShowRequestPermissionRationale;
    }

    public boolean isGranted() {
        return grantResult == PackageManager.PERMISSION_GRANTED;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setGrantResult(int grantResult) {
        this.grantResult = grantResult;
    }
}
