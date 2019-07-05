package com.dream.lmy.mydream.designModel.proxy;

import android.util.Log;

/**
 * 目标对象(被代理对象)
 */
public class RealObject extends AbstractObject {

    @Override
    protected void operation() {

        System.out.println("静态代理：实际实现功能");

    }
}
