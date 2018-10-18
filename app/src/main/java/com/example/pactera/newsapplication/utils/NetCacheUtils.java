package com.example.pactera.newsapplication.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pactera on 2018/4/4.
 */
public class NetCacheUtils {
    /**
     *
     * 从网络下载图片
     * @author admin
     *
     */
        private LocalCacheUtils mlocalcacheutils;
        private MemoryCacheUtils mmemorycacheutils;

        public NetCacheUtils(LocalCacheUtils localcacheutils, MemoryCacheUtils memorycacheutils) {
            mlocalcacheutils=localcacheutils;
            mmemorycacheutils=memorycacheutils;
        }

        public void getBitmapFromNet(ImageView iv_photo, String url) {
            // TODO Auto-generated method stub
            BitmapTask bitmaptask=new BitmapTask();
            bitmaptask.execute(iv_photo,url);//开启AsyncTask，参数在doInBackground获取
        }
    /*AsyncTask  异步任务即做一些简单的异步处理  ；是handle与线程池的封装
     * 第一个泛型：参数类型泛型
     * 第二个泛型：更新进度泛型
     * 第三个泛型：onProgressUpdate的返回结果的泛型
     *
     */

        class BitmapTask extends AsyncTask<Object, Void, Bitmap> {

            private ImageView pic;
            private String murl;
            /**
             * 后台耗时方法在此执行，子线程
             */
            @Override
            protected Bitmap doInBackground(Object... params) {

                pic = (ImageView) params[0];
                murl = (String) params[1];

                pic.setTag(murl);
                //将图片与url绑定
                return downloadBitmap(murl);
            }
            /**
             * 更新进度，主线程
             */
            @Override
            protected void onProgressUpdate(Void... values) {
                // TODO Auto-generated method stub
                super.onProgressUpdate(values);
            }
            /**
             * 后台耗时方法结束之后，在此执行，主线程
             */
            @Override
            protected void onPostExecute(Bitmap result) {
                if(result!=null){

                    String tag = (String) pic.getTag();
                    if(tag.equals(murl)){
                        pic.setImageBitmap(result);
                    }

                }
                mlocalcacheutils.setBitmapTolocal(murl, result);
                mmemorycacheutils.setBitmapTomemory(murl, result);
                System.out.println("从网络上加载图片啦");

            }
        }

        /**
         *
         * 下载图片
         * @return
         */
        private Bitmap downloadBitmap(String url){
            HttpURLConnection conn=null;
            try {
                conn=(HttpURLConnection) new URL(url)
                        .openConnection();

                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestMethod("GET");
                conn.connect();

                int responseCode = conn.getResponseCode();//响应码

                if(responseCode==200){//表示成功连接
                    InputStream inputStream = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
            finally{
                conn.disconnect();
            }
            return null;

        }
    }

