package com.example.pactera.newsapplication.utils;

/**
 * Created by pactera on 2018/4/4.
 */

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 *
 * 内存缓存
 * @author admin
 *
 */
public class MemoryCacheUtils {

    private HashMap<String, Bitmap> hashlist=new HashMap<String, Bitmap>();
    /**
     *
     * 从内存中读
     * @param url
     * @return
     */
    public  Bitmap getBitmapFrommemory(String url){
        Bitmap bitmap = hashlist.get(url);
        return bitmap;
    }


    /**
     *
     * 写入内存
     * @param url
     * @param bitmap
     */
    public  void setBitmapTomemory(String url,Bitmap bitmap){
        hashlist.put(url, bitmap);
    }
}