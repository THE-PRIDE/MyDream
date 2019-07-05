package com.dream.lmy.mydream.ccc;

/**
 * JNI开发流程
 * <p>
 * 1.在Java中声明native方法
 * 2.编译Java源文件得到class文件，然后通过javah命令导出JNI的头文件
 * 3.实现JNI方法
 */
public class JniTest {


    static {
        System.loadLibrary("jni-test");
    }

    public static native String get();

    public native void set(String string);

}
