package com.dream.lmy.mydream.netUtils.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    /*
    处理请求头
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("Otype", "02")
                .header("signnm", "1277984e")
                .build();
        return chain.proceed(request);
    }
}
