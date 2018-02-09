package com.my.forward.mvp.query.presenter_q;

import android.util.Log;

import com.my.forward.mvp.query.biz_q.IQuery;
import com.my.forward.mvp.query.biz_q.OnQueryListener;
import com.my.forward.mvp.query.biz_q.QueryBiz;
import com.my.forward.mvp.query.view_q.ShowView;

/**
 * Created by 123456 on 2018/2/1.
 * 这几个类是用来查询需要快递代拿（展示在recycleView）中的。
 */

public class QueryPresenter {
    private IQuery query;
    private ShowView show;

    public QueryPresenter(ShowView show) {
        query = new QueryBiz();
        this.show = show;
    }

    public void Query_show() {
        query.query(new OnQueryListener() {
            @Override
            public void querySuccess(String data) {
                //activity开始显示数据,这里是在异步线程里,需要用handler
                Log.i("000", data + "presenter接受到数据了");
            }

            @Override
            public void queryFailed() {
                Log.i("000", "gg了，查询出问题++++++QueryPresenter");
            }
        });
    }
}
