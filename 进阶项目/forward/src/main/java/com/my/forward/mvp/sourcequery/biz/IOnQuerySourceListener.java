package com.my.forward.mvp.sourcequery.biz;

/**
 * Created by 123456 on 2018/2/9.
 */

public interface IOnQuerySourceListener {
    void OnQuerySuccess();

    void OnError(String s);
}
