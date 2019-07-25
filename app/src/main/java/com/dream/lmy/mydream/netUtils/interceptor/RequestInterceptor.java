package com.dream.lmy.mydream.netUtils.interceptor;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 请求报文拦截处理
 */
public class RequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Charset UTF8 = Charset.forName("UTF-8");
        Request request = chain.request();
        RequestBody requestBody = null;
        if (request != null) {
            requestBody = request.body();
        }
        Request newRequest = null;
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            String requestParam = buffer.readString(charset);
//            Logger.error("requestBody", requestParam.replace("\\",""));
//            requestParam = requestParam.replace("\\","");
//            String newRequestParam = requestParam;
//            newRequestParam = EncryptUtils.encryptString(newRequestParam);//加密字符串
            RequestBody newBody = RequestBody.create(MediaType.parse("text/plain"), requestParam);
            newRequest = request.newBuilder().post(newBody).build();
        }
        return chain.proceed(newRequest);
    }
}
