package com.dream.lmy.mydream.mediaStore;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

public class MediaStoreUtils {

    public static void testMeidaStore(AppCompatActivity appCompatActivity){

        String id = MediaStore.MediaColumns._ID;
        String data = MediaStore.MediaColumns.DATA;

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        ContentResolver resolver = appCompatActivity.getContentResolver();
        //
        Cursor cursor = resolver.query(uri,new String[]{MediaStore.Images.Media._ID},null,null,null);


        if (cursor != null) {
            cursor.close();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("test","test");

        FileProvider fileProvider = new FileProvider();


    }

}
