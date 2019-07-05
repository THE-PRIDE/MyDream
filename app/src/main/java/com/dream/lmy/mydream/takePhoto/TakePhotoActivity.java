package com.dream.lmy.mydream.takePhoto;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dream.lmy.mydream.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TakePhotoActivity extends AppCompatActivity implements View.OnClickListener, ViewTakePhoto {


    private Button btn_take_camera;
    private Button btn_take_album;
    private ImageView img_take_photo;

    private Uri photoUri;

    private PresenterTakePhoto presenterTakePhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo_layout);
        initView();
        initData();
        initListener();
    }


    void initView() {
        btn_take_camera = findViewById(R.id.btn_take_camera);
        btn_take_album = findViewById(R.id.btn_take_album);
        img_take_photo = findViewById(R.id.img_take_photo);
    }

    void initData() {
        testBitmap();
    }

    void initListener() {

        btn_take_camera.setOnClickListener(this);
        btn_take_album.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_take_camera:
//                startActivityFile();
                takePhotoFromCamera();
                break;
            case R.id.btn_take_album:
                takePhotoFromAlbum();
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.e("TEST", permissions.toString());
        Log.e("TEST", grantResults.toString());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        if (0 == requestCode) {
            Uri uri = null;
            if (data != null && data.getData() != null) {
                uri = data.getData();
            }
            if (uri == null && photoUri != null) {
                uri = photoUri;
            }
            setPresenter(new PresenterTakePhoto(TakePhotoActivity.this, uri));
            presenterTakePhoto.handleBitmap(new CallBackTakePhoto<Bitmap>() {
                @Override
                public void onCallBack(Bitmap bitmap) {
                    setBitmapToView(bitmap);
                }
            });

//            getFileFromContentUri(uri, getApplicationContext());
//            File file = new File(getFileFromContentUri(uri, getApplicationContext()));
//            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
//            img_take_photo.setImageBitmap(rotaingImageView(readPictureDegree(getFileFromContentUri(uri, getApplicationContext())),bitmap));
        } else if (1 == requestCode) {

            if (data == null) {
                return;
            } else {
                Uri uri = data.getData();
                String path = getPathFromContentUri(uri, this);

                Bitmap bitmap1 = BitmapFactory.decodeFile(path);
                Log.e("TEST", bitmap1.getByteCount() + "");

                Bitmap bitmap = compressBitmapByScale(path, 200, 200);
                Log.e("TEST", bitmap.getByteCount() + "");

                img_take_photo.setImageBitmap(bitmap);

//                byte[] imageBytes = getByteFromURI(uri);
//                if (null != imageBytes) {
//                    String pribase64 = Base64.encodeToString(imageBytes, Base64.DEFAULT).replaceAll("\n", "");
//
//                } else {
//                }
            }

//            Uri uri = data.getData();
//            getPathByUri(uri);
//            uri.getQueryParameterNames();
//
////            content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F2084/ORIGINAL/NONE/924558351
//            uri.getScheme();
//            uri.getSchemeSpecificPart();
//            uri.getEncodedPath();
//            uri.getFragment();
//
//            uri.getAuthority();
//            uri.getPath();
//            uri.getQuery();
//            uri.getHost();
//            uri.getPort();
//
//            uri.getPathSegments();

        }
    }

    /**
     * 相机获取图片
     */
    private void takePhotoFromCamera() {
        String state = Environment.getExternalStorageState();
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + Environment.DIRECTORY_DCIM + File.separator + "";
        if (state.equals(Environment.MEDIA_MOUNTED)) {//SD卡可用状态
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            String fileName = getPhotoFileName() + ".jpg";

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//Android7.0之后，系统对URI的访问有限制
            photoUri = FileProvider.getUriForFile(TakePhotoActivity.this, "com.dream.lmy.mydream.fileprovider", new File(path + fileName));
//            } else {
//                photoUri = Uri.fromFile(new File(path + fileName));
//            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, 0);
        }
    }

    private void startActivityFile() {

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, 0);

        ContentResolver resolver = this.getContentResolver();
        resolver.insert(uri, values);

        String[] thumColumns = {MediaStore.Audio.AlbumColumns.ALBUM};

        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, thumColumns, MediaStore.Audio.Albums._ID, null, null);

        String[] name = cursor.getColumnNames();
        Log.e("TEST", name + "");
        cursor.close();


        String string = UUID.randomUUID().toString();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivity(intent);
    }

    /**
     * 从相册获取图片
     */
    private void takePhotoFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 1);
    }

    @SuppressLint("SimpleDateFormat")
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + dateFormat.format(date);
    }

    private void getPathByUri(Uri uri) {

//        String imageUri = "content://media/external/images/media/**";
//        Uri uri = Uri.parse(imageUri);

        String[] proj = {MediaStore.Images.Media.DATA};

//        Cursor cursor = this.managedQuery();

        Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
        cursor.getColumnNames();
        int column_indext = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        String imagePath = cursor.getString(column_indext);

        File file = new File(imagePath);
        Uri fileUri = Uri.fromFile(file);
        cursor.close();
    }

    private void getUriByPath(String path) {
        Uri mUri = Uri.parse("content://media/external/images/media");
        Uri mImageUri = null;
        Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            if (path.equals(data)) {
                int ringtoneID = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                mImageUri = Uri.withAppendedPath(mUri, "" + ringtoneID);
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();
    }


    public String getPathFromContentUri(Uri contentUri, Context context) {
        if (contentUri == null) {
            return null;
        }

        String scheme = contentUri.getScheme();
        if (scheme == null || ContentResolver.SCHEME_FILE.equals(scheme)) {
            return contentUri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            String filePath = "";
            String fileName;
            String[] filePathColumn = {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME};
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(contentUri, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                fileName = cursor.getString(cursor.getColumnIndex(filePathColumn[1]));
                cursor.close();
                filePath = getPathFromInputStreamUri(context, contentUri, fileName);
            }
            return filePath;
        }
        return "";
    }

    private String getPathFromInputStreamUri(Context context, Uri uri, String fileName) {
        InputStream inputStream = null;
        String filePath = null;
        if (uri.getAuthority() != null) {
            try {
                inputStream = context.getContentResolver().openInputStream(uri);
                File file = createTemporalFileFrom(context, inputStream, fileName);
                filePath = file.getPath();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return filePath;
    }

    /**
     * 创建临时文件
     *
     * @param context
     * @param inputStream
     * @param fileName
     * @return
     * @throws IOException
     */
    private File createTemporalFileFrom(Context context, InputStream inputStream, String fileName) throws IOException {
        File targetFile = null;
        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];
            targetFile = new File(context.getCacheDir(), fileName);
            if (targetFile.exists()) {
                targetFile.delete();
            }
            OutputStream outputStream = new FileOutputStream(targetFile);
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return targetFile;
    }

    public Bitmap compressBitmapByScale(String bitmapPath, int compWidth, int compHeight) {
        if (TextUtils.isEmpty(bitmapPath)) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// 仅加载载图片的参数等信息，不加载图片到内存
        BitmapFactory.decodeFile(bitmapPath, options);
        int width = options.outWidth;
        int height = options.outHeight;
        options.inScaled = true;

//        options.inSampleSize = getCompressScale(width, height, compWidth, compHeight);// 设置缩放比例
        options.inSampleSize = 7;// 设置缩放比例
        options.inJustDecodeBounds = false;
//        return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(bitmapPath,options),200,200,true);
        return BitmapFactory.decodeFile(bitmapPath, options);
    }


    public void testBitmap() {
        String path = Environment.getExternalStorageDirectory() + "/test_img.jpg";

        File file = new File(path);
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] data = new byte[1024];
            int len;
            while ((len = fileInputStream.read(data)) != -1) {
                arrayOutputStream.write(data,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] test = arrayOutputStream.toByteArray();

        Log.e("TEST","size:"+arrayOutputStream.size()+"");
        Log.e("TEST","length:"+test.length+"");

    }


//    /**
//     * 从URI中获取图片路径
//     *
//     * @param contentUri
//     * @param context
//     * @return
//     */
//    private static String getFileFromContentUri(Uri contentUri, Context context) {
//        if (contentUri == null) {
//            return null;
//        }
//        File file = null;
//        String filePath = "";
//        String fileName;
//        String[] filePathColumn = {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME};
//        ContentResolver contentResolver = context.getContentResolver();
//        Cursor cursor = contentResolver.query(contentUri, filePathColumn, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
////            filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
//            fileName = cursor.getString(cursor.getColumnIndex(filePathColumn[1]));
//            cursor.close();
////            if (!TextUtils.isEmpty(filePath)){
////                file = new File(filePath);
////            }
////
////            if (!file.exists() || file.length() <=0 || TextUtils.isEmpty(filePath)){
//////                filePath =
////            }
////            if (!TextUtils.isEmpty(filePath)){
////                file = new File(filePath);
////            }
//            filePath = getPathFromInputStreamUri(context, contentUri, fileName);
//        }
//        return filePath;
//    }

    /**
     * 从URI中读取文件流
     *
     * @param context
     * @param uri
     * @param fileName
     * @return
     */
//    private static String getPathFromInputStreamUri(Context context, Uri uri, String fileName) {
//        InputStream inputStream = null;
//        String filePath = null;
//        if (uri.getAuthority() != null) {
//            try {
//                inputStream = context.getContentResolver().openInputStream(uri);
//                File file = createTemporalFileFrom(context, inputStream, fileName);
//                filePath = file.getPath();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (inputStream != null) {
//                    try {
//                        inputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        return filePath;
//    }
//
//    /**
//     * 创建临时文件
//     *
//     * @param context
//     * @param inputStream
//     * @param fileName
//     * @return
//     * @throws IOException
//     */
//    private static File createTemporalFileFrom(Context context, InputStream inputStream, String fileName) throws IOException {
//        File targetFile = null;
//        if (inputStream != null) {
//            int read;
//            byte[] buffer = new byte[8 * 1024];
//            targetFile = new File(context.getCacheDir(), fileName);
//            if (targetFile.exists()) {
//                targetFile.delete();
//            }
//
//            OutputStream outputStream = new FileOutputStream(targetFile);
//            while ((read = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, read);
//            }
//            outputStream.flush();
//
//            try {
//                outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return targetFile;
//    }

//
//    /**
//     * 获取图片旋转角度
//     *
//     * @param path
//     * @return
//     */
//    private static int readPictureDegree(String path) {
//        int degree = 0;
//        try {
//            ExifInterface exifInterface = new ExifInterface(path);
//            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//
//            switch (orientation) {
//                case ExifInterface.ORIENTATION_ROTATE_90:
//                    degree = 90;
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_180:
//                    degree = 180;
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_270:
//                    degree = 270;
//                    break;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return degree;
//    }
//
//    /**
//     * 旋转图片
//     * @param angle 需旋转的角度
//     * @param bitmap 需旋转的图片
//     * @return
//     */
//    private static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
//        Bitmap angleBitmap = null;
//
//        //根据旋转角度，生产旋转矩阵
//        Matrix matrix = new Matrix();
//        matrix.postRotate(angle);
//
//        try {
//            angleBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        } catch (OutOfMemoryError error) {
//
//        }
//        if (angleBitmap == null){
//            return bitmap;
//        }
//        if (bitmap != angleBitmap){
//            bitmap.recycle();
//        }
//        return angleBitmap;
//    }
    @Override
    public void setBitmapToView(Bitmap bitmap) {
        img_take_photo.setImageBitmap(bitmap);
    }

    @Override
    public void setPresenter(PresenterTakePhoto presenter) {
        this.presenterTakePhoto = presenter;
    }
}
