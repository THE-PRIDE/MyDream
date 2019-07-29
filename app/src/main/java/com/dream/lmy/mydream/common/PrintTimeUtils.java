package com.dream.lmy.mydream.common;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class PrintTimeUtils {
    private static String TAG = TimeUnit.class.getSimpleName();

    public static void getCurrentTime() {
        long currentTime = System.currentTimeMillis();
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(currentTime);
        int H = mCalendar.get(Calendar.HOUR);
        int M = mCalendar.get(Calendar.MINUTE);
        int S = mCalendar.get(Calendar.SECOND);
        int ms = mCalendar.get(Calendar.MILLISECOND);
        Logger.error(TAG, M + "分" + S + "秒" + ms);
    }
}
