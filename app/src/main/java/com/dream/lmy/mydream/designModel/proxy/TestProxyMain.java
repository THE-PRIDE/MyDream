package com.dream.lmy.mydream.designModel.proxy;

import java.lang.reflect.Proxy;

public class TestProxyMain {

    public static void main(String[] args) {
        // 静态代理
        ProxyObject proxyObject = new ProxyObject(new RealObject());
        proxyObject.operation();

        // 动态代理
        InterfaceObject mBePresent = new BeRepresent();
        ProxyD proxy = new ProxyD(mBePresent);
        InterfaceObject mInterface = (InterfaceObject) Proxy.newProxyInstance(mBePresent.getClass().getClassLoader(), mBePresent.getClass().getInterfaces(), proxy);

        //通过代理对象调用 目标对象相关接口 中实现的方法，这个时候会跳转到这个代理对象所关联的InvocationHandler的invoke方法中
        mInterface.shopping();
        //获得目标对象的代理对象所对应的class对象的名称，用字符串表示
        System.out.println(mInterface.getClass().getName());

        try {
            int a = circulate();
            System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * break throw return 可退出while true 循环
     * @return int
     * @throws Exception e
     */
    private static int circulate() throws Exception {
        int a = 0;
        while (true) {

            a++;
            System.out.println(a);

            if (a == 5) {
                System.out.println("continue_start");
                continue;
//                System.out.println("continue_end");
            }
            System.out.println(a+"**");

//            if (a == 7) {
//                System.out.println("throw_start");
//                throw new Exception();
////                System.out.println("throw_end");
//            }

//            if (a == 8){
//                System.out.println("break_start");
//                break;
////                System.out.println("break_end");
//            }

            if (a == 10) {
                System.out.println("return_start");
                return a;
//                System.out.println("return_end");
            }

        }
    }
}
