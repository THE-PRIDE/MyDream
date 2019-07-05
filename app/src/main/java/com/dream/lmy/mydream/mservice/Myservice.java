package com.dream.lmy.mydream.mservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

public class Myservice extends Service{


    private MyBinder myBinder = new MyBinder();
    @Override
    public void onCreate() {
        super.onCreate();

    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


    class MyBinder extends Binder{

        public int getRandomNum(){
            Random random = new Random();
            return random.nextInt();
        }
    }
}
