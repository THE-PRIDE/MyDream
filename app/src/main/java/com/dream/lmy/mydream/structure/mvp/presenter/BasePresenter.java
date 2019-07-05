package com.dream.lmy.mydream.structure.mvp.presenter;

/**
 * 类似于MVC中的BaseModel，主要负责业务逻辑的实现。
 * Google的MVP实现方案，是把业务逻辑放在presenter中，弱化model。
 */
public interface BasePresenter {
    void onDestroy();
}
