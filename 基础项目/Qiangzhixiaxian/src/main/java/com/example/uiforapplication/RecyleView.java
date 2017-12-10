package com.example.uiforapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class RecyleView extends AppCompatActivity {


    private android.support.v7.widget.RecyclerView mReview;
    private  MyRecycleAdapter adapter;
    private List<String> dataList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyle_view);
        mReview = (android.support.v7.widget.RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager mManager = new LinearLayoutManager(this);
        //设置布局管理器
        mReview.setLayoutManager(mManager);
        //设置recyclerview的布局方向
        mManager.setOrientation(OrientationHelper.VERTICAL);
        init();
        mReview.setAdapter(adapter);
    }



    private  void  init(){
        dataList=new ArrayList<String>();
        for (int i=0;i<=10;i++){
            dataList.add("item"+i);
        }

    }
}