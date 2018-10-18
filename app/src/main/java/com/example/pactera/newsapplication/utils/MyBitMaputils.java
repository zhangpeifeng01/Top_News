package com.example.pactera.newsapplication.utils;

/**
 * Created by pactera on 2018/4/4.
 */

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.pactera.newsapplication.R;

/**
 *
 *
 * 自定义图片加载工具
 * @author admin
 *
 */
public class MyBitMaputils {
    NetCacheUtils netcache;
    LocalCacheUtils localcacheutils;
    MemoryCacheUtils memorycacheutils;
    public MyBitMaputils(){
        memorycacheutils=new MemoryCacheUtils();
        localcacheutils=new LocalCacheUtils();
        netcache=new NetCacheUtils(localcacheutils,memorycacheutils);

    }
    Bitmap bitmap =null;
    public void display(ImageView iv_photo, String url) {
        iv_photo.setImageResource(R.mipmap.ic_launcher);//默认图片，防止图片的复用
        //内存缓存
        bitmap= memorycacheutils.getBitmapFrommemory(url);
        if(bitmap!=null){
            iv_photo.setImageBitmap(bitmap);
            System.out.println("从内存中读取图片");
            return;
        }
        //本地缓存
        bitmap = localcacheutils.getBitmapFromlocal(url);
        if(bitmap!=null){
            iv_photo.setImageBitmap(bitmap);
            memorycacheutils.setBitmapTomemory(url, bitmap);
            System.out.println("从本地读取图片");
            return;//从本地读取就不需要从网络读取了
        }

        //网络缓存（第一次）
        netcache.getBitmapFromNet(iv_photo,url);
    }


}
