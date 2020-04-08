package com.example.lpwd.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "lpwd";
    private static int DB_VERSION = 1;


    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("创建数据库和表");
        //创建了数据库并创建一个叫records的表
        //SQLite数据创建支持的数据类型： 整型数据，字符串类型，日期类型，二进制的数据类型
        String sql = "create table if not exists user(id Integer primary key AUTOINCREMENT,name varchar(200),pwd varchar(200))";
        //execSQL用于执行SQL语句
        //完成数据库的创建
        db.execSQL(sql);
        //数据库实际上是没有被创建或者打开的，直到getWritableDatabase() 或者 getReadableDatabase() 方法中的一个被调用时才会进行创建或者打开
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
