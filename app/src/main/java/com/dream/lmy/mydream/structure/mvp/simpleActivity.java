package com.dream.lmy.mydream.structure.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dream.lmy.mydream.structure.UserInfo;
import com.dream.lmy.mydream.structure.mvp.callback.Callback1;
import com.dream.lmy.mydream.structure.mvp.contract.SampleContract;
import com.dream.lmy.mydream.structure.mvp.presenter.MyBasePresenter;


/**
 * activity实现了view接口，只作为view存在。
 * mPresenter为Model和View之间交互的桥梁。
 * Presenter和View相互持有。
 *
 *
 * 当业务逻辑增加，一个页面可能非常复杂，UI的改变非常多，导致View的接口会很庞大
 */
public class simpleActivity extends AppCompatActivity implements SampleContract.View{

    private SampleContract.Presenter mPresenter;
    Button button;
    TextView age, name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(mPresenter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getUserInfo("", new Callback1<UserInfo>() {
                    @Override
                    public void onCallback(UserInfo userInfo) {
                        setDataToView(userInfo);
                    }
                });
            }
        });

        MyBasePresenter basePresenter = new MyBasePresenter();
        getLifecycle().addObserver(basePresenter);
    }

    @Override
    public void setDataToView(UserInfo userInfo) {
        age.setText(userInfo.getAge());
        name.setText(userInfo.getName());
    }

    @Override
    public void setPresenter(SampleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
