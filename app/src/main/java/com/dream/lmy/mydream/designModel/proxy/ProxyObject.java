package com.dream.lmy.mydream.designModel.proxy;

/**
 * 代理类
 */

public class ProxyObject extends AbstractObject{

    private RealObject mRealObject;

    public ProxyObject(RealObject realObject){
        this.mRealObject = realObject;
    }

    @Override
    protected void operation() {

        if (mRealObject == null){
            mRealObject = new RealObject();
        }
        mRealObject.operation();
    }
}
