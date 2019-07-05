package com.dream.lmy.mydream.common;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;

import java.util.List;

public class ProcessUtils {

    /**
     * 获取当前进程名称
     * @param context 上下文
     * @param pid   当前进程PID （==Process.myPid()）
     * @return
     */
    public static String getProcessName(Context context, int pid) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
            if (pid == processInfo.pid){
                return processInfo.processName;
            }
        }
        return "";
    }
}
