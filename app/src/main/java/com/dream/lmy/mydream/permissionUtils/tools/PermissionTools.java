package com.dream.lmy.mydream.permissionUtils.tools;

import android.app.Activity;
import android.os.Build;

import com.module.csii.common.Logger;

public class PermissionTools {

    private static final String TAG = PermissionTools.class.getSimpleName();
    /**
     * 判断Activity 是否可用
     *
     * @param activity 目标Activity
     * @return true of false
     */
    public static boolean isActivityAvailable(Activity activity) {
        if (null == activity) {
            return false;
        }
        if (activity.isFinishing()) {
            Logger.error(TAG, " activity is finishing :" + activity.getClass().getSimpleName());
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            Logger.error(TAG, " activity is destroyed :" + activity.getClass().getSimpleName());
            return false;
        }
        return true;
    }
}
