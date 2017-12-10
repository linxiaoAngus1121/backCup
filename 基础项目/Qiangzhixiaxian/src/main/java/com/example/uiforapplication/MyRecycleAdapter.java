package com.example.uiforapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by 123456 on 2017/7/12.
 */

public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyViewHolder> {
   private List<String> myData;
    private  Context mContext;
    private    LayoutInflater inflater;
    public  MyRecycleAdapter(Context context,List<String> dataList){
         myData = dataList;
        mContext=context;
        inflater  = LayoutInflater.from(mContext);
    }


    //必须重写下面3个方法

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     //将layout转成view,再把view传给viewholder

        View view = inflater.inflate(R.layout.activity_recyle_view,null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }



    //此方法绑定数据给text view

    public void onBindViewHolder(MyViewHolder holder, int position) {
           holder.tv.setText(myData.get(position));
    }


    //返回数据的长度
    @Override
    public int getItemCount() {
        return myData.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
              tv=(TextView) itemView.findViewById(R.id.item1);
        }
    }
}
