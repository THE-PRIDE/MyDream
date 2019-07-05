package com.dream.lmy.mydream.hook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookTestActivity implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        // 这里是一个hook应用的实例，主要目的是hook  com.example.crackme中的check函数，使其返回值为true
        if (!loadPackageParam.packageName.equals("com.example.crackme")){
            //loadPackageParam为载入的package的参数，其中packageName存了包名，如果包名不一致，不做处理
            return;
        }

        //使用XposedBridge的log输出载入的APP，在tag过滤器中添加Xposed即可读取log
        XposedBridge.log("Loaded app:" + loadPackageParam.packageName);
        /**
         * hook方法（函数）时，使用此方法
         *
         * @parame 要hook的类
         * @parame 获取classLoader
         * @parame 要hook的方法
         * @parame 第一个参数
         * @parame 第二个参数
         * @parame hook回调函数
         */
        XposedHelpers.findAndHookMethod("com.example.crackme.MainActivity", loadPackageParam.classLoader, "check", String.class, String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // afterHookedMethod，在被hook得到方法之后调用，param.args作为数组，保存了调用函数时的参数，通过getResult得到返回值
                XposedBridge.log("after hook:"+param.args[0]);
                XposedBridge.log("after hook2:"+param.args[1]);
                XposedBridge.log("result:"+param.getResult());
                param.setResult(true);
                XposedBridge.log("result(settled):"+param.getResult());
            }
        });
    }
}
