package com.example.pactera.newsapplication.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by pactera on 2018/4/4.
 */
public class LocalCacheUtils {
    private static final String CACHE_PATH= Environment.getExternalStorageDirectory()
            +"/news_img";
    /**
     *
     * 从本地读图片
     * @param url
     */
    public Bitmap getBitmapFromlocal(String url){
        try {
            String filename = MD5Encoder.encode(url);

            File file=new File(CACHE_PATH);//通过父文件夹与自己的文件名称来创建一个文件

            if(file.exists()){
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                return bitmap;
            }else {
                file.mkdir();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    /**
     *
     * 将图片写到本地
     * @param url
     * @param bitmap
     */
    public void setBitmapTolocal(String url,Bitmap bitmap){
        try {
            String filename = MD5Encoder.encode(url);
            File file=new File(filename);
            File parentFile = file.getParentFile();
            if(!parentFile.exists()){//如果文件夾不存在，則创建
                file.mkdirs();
            }
            //将图片保存到本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    new FileOutputStream(file));


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
