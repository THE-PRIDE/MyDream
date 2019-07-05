package com.dream.lmy.mydream;

import android.app.Application;
import android.util.Log;

//import com.csii.bbg.common.WeexInit;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("TEST","ONCREATE");
//        WeexInit.init(this);
    }

}
