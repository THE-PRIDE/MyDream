package com.dream.lmy.mydream.permissionUtils.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.dream.lmy.mydream.permissionUtils.IPermissionActions;
import com.dream.lmy.mydream.permissionUtils.PermissionResultListener;
import com.dream.lmy.mydream.permissionUtils.bean.Permission;
import com.dream.lmy.mydream.permissionUtils.bean.PermissionConstants;
import com.dream.lmy.mydream.permissionUtils.tools.PermissionTools;

import static android.os.Build.VERSION_CODES.M;

public class PermissionFragment extends Fragment implements IPermissionActions {

    private static final String TAG = PermissionFragment.class.getSimpleName();

//    private Special specialToRequest;

//    private RequestPermissionListener runtimeListener;

//    private SpecialPermissionListener specialListener;

//    private GoAppDetailCallBack goAppDetailCallBack;

    private PermissionResultListener runtimeListener;

    @TargetApi(M)
    @Override
    public void requestPermissions(String[] permissions, PermissionResultListener listener) {
        this.runtimeListener = listener;
        requestPermissions(permissions, PermissionConstants.REQUEST_CODE_PERMISSION);
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
//        Intent intent = PermissionTools.getSpecialPermissionIntent(getActivity(), specialToRequest);
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
//
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
