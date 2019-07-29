package com.dream.lmy.mydream.permissionUtils.fragment;

import com.dream.lmy.mydream.permissionUtils.IPermissionActions;
import com.dream.lmy.mydream.permissionUtils.PermissionResultListener;

public class FragmentProxy implements IPermissionActions {

    private static final String TAG = FragmentProxy.class.getSimpleName();

    private IPermissionActions fragmentImp;

    public FragmentProxy(IPermissionActions fragmentImp) {
        this.fragmentImp = fragmentImp;
    }

//    @Override
//    public void requestPermissions(@NonNull String[] permissions, RequestPermissionListener listener) {
//        this.fragmentImp.requestPermissions(permissions, listener);
//        PermissionDebug.d(TAG, fragmentImp.getClass().getSimpleName() + " request:" + hashCode());
//    }
//
//    @Override
//    public void requestSpecialPermission(Special permission, SpecialPermissionListener listener) {
//        this.fragmentImp.requestSpecialPermission(permission, listener);
//        PermissionDebug.d(TAG, fragmentImp.getClass().getSimpleName() + " requestSpecial:" + hashCode());
//    }
//
//    @Override
//    public void goAppDetail(@Nullable GoAppDetailCallBack callBack) {
//        this.fragmentImp.goAppDetail(callBack);
//        PermissionDebug.d(TAG, fragmentImp.getClass().getSimpleName() + " goAppDetail:" + hashCode());
//    }

    @Override
    public void requestPermissions(String[] permissions, PermissionResultListener listener) {
        this.fragmentImp.requestPermissions(permissions,listener);
    }
}
