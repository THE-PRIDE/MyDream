package com.dream.lmy.mydream.bitmapUtils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * 此类用于处理各种bitmap
 */
public class BitmapUtils {

    /**
     * 获取图片旋转角度
     *
     * @param path
     * @return
     */
    private static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle  需旋转的角度
     * @param bitmap 需旋转的图片
     * @return
     */
    private static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        Bitmap angleBitmap = null;

        //根据旋转角度，生产旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        try {
            angleBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError error) {

        }
        if (angleBitmap == null) {
            return bitmap;
        }
        if (bitmap != angleBitmap) {
            bitmap.recycle();
        }
        return angleBitmap;
    }

    /**
     * 图片裁剪
     */
    private static void photoZoom(AppCompatActivity appCompatActivity, File srcFile, File output, int request) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(appCompatActivity, "", srcFile);
            intent.setDataAndType(uri, "image/*");
        }
        intent.setDataAndType(Uri.fromFile(srcFile), "image/*");
        intent.putExtra("crop", "true");// crop为true,是设置在开启的intent中设置显示的view可以裁剪

        intent.putExtra("aspectX", 1);//设置宽高比例
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", "800");//裁剪图片的宽高
        intent.putExtra("outputY", "480");

        intent.putExtra("return-data", false);//true 不返回URI,false 返回
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        appCompatActivity.startActivityForResult(intent, request);


    }


    public static Uri getImageContentUri(Context context, File imageFile) {

        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=?", new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }

        }

    }

}
