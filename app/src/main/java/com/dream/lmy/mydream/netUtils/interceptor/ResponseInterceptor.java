package com.dream.lmy.mydream.netUtils.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 响应报文拦截处理
 */
public class ResponseInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());
        String content = "";
        MediaType mediaType = null;
        try {
            mediaType = response.body() != null ? response.body().contentType() : null;
//            content = EncryptUtils.decryptString(response.body() != null ? response.body().string() : "");//如果获取到的是密文，需在此解密
            content = response.body().string();//.string()方法只能调用一次
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response newResponse = response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
        return newResponse;
    }
}
