package com.example.pactera.newsapplication.mydb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pactera on 2018/4/3.
 */
public class MySQLiteDb extends SQLiteOpenHelper {
    public MySQLiteDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//创建头条数据库表
        String top = "create table Top_table(_id INTEGER PRIMARY KEY AUTOINCREMENT,author_name varchar(20),category varchar(20)" +
                ",date varchar(20),thumbnail_pic_s varchar(20),thumbnail_pic_s02 varchar(20)" +
                ",thumbnail_pic_s03 varchar(20) ,title varchar(20),uniquekey varchar(20)" +
                ",url varchar(20))";
        String yule = "create table YuLe_table(_id INTEGER PRIMARY KEY AUTOINCREMENT,author_name varchar(20),category varchar(20)" +
                ",date varchar(20),thumbnail_pic_s varchar(20),thumbnail_pic_s02 varchar(20)" +
                ",thumbnail_pic_s03 varchar(20) ,title varchar(20),uniquekey varchar(20)" +
                ",url varchar(20))";
        sqLiteDatabase.execSQL(top);
        sqLiteDatabase.execSQL(yule);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
