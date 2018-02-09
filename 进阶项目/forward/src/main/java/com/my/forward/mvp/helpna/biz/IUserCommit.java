package com.my.forward.mvp.helpna.biz;

import com.my.forward.mvp.helpna.bean.Information;

/**
 * Created by 123456 on 2018/1/31.
 * commit方法就是最终的提交方法
 */

public interface IUserCommit {
    void commit(Information information, OnCommitListener listener);
}
