package com.dream.lmy.mydream.permissionUtils;

import android.annotation.TargetApi;

import static android.os.Build.VERSION_CODES.M;

public interface IPermissionActions {

    /**
     * 请求权限
     *
     * @param permissions 权限
     * @param listener    回调
     */
    @TargetApi(M)
    void requestPermissions(String[] permissions, PermissionResultListener listener);


//    /**
//     * 请求特殊权限
//     *
//     * @param permission 特殊权限
//     * @param listener   回调
//     */
//    void requestSpecialPermission(Special permission, SpecialPermissionListener listener);
//
//    /**
//     * 去应用详情页
//     *
//     * @param callBack 回调
//     */
//    void goAppDetail(@Nullable GoAppDetailCallBack callBack);

}