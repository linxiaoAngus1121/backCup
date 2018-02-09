package com.my.forward.mvp.query.biz_q;

/**
 * Created by 123456 on 2018/2/1.
 */

public interface OnQueryListener {
    void querySuccess(String data);
    void queryFailed();
}
