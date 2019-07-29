package com.dream.lmy.mydream.permissionUtils.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.dream.lmy.mydream.permissionUtils.IPermissionActions;
import com.dream.lmy.mydream.permissionUtils.PermissionResultListener;
import com.dream.lmy.mydream.permissionUtils.bean.Permission;
import com.dream.lmy.mydream.permissionUtils.bean.PermissionConstants;
import com.dream.lmy.mydream.permissionUtils.tools.PermissionTools;

import static android.os.Build.VERSION_CODES.M;

public class PermissionSupportFragment extends Fragment implements IPermissionActions {

    private static final String TAG = PermissionSupportFragment.class.getSimpleName();

//    private RequestPermissionListener runtimeListener;
//
//    private SpecialPermissionListener specialListener;
//
//    private Special specialToRequest;
//
//    private GoAppDetailCallBack goAppDetailCallBack;

    private PermissionResultListener runtimeListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @TargetApi(M)
    @Override
    public void requestPermissions(String[] permissions, PermissionResultListener listener) {
        requestPermissions(permissions, PermissionConstants.REQUEST_CODE_PERMISSION);
        this.runtimeListener = listener;
    }

    @TargetApi(M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Permission[] permissionResults = new Permission[permissions.length];
        if (requestCode == PermissionConstants.REQUEST_CODE_PERMISSION) {
            for (int i = 0; i < permissions.length; ++i) {
                Permission permission = new Permission(permissions[i], grantResults[i], this.shouldShowRequestPermissionRationale(permissions[i]));
                permissionResults[i] = permission;
            }
        }
        if (null != runtimeListener && PermissionTools.isActivityAvailable(getActivity())) {
            runtimeListener.onPermissionResult(permissionResults);
        }
    }

//    @Override
//    public void requestSpecialPermission(Special permission, SpecialPermissionListener listener) {
//        this.specialListener = listener;
//        this.specialToRequest = permission;
//        Intent intent = PermissionTools.getSpecialPermissionIntent(getActivity(), permission);
//        if (null == intent) {
//            PermissionDebug.w(TAG, "create intent failed");
//            return;
//        }
//        try {
//            startActivityForResult(intent, SecurityConstants.REQUEST_CODE_PERMISSION_SPECIAL);
//        } catch (Exception e) {
//            e.printStackTrace();
//            PermissionDebug.e(TAG, e.toString());
//        }
//    }
//
//    @Override
//    public void goAppDetail(@Nullable GoAppDetailCallBack callBack) {
//        this.goAppDetailCallBack = callBack;
//        Intent intent = PermissionTools.getAppManageIntent(getActivity());
//        if (null == intent) {
//            PermissionDebug.w(TAG, "create intent failed");
//            return;
//        }
//        startActivityForResult(intent, SecurityConstants.REQUEST_CODE_APPLICATION_SETTINGS);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Activity activity = getActivity();
        if (!PermissionTools.isActivityAvailable(activity)) {
            return;
        }
//        if (requestCode == SecurityConstants.REQUEST_CODE_PERMISSION_SPECIAL && null != specialToRequest && null != specialListener) {
//            boolean result = new SpecialChecker(activity, specialToRequest).check();
//            if (result) {
//                specialListener.onGranted(specialToRequest);
//            } else {
//                specialListener.onDenied(specialToRequest);
//            }
//            return;
//        }
//        if (requestCode == SecurityConstants.REQUEST_CODE_APPLICATION_SETTINGS && null != goAppDetailCallBack) {
//            goAppDetailCallBack.onBackFromAppDetail(data);
//        }
    }
}