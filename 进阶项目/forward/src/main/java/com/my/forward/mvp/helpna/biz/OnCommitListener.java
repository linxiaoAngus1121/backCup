package com.my.forward.mvp.helpna.biz;

/**
 * Created by 123456 on 2018/1/31.
 * UserCommitBiz需要的回调接口，返回给调用者说失败还是成功
 */

public interface OnCommitListener {
    void commitSuccess();

    void commitFailed();
}
