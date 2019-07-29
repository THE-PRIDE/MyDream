package com.dream.lmy.mydream.common;

import android.util.Log;

public class Logger {

    public static boolean isDebug = true;

    public static void error(String tag, String message) {
        if (isDebug) {
            Log.e(tag, message);
        }
    }
}
