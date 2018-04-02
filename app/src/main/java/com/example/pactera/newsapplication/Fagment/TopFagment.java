package com.example.pactera.newsapplication.Fagment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pactera.newsapplication.R;

/**
 * Created by pactera on 2018/4/2.
 */
public class TopFagment extends Fragment{
    private RecyclerView recyclerView_;
    @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_top, null);
        recyclerView_= (RecyclerView) view.findViewById(R.id.recyclerview);
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
}
