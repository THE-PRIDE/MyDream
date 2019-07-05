package com.dream.lmy.mydream.netUtils;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBody {

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

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setData(List<JsonObject> data) {
        this.data = data;
    }
}
