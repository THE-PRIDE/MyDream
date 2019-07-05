package com.dream.lmy.mydream.structure.mvc.model;

import com.dream.lmy.mydream.structure.UserInfo;
import com.dream.lmy.mydream.structure.mvc.callback.Callback1;

/**
 * 业务逻辑的具体实现
 */
public class SimpleModel implements BaseModel{


    public void getUserInfo(String uid, Callback1<UserInfo> callback1){

        UserInfo userInfo = new UserInfo();//通过网络请求获取数据
        callback1.onCallBack(userInfo);
    }




    @Override
    public void onDestroy() {

    }
}
