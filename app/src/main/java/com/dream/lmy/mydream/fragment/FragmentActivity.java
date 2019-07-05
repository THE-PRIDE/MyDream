package com.dream.lmy.mydream.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.dream.lmy.mydream.R;

public class FragmentActivity extends AppCompatActivity {

    private MyFragment myFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData(){

        myFragment = new MyFragment();


        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.id_container,myFragment,"");//id 容器；fragment；tag；
        int container = myFragment.getId();//fragment容器的ID
        transaction.addToBackStack(null);
        transaction.commitNow();
        transaction.commitNowAllowingStateLoss();



    }


}
