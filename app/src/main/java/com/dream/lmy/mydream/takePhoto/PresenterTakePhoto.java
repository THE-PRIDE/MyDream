package com.dream.lmy.mydream.takePhoto;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

/***
 * 获取本地图片的Presenter类
 *
 * 负责业务逻辑实现
 * P和View相互持有
 *
 */
public class PresenterTakePhoto {

    private Context context;
    private Uri contentUri;

//    private ViewTakePhoto viewTakePhoto;

    PresenterTakePhoto(Context context,Uri contentUri){
        this.context = context;
        this.contentUri = contentUri;
    }

    public void handleBitmap(CallBackTakePhoto<Bitmap> callback) {

        ModelTakePhoto modelTakePhoto = new ModelTakePhoto(contentUri, context);
        Bitmap bitmap = modelTakePhoto.getBitmapFromPath(contentUri,context);
        callback.onCallBack(bitmap);
    }

}
