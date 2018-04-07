package com.example.pactera.newsapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobads.AppActivity;
import com.example.pactera.newsapplication.Fagment.TopFagment;
import com.example.pactera.newsapplication.Fagment.YuLeFagment;
import com.example.pactera.newsapplication.adapter.RecyclerAdapter;
import com.example.pactera.newsapplication.newsbean.NewsData;
import com.example.pactera.newsapplication.utils.NetworkUtil;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final int REQUESTCODE = 101;
    private RecyclerView recyclerView_;
    private RadioGroup radioGroup;
    private RadioButton rb_top;
    private RadioButton rb_yule;
    final static String URL_ = "http://v.juhe.cn/toutiao/index?type=top" +
            "&key=386d3190efa79dda5048f554a62a0178";
    RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkSelfPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTCODE);
            }}
        // 目前开放了七大主题：黑色、蓝色、咖啡色、绿色、藏青色、红色、白色(默认) 主题
        AppActivity
                .setActionBarColorTheme(AppActivity.ActionBarColorTheme.ACTION_BAR_WHITE_THEME);
        radioGroup = (RadioGroup) findViewById(R.id.rg);
        rb_top = (RadioButton) findViewById(R.id.rb_top);
        rb_yule = (RadioButton) findViewById(R.id.rb_yule);
        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //设置为默认界面 MainHomeFragment
        ft.add(R.id.framelayout, mFragments[0]).commit();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_top:
                        setIndexSelected(0);
                        break;
                    case R.id.rb_yule:
                        setIndexSelected(1);
                        break;
                }
            }
        });

    }

    //数组 存储Fragment
    Fragment[] mFragments = new Fragment[]{
            new TopFagment(),
            new YuLeFagment(),
    };
    //当前Fragent的下标

    private int currentIndex;

    //设置Fragment页面
    private void setIndexSelected(int index) {
        if (currentIndex == index) {
            return;
        }
        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //隐藏当前Fragment
        ft.hide(mFragments[currentIndex]);
        //判断Fragment是否已经添加
        if (!mFragments[index].isAdded()) {
            ft.add(R.id.framelayout, mFragments[index]).show(mFragments[index]);
        } else {
            //显示新的Fragment
            ft.show(mFragments[index]);
        }
        ft.commit();
        currentIndex = index;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTCODE) {

            } else {
                //用户不同意

            }
        }

}

