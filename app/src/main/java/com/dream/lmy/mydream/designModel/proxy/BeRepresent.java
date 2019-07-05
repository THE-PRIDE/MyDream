package com.dream.lmy.mydream.designModel.proxy;

/**
 * 动态代理
 * 被代理类（目标类）
 */
public class BeRepresent implements InterfaceObject {
    @Override
    public void shopping() {

        System.out.println("动态代理：具体实现方法");
    }
}
