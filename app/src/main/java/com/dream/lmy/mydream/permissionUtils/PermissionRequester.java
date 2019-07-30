package com.dream.lmy.mydream.permissionUtils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;

import com.dream.lmy.mydream.common.Logger;
import com.dream.lmy.mydream.permissionUtils.bean.Permission;
import com.dream.lmy.mydream.permissionUtils.content.PermissionActivityLifecycle;
import com.dream.lmy.mydream.permissionUtils.fragment.FragmentProxy;
import com.dream.lmy.mydream.permissionUtils.fragment.PermissionFragmentFactory;

import static android.os.Build.VERSION_CODES.M;

/**
 * 运行时权限工具类
 */
public class PermissionRequester {

    private static final String TAG = PermissionRequester.class.getSimpleName();
    private static Object LOCKABLE = new Object();
    private static PermissionRequester instance;

    private FragmentProxy permissionFragment;
    private String[] permissions;
    private Activity activity;
    private static PermissionActivityLifecycle lifecycle;

    public static PermissionRequester getInstance() {
        synchronized (LOCKABLE) {
            if (null == instance) {
                instance = new PermissionRequester();
            }
            return instance;
        }
    }

    public PermissionRequester with(String... permissionNames){
        activity = getTopActivity();
        if (null != activity) {
            this.permissionFragment = new FragmentProxy(PermissionFragmentFactory.create(activity));
        }
        this.permissions = permissionNames;
        return this;
    }

    public PermissionRequester with(Permission... permissions){
        activity = getTopActivity();
        if (null != activity) {
            this.permissionFragment = new FragmentProxy(PermissionFragmentFactory.create(activity));
        }
        return this;
    }

    @TargetApi(M)
    public void request(PermissionResultListener listener) {
        if (permissionFragment == null || permissions == null) {
            throw new IllegalArgumentException("fragment or params permission is null");
        }
        permissionFragment.requestPermissions(permissions, listener);
    }

    /**
     * 获取栈顶Activity
     * @return 获取activity
     */
    public Activity getTopActivity() {
        if (null == lifecycle || null == lifecycle.topActWeakReference) {
            return null;
        }
        if (null == lifecycle.topActWeakReference.get() || lifecycle.topActWeakReference.get().isFinishing()) {
            return null;
        }
        Logger.error(TAG, lifecycle.topActWeakReference.get() + "");
        return lifecycle.topActWeakReference.get();
    }

    /**
     * 注册activityLifecycle
     * @param context 上下文
     */
    public static void registerApplication(Application context) {
        if (null != lifecycle) {
            context.unregisterActivityLifecycleCallbacks(lifecycle);
        }
        lifecycle = new PermissionActivityLifecycle();
        context.registerActivityLifecycleCallbacks(lifecycle);
    }
}
