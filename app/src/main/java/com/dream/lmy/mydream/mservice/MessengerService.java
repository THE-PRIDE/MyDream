package com.dream.lmy.mydream.mservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import static com.dream.lmy.mydream.mservice.IpcConstant.BINDER_CLIENT;
import static com.dream.lmy.mydream.mservice.IpcConstant.BINDER_SERVICE;

/**
 * 使用Messenger 实现IPC
 * 服务端
 */
public class MessengerService extends Service {

    private static final String TAG = "MessengerService";
    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case BINDER_CLIENT:
                    Log.e(TAG,msg.getData().get("msg").toString());

                    Messenger client = msg.replyTo;
                    Message replyMessage = Message.obtain(null,BINDER_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","已收到");
                    replyMessage.setData(bundle);
                    try {
                        client.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
