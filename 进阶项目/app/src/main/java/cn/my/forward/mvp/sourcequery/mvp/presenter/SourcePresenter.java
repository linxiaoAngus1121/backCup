package cn.my.forward.mvp.sourcequery.mvp.presenter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.my.forward.mvp.sourcequery.mvp.bean.Bean_l;
import cn.my.forward.mvp.sourcequery.mvp.bean.Bean_s;
import cn.my.forward.mvp.sourcequery.mvp.biz.IGetCodeListtener;
import cn.my.forward.mvp.sourcequery.mvp.biz.ILogin;
import cn.my.forward.mvp.sourcequery.mvp.biz.IOnLoginListener;
import cn.my.forward.mvp.sourcequery.mvp.biz.IOnQuerySourceListener;
import cn.my.forward.mvp.sourcequery.mvp.biz.ITimeTableListener;
import cn.my.forward.mvp.sourcequery.mvp.biz.SourceAndLoginBiz;
import cn.my.forward.mvp.sourcequery.mvp.view.ISourceView;
import cn.my.forward.mvp.sourcequery.mvp.view.ITimeTableView;

/**
 * Created by 123456 on 2018/2/9.
 * presenter是view层和model层交互的桥梁
 */

public class SourcePresenter {
    private static ILogin sourceQuery = SourceAndLoginBiz.getInstance();
    private ISourceView view;
    private ITimeTableView mTableView;
    private Bean_l bean01;
    private Handler mhalder = new Handler(Looper.getMainLooper());  //让handler运行在主线程中

    public SourcePresenter(ISourceView view) {
        this.view = view;
    }

    public SourcePresenter(ITimeTableView view) {
        mTableView = view;
    }

    /**
     * 获取viewstate还有其他登录所需要的信息。
     */
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
        view.closekeyboard();
        bean01.setStuNo(view.getstudNo());
        bean01.setStuPs(view.getstuPs());
        bean01.setCode(view.getCode());
        Log.i("000", bean01.toString());


        sourceQuery.login(bean01, new IOnLoginListener() {
            @Override
            public void OnLoginSuccess() {
                mhalder.post(new Runnable() {//这样view.showLoginSuccess()方法就会执行在主线程中
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
        });
    }

    public void changeCode() {
        this.prepareLogin();
    }

    public void scoureQuery() {
        sourceQuery.score(new IOnQuerySourceListener() {
            @Override
            public void OnQuerySuccess(final ArrayList<Bean_s> list) {
                mhalder.post(new Runnable() {
                    @Override
                    public void run() {
                        view.showSource(list);
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

    public void timetable() {
        sourceQuery.timeTable(new ITimeTableListener() {
            @Override
            public void QueryTimeTableSuccess(final List<String> nodes) {
                Log.i("000", "课表查询成功");
                mhalder.post(new Runnable() {
                    @Override
                    public void run() {
                        mTableView.showTimeTble(nodes);
                    }
                });
            }

            @Override
            public void QuertTimeTableFailure(final String s) {
                mhalder.post(new Runnable() {
                    @Override
                    public void run() {
                        mTableView.showTimeTbleError(s);
                    }
                });
            }
        });
    }
}
