package com.dream.lmy.mydream.threadStudy;

import android.os.AsyncTask;

public class ThreadAsyncTask {


    public void startAsync(){


        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }

        };

        asyncTask.execute();
    }
}
