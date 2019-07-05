package com.dream.lmy.mydream.aidl;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dream.lmy.mydream.R;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity implements ServiceConnection,IBinder.DeathRecipient{

    private static final String TAG = "BookManagerActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;

    private IBookManager mRemoteBookManager;

    //TODO 如何像serviceConnection一样优雅的实现 handler？
    @SuppressLint("HandlerLeak")
    private Handler mHandler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Book book = (Book)msg.obj;
                    Log.e(TAG,"new book array:"+book.bookName);
                    break;
            }
        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED,newBook).sendToTarget();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = new Intent(BookManagerActivity.this,BookManagerService.class);
        bindService(intent,this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        Log.e("TEST","onDestroy");
        super.onDestroy();
        if (mRemoteBookManager != null){
            try {
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.e("TEST","onServiceConnected");
        IBookManager bookManager = IBookManager.Stub.asInterface(service);
        try {
            service.linkToDeath(this,0);
            mRemoteBookManager = bookManager;
            List<Book> list = bookManager.getBookList();
            Log.e("TEST",list.get(1).bookName);
            Book book = new Book(3,"艺术探索");
            bookManager.addBook(book);
            List<Book> newlist = bookManager.getBookList();
            Log.e("TEST",newlist.get(2).bookName);
            mRemoteBookManager.registerListener(mOnNewBookArrivedListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.e("TEST","onServiceDisconnected");
        mRemoteBookManager = null;
    }

    @Override
    public void binderDied() {
        if (mRemoteBookManager == null) return;
        mRemoteBookManager.asBinder().unlinkToDeath(this,0);
        mRemoteBookManager = null;
        //TODO bindService
    }
}
