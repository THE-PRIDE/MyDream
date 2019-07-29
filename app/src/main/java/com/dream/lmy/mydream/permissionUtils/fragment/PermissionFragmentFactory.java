package com.dream.lmy.mydream.permissionUtils.fragment;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.dream.lmy.mydream.common.Logger;
import com.dream.lmy.mydream.permissionUtils.IPermissionActions;

import java.lang.reflect.Field;
import java.util.List;

public class PermissionFragmentFactory {


    private static final String TAG = PermissionFragmentFactory.class.getSimpleName();

    private static final String FRAGMENT_TAG = "permission_fragment_tag";

    public static IPermissionActions create(Activity activity) {
        IPermissionActions action;
        if (activity instanceof FragmentActivity) {
            FragmentManager supportFragmentManager = getSupportFragmentManager((FragmentActivity) activity);
            PermissionSupportFragment permissionSupportFragment = (PermissionSupportFragment) supportFragmentManager.findFragmentByTag(FRAGMENT_TAG);
            if (null == permissionSupportFragment) {
                permissionSupportFragment = new PermissionSupportFragment();
                supportFragmentManager.beginTransaction()
                        .add(permissionSupportFragment, FRAGMENT_TAG)
                        .commitNowAllowingStateLoss();
            }
            action = permissionSupportFragment;
        } else {
            android.app.FragmentManager fragmentManager = getFragmentManager(activity);
            PermissionFragment permissionFragment = (PermissionFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
            if (null == permissionFragment) {
                permissionFragment = new PermissionFragment();
                fragmentManager.beginTransaction()
                        .add(permissionFragment, FRAGMENT_TAG)
                        .commitAllowingStateLoss();
                //make it commit like commitNow
                fragmentManager.executePendingTransactions();
            }
            action = permissionFragment;
        }
        return action;
    }


    private static FragmentManager getSupportFragmentManager(FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        if (fragmentManager.getFragments().size() > 0
                && null != fragmentManager.getFragments().get(0)) {
            return fragmentManager.getFragments().get(0).getChildFragmentManager();
        }
        return fragmentManager;
    }

    private static android.app.FragmentManager getFragmentManager(Activity activity) {
        android.app.FragmentManager fragmentManager = activity.getFragmentManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (fragmentManager.getFragments().size() > 0
                    && null != fragmentManager.getFragments().get(0)) {
                return fragmentManager.getFragments().get(0).getChildFragmentManager();
            }
        } else {
            try {
                Field fragmentsField = Class.forName("android.app.FragmentManagerImpl").getDeclaredField("mAdded");
                fragmentsField.setAccessible(true);
                List<android.app.Fragment> fragmentList = (List<android.app.Fragment>) fragmentsField.get(fragmentManager);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
                        && fragmentList.size() > 0
                        && null != fragmentList.get(0)) {
                    Logger.error(TAG, "reflect get child fragmentManager success");
                    return fragmentList.get(0).getChildFragmentManager();
                }
            } catch (Exception e) {
                Logger.error(TAG, "try to get childFragmentManager failed " + e.toString());
                e.printStackTrace();
            }
        }
        return fragmentManager;
    }

}
