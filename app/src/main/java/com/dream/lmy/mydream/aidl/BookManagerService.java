package com.dream.lmy.mydream.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {
    private static final String TAG = "BMS";

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
//    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList2 = new RemoteCallbackList<>();


    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList2.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList2.unregister(listener);
        }

    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"Android"));
        mBookList.add(new Book(2,"IOS"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=5;i<=100;i++){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    onNewBookArrived(new Book(i,"book"+i));
                    Log.e("TEST","ONnewBookArrived");

//                    mBookList.add(new Book(i,"book"+i));
                }
            }
        }).start();
    }

    private void onNewBookArrived(Book book){
        mBookList.add(book);
        final int N = mListenerList2.beginBroadcast();
        for (int i = 0;i<N;i++){
            IOnNewBookArrivedListener l = mListenerList2.getBroadcastItem(i);
            if (l != null){
                try {
                    l.onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mListenerList2.finishBroadcast();

//        for (int i = 0;i<=mListenerList.size();i++){
//        if (mListenerList == null)return;
//            IOnNewBookArrivedListener listener = mListenerList.get(0);
//            try {
//                Log.e("TEST","ONnewBookArrived"+book.bookName);
//                listener.onNewBookArrived(book);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
