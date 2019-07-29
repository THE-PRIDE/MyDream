package com.dream.lmy.mydream;

import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dream.lmy.mydream.common.ProcessUtils;
import com.dream.lmy.mydream.aidl.UserManager;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.e("TEST", String.valueOf(UserManager.mUserID));

        String processName = ProcessUtils.getProcessName(getApplicationContext(), Process.myPid());
        Log.e("TEST", processName);

        getBookList();
    }

    private void getBookList(){

//        RequestHelper.getInstance().getList();
        RxJavaHelper.creatRxDemo();
    }
}
