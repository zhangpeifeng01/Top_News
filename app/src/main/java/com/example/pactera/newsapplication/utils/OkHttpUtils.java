package com.example.pactera.newsapplication.utils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by pactera on 2017/11/20.
 */
public class OkHttpUtils {
    private static  final  OkHttpClient M_OKHTTP=new OkHttpClient();
    static {
        M_OKHTTP.setConnectTimeout(30, TimeUnit.SECONDS);
    }
    public static void ok_Http(Request request, Callback callback){
            M_OKHTTP.newCall(request).enqueue(callback);
    }


}
