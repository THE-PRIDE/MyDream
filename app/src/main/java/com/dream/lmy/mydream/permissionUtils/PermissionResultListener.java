package com.dream.lmy.mydream.permissionUtils;

import com.dream.lmy.mydream.permissionUtils.bean.Permission;

public interface PermissionResultListener {

//    void onPermissionOk();
//
//    void onPermissionDenied();

    void onPermissionResult(Permission[] permissions);
}
