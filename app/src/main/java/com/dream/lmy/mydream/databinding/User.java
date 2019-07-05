package com.dream.lmy.mydream.databinding;

public class User {

    private String name;
    private int age;
    private String phone;
    private String imgUrl;

    public User(String name, int age, String phone, String imgUrl) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
