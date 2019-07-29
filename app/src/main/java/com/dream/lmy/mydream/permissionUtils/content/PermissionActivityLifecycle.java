package com.dream.lmy.mydream.permissionUtils.content;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;

public class PermissionActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    public WeakReference<Activity> topActWeakReference;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        topActWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        topActWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
