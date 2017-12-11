package com.my.expressquery.MyInterFace;

import com.my.expressquery.db.Data;

import java.util.List;

/**
 * Created by 123456 on 2017/11/4.
 * 用于从网络请求数据展示到my_press那个fragment
 */

public interface CallBckGetData {
    void getdata(List<Data> list);

}
