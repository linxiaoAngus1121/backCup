package com.example.materialdesign;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by 123456 on 2017/7/23.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Fruit> fruitList;
    private Context mcontext;
    public Adapter(List<Fruit> list){
        fruitList=list;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
            ImageView imageView;
            TextView textView;
       //     CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
       //     cardView=(CardView)itemView;
        //    imageView=(ImageView)  itemView.findViewById(R.id.fruit_image);
          //  textView=(TextView)  itemView.findViewById(R.id.fruit_name);
        }



    }






/*
*
* 初始化一些东西
* */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mcontext==null){
            mcontext=parent.getContext();
        }
     //   View view = LayoutInflater.from(mcontext).inflate(,android)
        //ViewHolder holder = new ViewHolder(view);
       // return holder;
        return null;
    }

    @Override
    /*
    * 绑定数据
    * */
    public void onBindViewHolder(ViewHolder holder, int position) {
                Fruit fruit=fruitList.get(position);
              holder.textView.setText(fruit.getName());
        Glide.with(mcontext).load(fruit.getImageid()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }
}
