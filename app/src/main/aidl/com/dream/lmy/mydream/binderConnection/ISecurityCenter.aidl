package com.dream.lmy.mydream.binderConnection;


interface ISecurityCenter {

    String encrypt(String content);
    String decrypt(String password);
}
