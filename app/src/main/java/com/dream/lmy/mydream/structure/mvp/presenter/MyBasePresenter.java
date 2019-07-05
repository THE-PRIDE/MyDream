package com.dream.lmy.mydream.structure.mvp.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;

public class MyBasePresenter implements IPresenter {

    private static final String TAG = Class.class.getSimpleName();

    @Override
    public void onCreate(LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(LifecycleOwner owner) {

    }

    @Override
    public void onLifecycleChanged(LifecycleOwner owner, Lifecycle.Event event) {

    }


}
