package com.dream.lmy.mydream.mediaStore;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.dream.lmy.mydream.R;

import java.io.File;
import java.util.Arrays;

public class MediaStoreActivity extends AppCompatActivity {

    private TextView mTvMediaInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mediastore);
        initView();
        initData();
        initListener();
    }

    void initView() {
        mTvMediaInfo = findViewById(R.id.tv_show_media_info);
    }

    void initData() {

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = new String[]{MediaStore.MediaColumns._ID, MediaStore.MediaColumns.DISPLAY_NAME};
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, null, null, null, null);
        if (cursor != null) {
            mTvMediaInfo.setText("");
            cursor.close();
        }

        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.e("TEST",file.getAbsolutePath());

    }

    void initListener() {
        testAPI();
        testAPI2();
    }

    /**
     * 获取所有列的名称
     *
     * @return StringBuilder
     */
    private StringBuilder getAllColumnName(Cursor cursor) {

        StringBuilder stringBuilder = new StringBuilder();
        for (String columnName : cursor.getColumnNames()) {
            stringBuilder.append(columnName).append("\n");
        }
        return stringBuilder;
    }


    @TargetApi(28)
    private void testAPI(){
        Log.e("test","28");
    }

    @TargetApi(26)
    private void testAPI2(){
        Log.e("test","26");
    }
}
