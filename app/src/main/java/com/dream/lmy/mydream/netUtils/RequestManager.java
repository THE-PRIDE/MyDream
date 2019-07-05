package com.dream.lmy.mydream.netUtils;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestManager {


    @POST("document/test/test.json")
    Call<ResponseBody> getResponse();

    @POST("document/test/test.json")
    Call<ResponseBody> addBook(@Path("name") String name);

    @GET("wxarticle/chapters/json")
    Call<ResponseBody> getList();
}
