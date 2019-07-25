package com.dream.lmy.mydream.netUtils;

import com.dream.lmy.mydream.netUtils.request.MyRequestBody;
import com.dream.lmy.mydream.netUtils.response.MyResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestManager {

    @POST("ServiceCenter/acct/CheckVerifyCode")
    Call<String> checkVerifyCode(@Body String requestBody);


    @POST("ServiceCenter/acct/getToke")
    Call<MyResponseBody> getToken(@Body MyRequestBody requestBody);

}
