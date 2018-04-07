package com.example.pactera.newsapplication.Fagment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pactera.newsapplication.R;
import com.example.pactera.newsapplication.ShowActivity;
import com.example.pactera.newsapplication.adapter.RecyclerAdapter;
import com.example.pactera.newsapplication.mydb.SQLiteUtils;
import com.example.pactera.newsapplication.newsbean.NewsData;
import com.example.pactera.newsapplication.utils.NetworkUtil;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class YuLeFagment extends Fragment {
    final static String URL_="http://v.juhe.cn/toutiao/index?type=yule" +
            "&key=386d3190efa79dda5048f554a62a0178";
    private RecyclerView recyclerView_;
    RecyclerAdapter adapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==2){
                final List<NewsData.ResultBean.DataBean> nrd= (List<NewsData.ResultBean.DataBean>) msg.obj;
                adapter=new RecyclerAdapter(getActivity(),nrd);
                recyclerView_.setAdapter(adapter);
                adapter.setOnItemClickLitener(new RecyclerAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent=new Intent(getActivity(),ShowActivity.class);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_top, null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_= (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView_.setLayoutManager(layoutManager);
        recyclerView_.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        GetData();
        return view;
    }
    //重写setMenuVisibility方法，不然会出现叠层的现象
    @Override
    public void setMenuVisibility(boolean menuVisibile) {
        super.setMenuVisibility(menuVisibile);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisibile ? View.VISIBLE : View.GONE);
        }
    }
    public void GetData(){
        if (NetworkUtil.isNetworkAvailable(getActivity())) {
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
                            Gson gson = new Gson();
                            NewsData newsData = gson.fromJson(response.body().string(), NewsData.class);
                            List<NewsData.ResultBean.DataBean> data = newsData.getResult().getData();
                            Log.d("news", data.toString());
                            SQLiteUtils db = new SQLiteUtils(getActivity());
                            for (int i = 0; i < data.size(); i++) {
                                db.InsertDB(data.get(i), "YuLe_table");
                            }

                            Message message = new Message();
                            message.what = 2;
                            message.obj = data;
                            handler.sendMessage(message);

                        } else {
                            SQLiteUtils db = new SQLiteUtils(getActivity());
                            List<NewsData.ResultBean.DataBean> list = db.QueryDB("YuLe_table");
                            Toast.makeText(getActivity(), "查询数据成功", Toast.LENGTH_SHORT).show();
                            Message message = new Message();
                            message.what = 1;
                            message.obj = list;
                            handler.sendMessage(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }else {
            SQLiteUtils db = new SQLiteUtils(getActivity());
            List<NewsData.ResultBean.DataBean> list = db.QueryDB("YuLe_table");
            if (list.size()!=0){
                Message message = new Message();
                message.what = 2;
                message.obj = list;
                handler.sendMessage(message);
            }else {
                Toast.makeText(getActivity(),"",Toast.LENGTH_LONG).show();
            }
           ;
        }

    }
}
