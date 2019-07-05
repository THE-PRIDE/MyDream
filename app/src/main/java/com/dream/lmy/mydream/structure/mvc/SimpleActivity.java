package com.dream.lmy.mydream.structure.mvc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dream.lmy.mydream.structure.UserInfo;
import com.dream.lmy.mydream.structure.mvc.callback.Callback1;
import com.dream.lmy.mydream.structure.mvc.model.SimpleModel;

/**
 * model层彻底解耦，C和V没有解耦
 * 层与层之间交互，通过回调或消息机制完成，避免直接持有
 * 业务逻辑放置在model层，可以更好的复用和修改增加业务
 */
public class SimpleActivity extends AppCompatActivity {


    private SimpleModel simpleModel;
    Button button;
    TextView age,name;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        simpleModel = new SimpleModel();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInfo("");//输入
            }
        });
    }

    private void getUserInfo(String uid){
        simpleModel.getUserInfo(uid, new Callback1<UserInfo>() {
            @Override
            public void onCallBack(UserInfo userInfo) {
                setDataToView(userInfo);
            }
        });
    }

    private void setDataToView(UserInfo userInfo){
        age.setText(userInfo.getAge());
        name.setText(userInfo.getName());
    }
}
