package com.dream.lmy.mydream.netUtils;

import com.dream.lmy.mydream.databinding.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ParseJsonUtils {
    public void parseJson(){

        Gson gson = new Gson();
        gson.fromJson("alice",String.class);
        gson.fromJson("18",Integer.class);

    }

    public static void createJson(){
        Person person = new Person();
        person.age = 18;
        person.name = "alice";

        Person2 person2 = new Person2();
        person2.name = "Jack";
        person2.sex = "man";

        Gson gson = new Gson();
        String data = gson.toJson(person);
        System.out.println(data);

        String data2 = gson.toJson(person2);
        System.out.println(data2);

        Person person1 = gson.fromJson(data , Person.class);
        Person person3 = gson.fromJson(data2, Person.class);

        System.out.println(person1.name);
        System.out.println(person3.name);
    }

    public static void main(String args[]){
        createJson();

        List list = new ArrayList();
        list.iterator();
    }
}
