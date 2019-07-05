package com.dream.lmy.mydream.takePhoto;

import android.graphics.Bitmap;

public interface ViewTakePhoto {

    void setBitmapToView(Bitmap bitmap);

    void setPresenter(PresenterTakePhoto presenter);
}
