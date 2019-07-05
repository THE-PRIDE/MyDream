package com.dream.lmy.mydream.structure.mvc.model;

/**
 * 所有业务逻辑model的父类，这里的onDestroy方法和activity或fragment生命周期同步，做一些销毁操作
 */
public interface BaseModel {

    void onDestroy();
}
