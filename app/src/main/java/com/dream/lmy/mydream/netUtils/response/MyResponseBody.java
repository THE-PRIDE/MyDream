package com.dream.lmy.mydream.netUtils.response;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 响应报文
 */
public class MyResponseBody {

    @SerializedName("errorCode")
    @Expose
    private int errorCode;

    @SerializedName("errorMsg")
    @Expose
    private String errorMsg;

    @SerializedName("data")
    @Expose
    private List<JsonObject> data;

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public List<JsonObject> getData() {
        return data;
    }
}
