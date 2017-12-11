package com.my.expressquery.Json.Json_Network_Query;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 123456 on 2017/11/11.
 */

public class Net_Root {

    @SerializedName("showapi_res_code")
    private int res_code;

    @SerializedName("showapi_res_body")
    private Net_Body body;

    public int getRes_code() {
        return res_code;
    }

    public void setRes_code(int res_code) {
        this.res_code = res_code;
    }

    public Net_Body getBody() {
        return body;
    }

    public void setBody(Net_Body body) {
        this.body = body;
    }


}
