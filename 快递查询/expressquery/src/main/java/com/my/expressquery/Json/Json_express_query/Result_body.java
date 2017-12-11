package com.my.expressquery.Json.Json_express_query;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 123456 on 2017/9/30.
 */
/*
最底层往最外层写，详情看请求后返回的json数据
   */
public class Result_body {
    private String mailNo;

    private String updateStr;

    private String expSpellName;

    public String getExpSpellName() {
        return expSpellName;
    }

    public void setExpSpellName(String expSpellName) {
        this.expSpellName = expSpellName;
    }

    private int ret_code;

    private int status;

    private String tel;

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @SerializedName("data")
    private List<Data> datas;

    private String expTextName; //快递名称

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public String getUpdateStr() {
        return updateStr;
    }

    public void setUpdateStr(String updateStr) {
        this.updateStr = updateStr;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public int getStatus() {
        return status;
    }

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getExpTextName() {
        return expTextName;
    }

    public void setExpTextName(String expTextName) {
        this.expTextName = expTextName;
    }
}
