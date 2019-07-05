package com.dream.lmy.mydream.structure.mvp.contract;

import com.dream.lmy.mydream.structure.UserInfo;
import com.dream.lmy.mydream.structure.mvp.callback.Callback1;
import com.dream.lmy.mydream.structure.mvp.presenter.BasePresenter;
import com.dream.lmy.mydream.structure.mvp.view.BaseView;

/**
 * Contract契约类，用于定义同一个界面的view的接口和presenter的具体实现。
 * 通过规范的方法命名和注释，可以清晰的看到整个页面的逻辑。
 */
public class SampleContract {
    public static class Presenter implements BasePresenter{

        public void getUserInfo(String uid, Callback1<UserInfo> callback1){
            UserInfo userInfo = new UserInfo();//从网络获取数据
            callback1.onCallback(userInfo);
        }

        @Override
        public void onDestroy() {

        }
    }

    public interface View extends BaseView<Presenter>{
        void setDataToView(UserInfo userInfo);
    }
}
