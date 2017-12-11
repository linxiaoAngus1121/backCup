package com.my.expressquery.Json.Json_express_query;

/**
 * Created by 123456 on 2017/9/30.
 */
/*
最底层往最外层写，详情看请求后返回的json数据*/


public class Data {
    private String time;

    private String context;

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return this.context;
    }

}
