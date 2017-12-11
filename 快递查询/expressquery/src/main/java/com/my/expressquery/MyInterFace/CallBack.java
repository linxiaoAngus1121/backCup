package com.my.expressquery.MyInterFace;

/**
 * Created by 123456 on 2017/11/3.
 * 请求快递数据成功后的那个string数据，
 */

public interface CallBack {
    //第一个参数为快递的详情，第二个为快递的名称，在Mianfragment中要用
    void success(String strings, String ex_name, String code);

    void error(String strings);
}
