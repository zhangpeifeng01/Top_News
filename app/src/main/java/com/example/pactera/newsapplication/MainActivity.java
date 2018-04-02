package com.example.pactera.newsapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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

import com.example.pactera.newsapplication.adapter.RecyclerAdapter;
import com.example.pactera.newsapplication.newsbean.NewsData;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView_;
    private RadioGroup radioGroup;
    private RadioButton rb_top;
    private RadioButton rb_yule;
    final static String URL_="http://v.juhe.cn/toutiao/index?type=top" +
            "&key=386d3190efa79dda5048f554a62a0178";
    RecyclerAdapter adapter;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                final List<NewsData.ResultBean.DataBean> nrd= (List<NewsData.ResultBean.DataBean>) msg.obj;
                adapter=new RecyclerAdapter(MainActivity.this,nrd);
                recyclerView_.setAdapter(adapter);
                adapter.setOnItemClickLitener(new RecyclerAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent=new Intent(MainActivity.this,ShowActivity.class);
                        intent.putExtra("url", nrd.get(position).getUrl());
                        intent.putExtra("title", nrd.get(position).getTitle());
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetData();
        recyclerView_= (RecyclerView) findViewById(R.id.recyclerview);
        radioGroup= (RadioGroup) findViewById(R.id.rg);
        recyclerView_.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rb_top= (RadioButton) findViewById(R.id.rb_top);
        rb_yule= (RadioButton) findViewById(R.id.rb_yule);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_.setLayoutManager(layoutManager);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
               switch (i){
                   case R.id.rb_top:

                       break;
                   case R.id.rb_yule:
                       break;
               }
            }
        });



    }
    public void GetData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(URL_)//请求接口。如果需要传U拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                       Gson gson=new Gson();
                        NewsData newsData = gson.fromJson(response.body().string(), NewsData.class);
                        List<NewsData.ResultBean.DataBean> data = newsData.getResult().getData();
                        Log.d("news",data.toString());


                        Message message =new Message();
                        message.what=1;
                      message.obj = data;
                        handler.sendMessage(message);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
