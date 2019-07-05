package com.dream.lmy.mydream.structure.mvc.callback;

/**
 * callback根据view或Controller调用的Model时，回调的参数个数选择使用。
 * @param <T>
 */
public interface Callback1<T> {
    void onCallBack(T t);
}
