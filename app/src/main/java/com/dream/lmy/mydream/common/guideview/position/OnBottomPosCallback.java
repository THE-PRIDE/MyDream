package com.dream.lmy.mydream.common.guideview.position;

import android.graphics.RectF;

import com.dream.lmy.mydream.common.guideview.HighLight;


/**
  */
public  class OnBottomPosCallback extends OnBaseCallback{
    public OnBottomPosCallback() {
    }

    public OnBottomPosCallback(float offset) {
        super(offset);
    }

    @Override
    public void getPosition(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
        marginInfo.rightMargin = rightMargin;
        marginInfo.topMargin = rectF.top + rectF.height()+offset;
    }

}
