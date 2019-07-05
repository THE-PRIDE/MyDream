package com.dream.lmy.mydream.structure.mvp.model;

import com.dream.lmy.mydream.structure.UserInfo;
import com.dream.lmy.mydream.structure.mvp.callback.Callback1;
import com.dream.lmy.mydream.structure.mvp.presenter.BasePresenter;

public class SampleModel implements BasePresenter {

    public void getUserInfo(String uid, Callback1<UserInfo> callback1){

        UserInfo userInfo = new UserInfo();//通过网络请求获取数据
        callback1.onCallback(userInfo);
    }

    @Override
    public void onDestroy() {

    }
}
