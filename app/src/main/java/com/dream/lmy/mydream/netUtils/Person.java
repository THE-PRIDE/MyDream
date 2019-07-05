package com.dream.lmy.mydream.netUtils;

import com.google.gson.annotations.SerializedName;

public class Person {

    @SerializedName(value = "name",alternate = {"NAME"})
    public String name;

    @SerializedName(value = "age",alternate = {"AGE","Age"})
    public int age;

    public double score;
}
