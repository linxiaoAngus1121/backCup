package com.my.expressquery.Json.Json_express_query;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 123456 on 2017/9/30.
 */

//最底层往最外层写，详情看请求后返回的json数据
//  @SerializedName("showapi_res_body")对应json数据中的字段名，一定要对应，不然无法解析

public class Root {
    @SerializedName("showapi_res_code")
    private int res_code;
    @SerializedName("showapi_res_body")
    private Result_body result_body;

    public int getRes_code() {
        return res_code;
    }

    public void setRes_code(int res_code) {
        this.res_code = res_code;
    }

    public Result_body getResult_body() {

        return result_body;
    }

    public void setResult_body(Result_body result_body) {
        this.result_body = result_body;
    }

}
