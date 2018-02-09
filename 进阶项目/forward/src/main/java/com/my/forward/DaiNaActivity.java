package com.my.forward;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.my.forward.adapter.RecycleAdapter;
import com.my.forward.mvp.query.presenter_q.QueryPresenter;
import com.my.forward.mvp.query.view_q.ShowView;

import java.util.ArrayList;
import java.util.List;

public class DaiNaActivity extends AppCompatActivity implements ShowView {

    private List<String> list = new ArrayList<>();  //这两个数据应该是从数据库中查询的，文字
    private List<Integer> im_src = new ArrayList<>(); //图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daina_layout);
        RecyclerView view = (RecyclerView) findViewById(R.id.show_sos);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager
                .VERTICAL, false);
        view.setLayoutManager(manager);

      /*  for (int i = 0; i < 20; i++) {
            list.add("四六级冲刺指南|这份拿走就够了" + i);
            im_src.add(R.drawable.shoe);
        }*/
        QueryPresenter presenter = new QueryPresenter(this);
        presenter.Query_show();
        view.setAdapter(new RecycleAdapter(this, list, im_src));
    }

    @Override
    public void show() {
        Log.i("000", "成功接受数据，接下来显示");
    }
}
