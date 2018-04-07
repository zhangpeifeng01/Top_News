package com.example.pactera.newsapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pactera.newsapplication.R;
import com.example.pactera.newsapplication.newsbean.NewsData;

import java.util.List;

/**
 * Created by pactera on 2017/11/21.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHoder>{
    private Context mcontext;
    private List<NewsData.ResultBean.DataBean> mlist;
    private OnItemClickLitener mOnItemClickLitener;

    public RecyclerAdapter(Context mcontext, List<NewsData.ResultBean.DataBean> mlist) {
        this.mcontext = mcontext;
        this.mlist = mlist;
    }

    public void rovmeItem(int position){
        mlist.remove(position);
        notifyDataSetChanged();
    }
    public void addItem(int position){

    }


    @Override
    public MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.recycler_item, null);
        final MyViewHoder myViewHoder=new MyViewHoder(view);
        return myViewHoder;
    }

    @Override
    public void onBindViewHolder(final MyViewHoder holder, final int position) {

        holder.item_tv_top.setText(mlist.get(position).getTitle());
        holder.item_tv_bottom.setText(mlist.get(position).getAuthor_name());
        holder.item_tv_time.setText(mlist.get(position).getDate());
        if (mlist.get(position).getThumbnail_pic_s()!=null&&!mlist.get(position).getThumbnail_pic_s().equals(null)&&mlist.get(position).getThumbnail_pic_s()!=""){
//            Glide.with(mcontext).load(mlist.get(position).getThumbnail_pic_s())
//                    .asBitmap().into(new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                    holder.imageView.setImageBitmap(resource);
//                }
//            }); //方法中设置asBitmap可以设置回调类型
            Glide.with(mcontext).load(mlist.get(position).getThumbnail_pic_s())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .override(100, 100)
                    .into(holder.imageView);

        }else if (mlist.get(position).getThumbnail_pic_s()!=null&&!mlist.get(position).getThumbnail_pic_s02().isEmpty()){
            Glide.with(mcontext).load(mlist.get(position).getThumbnail_pic_s02())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .override(100, 100)
                    .into(holder.imageView);
        }

        if (mOnItemClickLitener!=null){

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemClick(holder.itemView,position);
         }
        });
            holder.item_tv_top.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    mOnItemClickLitener.onItemLongClick(holder.itemView,position);
                    return false;
                }
            });
        }
 }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class MyViewHoder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private  TextView item_tv_top;
        private  TextView item_tv_bottom;
        private  TextView item_tv_time;
        public MyViewHoder(View itemView) {
            super(itemView);
          imageView= (ImageView) itemView.findViewById(R.id.item_iv);
           item_tv_top= (TextView) itemView.findViewById(R.id.item_tv_top);
            item_tv_bottom= (TextView) itemView.findViewById(R.id.item_tv_bottom);
            item_tv_time= (TextView) itemView.findViewById(R.id.item_tv_time);

        }
    }
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
}
