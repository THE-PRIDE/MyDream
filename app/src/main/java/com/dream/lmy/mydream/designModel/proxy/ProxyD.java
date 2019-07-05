package com.dream.lmy.mydream.designModel.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理类
 */
public class ProxyD implements InvocationHandler {

    private Object target;//要代理的目标对象

    ProxyD(Object target){
        this.target = target;
    }

    /**
     * 此方法相当一个拦截方法
     * @param proxy 代理对象
     * @param method 代理方法
     * @param args 代理方法中的参数
     * @return 返回值会返回给使用者
     * @throws Throwable 异常
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        method.invoke(target,args);
        return null;
    }
}
