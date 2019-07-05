package com.dream.lmy.mydream.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {


    /**
     * 构造方法
     * @param context 上下文
     * @param name 数据库名称
     * @param factory 游标工厂，一般设为null
     * @param version 数据库版本
     */
    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库时调用，在这里执行建表操作。
        db.execSQL(CREATE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //更新数据库
    }


    public static final  String CREATE_NEWS = "create table news(" +
            "id integer primary key autoincrement," +
            "title text," +
            "content text," +
            "publishdate integer," +
            "commentcount integer)";


}
