package com.dream.lmy.mydream.okhttp;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {

    /**
     * okhttp基本用法
     * 构建OkHttpClient对象
     * 构建Request请求对象
     * 构建Call对象
     * 发起请求
     */
    public static void creatOkhttp(){
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody body = new FormBody.Builder()
                .add("phone","13412341234")
                .add("pass","12345")
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url("")
                .build();

        Call call = client.newCall(request);
        Response execute;

        try {
            // 同步请求
            execute = call.execute();

            // 异步请求
            call.equals(new Callback(){

                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });

            Log.e("TEST",execute.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
