package com.dream.lmy.mydream.structure.mvp.view;

import com.dream.lmy.mydream.structure.mvp.presenter.BasePresenter;

/**
 * BaseView是所有View的父类，将Android中view抽象出来，
 * 只有跟view相关的操作都有baseview的实现类完成。
 */
public interface BaseView<P extends BasePresenter> {
    void setPresenter(P presenter);
}
