package com.my.forward.mvp.sourcequery.presenter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.my.forward.mvp.sourcequery.bean.Bean_l;
import com.my.forward.mvp.sourcequery.biz.IGetCodeListtener;
import com.my.forward.mvp.sourcequery.biz.IOnLoginListener;
import com.my.forward.mvp.sourcequery.biz.IOnQuerySourceListener;
import com.my.forward.mvp.sourcequery.biz.ISourceQuery;
import com.my.forward.mvp.sourcequery.biz.SourceAndLoginBiz;
import com.my.forward.mvp.sourcequery.view.ISourceView;

import java.io.InputStream;

/**
 * Created by 123456 on 2018/2/9.
 * presenter是view层和model层交互的桥梁
 */

public class SourcePresenter {
    private ISourceQuery sourceQuery;
    private ISourceView view;
    private Bean_l bean01;
    private Handler mhalder = new Handler(Looper.getMainLooper());

    public SourcePresenter(ISourceView view) {
        sourceQuery = new SourceAndLoginBiz();
        this.view = view;
    }

    public void prepareLogin() {
        sourceQuery.prepareLogin(new IGetCodeListtener() {
            @Override
            public void getCodeSuccess(final InputStream inputStream, Bean_l bean) {
                // view.showCode(inputStream, bean);
                bean01 = bean;
                mhalder.post(new Runnable() {
                    @Override
                    public void run() {
                        view.showCode(inputStream);
                    }
                });
            }


            @Override
            public void getCodeFailure(final String s) {
                //   view.showCodeError(s);
                mhalder.post(new Runnable() {
                    @Override
                    public void run() {
                        view.showCodeError(s);
                    }
                });
            }

            @Override
            public void getViewStateError(final String s) {
                mhalder.post(new Runnable() {
                    @Override
                    public void run() {
                        view.showViewStateError(s);
                    }
                });
            }
        });

    }


    public void login() {
        bean01.setStuNo(view.getstudNo());
        bean01.setStuPs(view.getstuPs());
        bean01.setCode(view.getCode());
        Log.i("000", bean01.toString());


        sourceQuery.login(bean01, new IOnLoginListener() {
            @Override
            public void OnLoginSuccess() {
                mhalder.post(new Runnable() {
                    @Override
                    public void run() {
                        view.showLoginSuccess();
                    }
                });
            }

            @Override
            public void OnLoginError(String s) {
                mhalder.post(new Runnable() {
                    @Override
                    public void run() {
                        view.showLoginError();
                    }
                });
            }
        }, new IOnQuerySourceListener() {
            @Override
            public void OnQuerySuccess() {

                mhalder.post(new Runnable() {
                    @Override
                    public void run() {
                        view.showSource();
                    }
                });
            }

            @Override
            public void OnError(final String s) {

                mhalder.post(new Runnable() {
                    @Override
                    public void run() {
                        view.showSourceError(s);
                    }
                });
            }
        });
    }
}
