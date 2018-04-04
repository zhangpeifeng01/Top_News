package com.example.pactera.newsapplication.mydb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pactera.newsapplication.newsbean.NewsData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pactera on 2018/4/3.
 */
public class SQLiteUtils {

    Context context;
    SQLiteDatabase db;
    public SQLiteUtils( Context context) {
        this.context = context;
         MySQLiteDb dbHelper = new MySQLiteDb(context,context.getFilesDir().getAbsolutePath()+ "/databases/news_db.db",null,1);
       //得到一个可读的SQLiteDatabase对象
        db =dbHelper.getWritableDatabase();

           }


    public void InsertDB(NewsData.ResultBean.DataBean topdb){
        //生成ContentValues对象 //key:列名，value:想插入的值
        ContentValues cv = new ContentValues();
        cv.put("author_name",topdb.getAuthor_name());
        cv.put("category",topdb.getCategory());
        cv.put("date",topdb.getDate());
        cv.put("thumbnail_pic_s",topdb.getThumbnail_pic_s());
        cv.put("thumbnail_pic_s02",topdb.getThumbnail_pic_s02());
        cv.put("thumbnail_pic_s03",topdb.getThumbnail_pic_s03());
        cv.put("title",topdb.getTitle());
        cv.put("uniquekey",topdb.getUniquekey());
        cv.put("url",topdb.getUrl());
        db.insert("Top_table",null,cv);

    }
    public List<NewsData.ResultBean.DataBean> QueryDB(){
        List<NewsData.ResultBean.DataBean> list=new ArrayList<>();
      String sql="select * from Top_table order by _id ;";
                 Cursor cursor=db.rawQuery(sql,null);
     while (cursor.moveToNext()) {
            String author_name = cursor.getString(cursor.getColumnIndex("author_name"));
            String category = cursor.getString(cursor.getColumnIndex("category"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String thumbnail_pic_s = cursor.getString(cursor.getColumnIndex("thumbnail_pic_s"));
            String thumbnail_pic_s02 = cursor.getString(cursor.getColumnIndex("thumbnail_pic_s02"));
            String thumbnail_pic_s03 = cursor.getString(cursor.getColumnIndex("thumbnail_pic_s03"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String uniquekey = cursor.getString(cursor.getColumnIndex("uniquekey"));
         NewsData.ResultBean.DataBean dataBean = new NewsData.ResultBean.DataBean(author_name, category,
                 date, thumbnail_pic_s, thumbnail_pic_s02, thumbnail_pic_s03,
                 title, url, uniquekey);
         list.add(dataBean);
     }

        return list;
    }
}
