package com.dream.lmy.mydream.common.guideview.position;

import android.graphics.RectF;

import com.dream.lmy.mydream.common.guideview.HighLight;


/**
  */
public class OnLeftPosCallback extends OnBaseCallback {
    public OnLeftPosCallback() {
    }

    public OnLeftPosCallback(float offset) {
        super(offset);
    }

    @Override
    public void getPosition(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
        marginInfo.rightMargin = rightMargin+rectF.width()+offset;
        marginInfo.topMargin = rectF.top;
    }
}
