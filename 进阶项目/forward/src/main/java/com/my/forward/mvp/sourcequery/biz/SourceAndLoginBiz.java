package com.my.forward.mvp.sourcequery.biz;

import android.os.Environment;
import android.util.Log;

import com.my.forward.mvp.sourcequery.bean.Bean_l;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 123456 on 2018/2/9.
 * 登录，查询成绩类
 */

public class SourceAndLoginBiz implements ISourceQuery {
    private OkHttpClient client = new OkHttpClient();
    private String stuName; //学生的名字



    @Override
    public void login(Bean_l bean, IOnLoginListener listener, IOnQuerySourceListener
            querySourceListener) {
        tologin(bean, listener, querySourceListener);
    }

    @Override
    public void prepareLogin(IGetCodeListtener listener) {
        getVIEWSTATE(listener);
    }

    /**
     * 获取页面的viewstate
     *
     * @param listener 回调监听
     * @return Beab_l 返回的实体类
     */
    private void getVIEWSTATE(final IGetCodeListtener listener) {
        final Bean_l bean = new Bean_l();
        final Request request = new Request.Builder()
                .url("http://jwxt.sontan.net/")
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("000", e.toString());
                listener.getViewStateError(e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.body() != null) {
                    final String string = response.body().string();
                    Headers headers = response.headers();
                    List<String> values = headers.values("Set-Cookie");
                    String routeid = null;
                    String session_id = null;
                    for (int i = 0; i < values.size(); i++) {
                        if (i == 0) {
                            int indexOf = values.get(0).indexOf(";");
                            String substring = values.get(0).substring(0, indexOf);
                            routeid = substring + ";";
                            Log.i("000", routeid + "截取了乐乐乐乐乐乐");
                        } else {
                            int indexOf = values.get(1).indexOf(";");
                            session_id = values.get(1).substring(0, indexOf);
                            session_id = session_id + ";";
                            Log.i("000", session_id + "sessionid");
                        }
                    }
                    String finalcookies = routeid + session_id;

                    bean.setCookies(finalcookies);
                    Log.i("000", finalcookies);
                    String substring = string.substring(2282, 2302);
                    Log.i("000", substring + "截取后的__VIEWSTATE");
                    bean.setViewState(substring);
                    getpic(bean, listener);
                }
            }
        });
    }


    /**
     * viewstate获取后就要获取图片验证码
     *
     * @param bean     里面存放的是登录所需的信息，一步一步拼接
     * @param listener 回调监听
     */
    private void getpic(final Bean_l bean, final IGetCodeListtener listener) {
        Request request = new Request.Builder()
                .get()
                .url("http://jwxt.sontan.net/CheckCode.aspx")
                .header("Cookie", bean.getCookies())
                .header("Connection", " Keep-Alive")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("000", e.toString() + "获取验证码失败了");
                listener.getCodeFailure(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    final InputStream inputStream = response.body().byteStream();
                    listener.getCodeSuccess(inputStream, bean);
                }
            }
        });
    }


    /**
     * 执行登录
     *
     * @param bean     实体类对想，包含登录所需要的信息
     * @param listener 回调监听
     */
    public void tologin(final Bean_l bean, final IOnLoginListener listener,
                        final IOnQuerySourceListener querySourceListener) {
        FormBody body = buildBody(bean);
        Request request = new Request.Builder()
                .post(body)
                .header("Content-Type", "application/x-www-form-urlencoded")
                // TODO: 2018/2/7  因为这个是要和服务器下发的cookies保持一致才能进行登录，之前的就是这里出错，所以不能登录.
                .header("Cookie", bean.getCookies())
                .header("Connection", "keep-alive")
                .header("Accept-Language", "zh-CN,zh;q=0.8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0)" +
                        " like Gecko")
                .header("Content-Length", "230")
                .url("http://jwxt.sontan.net/Default2.aspx")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("000", e.toString() + "嘎嘎嘎嘎嘎嘎嘎嘎嘎");
                listener.OnLoginError(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //这里不应全部读取，当读取到同学的时候，应该停止读取了
                if (response.code() != 200) {
                    listener.OnLoginError(response.message());
                    return;
                }
                InputStream inputStream = response.body().byteStream();
                //网页上采取的是gb2312格式，所以这里要采用一致的才不会乱码
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,
                        "gb2312"));
                StringBuilder sb = new StringBuilder();
                String line;
                int indexOf;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    indexOf = sb.indexOf("同学");
                    if (indexOf != -1) {   //找到了
                        Log.i("000", "找到了");
                        Log.i("000", sb.toString());
                        listener.OnLoginSuccess();
                        stuName = getstName(sb.toString(), indexOf);
                        break;
                    }
                }
                //进入查询成绩的流程
                toGradeQurry(bean, querySourceListener);
            }
        });
    }


    /**
     * 进行成绩查询
     *
     * @param bean                实体类
     * @param querySourceListener 成绩查询回调
     */
    private void toGradeQurry(final Bean_l bean, final IOnQuerySourceListener querySourceListener) {
        final Request request = new Request.Builder()
                .header("Cookie", bean.getCookies())
                .header("Referer", "http://jwxt.sontan.net/xs_main.aspx?xh=" + bean.getStuNo())
                .header("Connection", "keep-alive")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0)" +
                        " like Gecko")
                .url("http://jwxt.sontan.net/xscjcx.aspx?xh=" +
                        bean.getStuNo() + "&xm=学生&gnmkdm=N121605")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("000", e.toString() + "成绩失败了");
                querySourceListener.OnError(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    Log.i("000", "进入成绩成功了");
                    //接下来点击历年成绩，但是首先要去获取到页面上的__VIEWSTATE
                    InputStream inputStream = response.body().byteStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    int indexOf = -1;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        indexOf = sb.indexOf("__VIEWSTATE\" value=");
                        if (indexOf != -1) {   //找到了
                            Log.i("000", "找到了");
                            Log.i("000", sb.toString());
                            break;
                        }
                    }
                    //substring这里是最终需要的__VIEWSTATE
                    String substring = sb.toString().substring(indexOf);
                    String[] split = substring.split("value=\"", 2);
                    Log.i("000", split[0] + "这是0的部分");
                    Log.i("000", split[1] + "这是1的部分");  //取1这一部分
                    String[] split1 = split[1].split("\"", 2);//继续splite
                    Log.i("000", split1[0] + "这是0的部分+++++");    //最终需要的
                    Log.i("000", split1[1] + "这是1的部分+++++");
                    substring = split1[0];
                    //默认开始历年成绩查询
                    pastScouce(bean, substring, querySourceListener);
                }
            }
        });
    }


    /**
     * 历年成绩查询
     *
     * @param bean                实体类
     * @param substring           需要的参数__VIEWSTATE
     * @param querySourceListener 回调
     */
    private void pastScouce(Bean_l bean, String substring, final IOnQuerySourceListener
            querySourceListener) {
        FormBody body = buildBody(substring);
        // TODO: 2018/2/7 这里要获取登录中爬到的姓名 ，先默认是自己的名字
        String url = "http://jwxt.sontan.net/xscjcx.aspx?xh=" + bean.getStuNo() +
                "&xm=" + stuName + "&gnmkdm=N121605";
        Log.i("000", url);
        // TODO: 2018/2/7 这里要获取登录中爬到的姓名 ，先默认是自己的名字,这也一样
        String utf8Togb2312 = utf8Togb2312(stuName);
        Request request = new Request
                .Builder()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0)" +
                        " like Gecko")
                .header("Accept-Language", "zh-CN,zh;q=0.8")
                .header("Connection", " Keep-Alive")
                .header("Content-Length", "3946")
                .header("Cookie", bean.getCookies())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "text/html, application/xhtml+xml, image/jxr, */*")
                .header("Referer", "http://jwxt.sontan.net/xscjcx.aspx?xh=" + bean.getStuNo()
                        + "&xm=" + utf8Togb2312 + "&gnmkdm=N121605")
                //%C1%D6%CF%FE对应着gb2312的中文编码表，网址http://www.knowsky.com/resource/gb2312tbl.htm
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("000", e.getMessage() + "历年成绩失败");
                querySourceListener.OnError(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    Log.i("000", "查询历年成绩成功");
                    querySourceListener.OnQuerySuccess();
                    file(response.body().byteStream());
                }
            }
        });
    }


    private void file(InputStream inputStream) throws FileNotFoundException {
        File file = new File(Environment.getExternalStorageDirectory(), "12306.txt");
        FileOutputStream out = new FileOutputStream(file);

        byte[] b = new byte[1024];
        try {
            while ((inputStream.read(b) != -1)) {
                out.write(b);
            }
            out.close();
            Log.i("000", "文件成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将名字转成gb2312编码，并拼接返回
     *
     * @param name 名字
     * @return 返回拼接后的数据
     */
    private String utf8Togb2312(String name) {
        byte[] bytes = new byte[0];//先把字符串按gb2312转成byte数组
        try {
            bytes = name.getBytes("gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] a = new String[10];
        int i = 0;
        for (byte b : bytes) {//循环数组
            String s = Integer.toHexString(b);//再用Integer中的方法，把每个byte转换成16进制输出
            Log.i("000", s);
            String substring = s.substring(6, 8).toUpperCase();//可以了
            a[i] = substring;
            i++;
            Log.i("000", substring);
/*
            输出示例
            ffffffc2
            ffffffc8
            ffffffa8
            ffffffcd
            fffffffe
             */
            //去掉前面6个f就是每个名字的gb2312编码
        }
        StringBuilder builder = new StringBuilder();
        for (String anA : a) {
            if (anA == null) {
                continue;
            }
            builder.append("%");
            builder.append(anA);
        }
        Log.i("000", builder.toString() + "拼接完成");

        return builder.toString();
    }


    /**
     * 构建历年成绩查询需要传入的参数
     *
     * @param substring viewstate参数
     * @return body请请求体
     */
    private FormBody buildBody(String substring) {
        return new FormBody
                .Builder()
                .add("__EVENTTARGET", "")
                .add("__EVENTARGUMENT", "")
                .add("__VIEWSTATE", substring)
                .add("hidLanguage", "")
                .add("ddlXN", "")
                .add("ddlXQ", "")
                .add("ddl_kcxz", "")
                .add("btn_zcj", "历年成绩")
                .build();
    }


    /**
     * 获取学生的姓名
     *
     * @param s       爬取到网页上的数据
     * @param indexOf 出现了同学两个字的位置
     * @return 返回学生的姓名
     */
    private String getstName(String s, int indexOf) {
        Log.i("000", s + "要截取名字了");
        int i = s.indexOf("\"xhxm\">");
        String substring = s.substring(i + 7, indexOf);
        Log.i("000", substring + "截取到的名字");
        return substring;
    }


    /**
     * 构建登录需要传入的参数
     *
     * @return body请求体
     */
    private FormBody buildBody(Bean_l bean) {
        return new FormBody.Builder()
                .add("__VIEWSTATE", bean.getViewState())
                .add("txtUserName", bean.getStuNo())
                .add("TextBox2", bean.getStuPs())
                .add("txtSecretCode", bean.getCode())
                .add("RadioButtonList1", "学生")
                .add("Button1", "")
                .add("lbLanguage", "")
                .add("hidPdrs", "")
                .add("hidsc", "")
                .build();
    }
}
