package com.my.expressquery.HttpUtil;

import android.util.Log;

import com.google.gson.Gson;
import com.my.expressquery.Json.Json_Network_Query.Net_Data;
import com.my.expressquery.Json.Json_Network_Query.Net_Root;
import com.my.expressquery.Json.Json_express_query.Root;
import com.my.expressquery.MyInterFace.CallBack;
import com.my.expressquery.MyInterFace.CallBckGetData;
import com.my.expressquery.MyInterFace.NetWorkQuery_CallBack;
import com.my.expressquery.db.Data;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 123456 on 2017/11/2.
 * B类
 * 请求网络查询快递
 */

public class QueryUtil {

    /*
    *       快递查询
    * */
    public void queryNoName(final CallBack callBack, String odd, String code) {
        String myid = "47084";
        String mysecret = "d6be03fcaaac4165b608421214d1561f";
        String url;
        if (code == null) {        //代表没有输入快递名称，那就采用auto方式查询
            url = "http://route.showapi.com/64-19?showapi_appid=" + myid + "&com=auto" + "&nu=" +
                    odd
                    + "&showapi_sign=" + mysecret;
        } else {                  //有快递名称
            url = "http://route.showapi.com/64-19?showapi_appid=" + myid + "&com=" + code +
                    "&nu=" + odd
                    + "&showapi_sign=" + mysecret;
        }


        HttpUtil.sendHttpRequest(url, new Callback() {      //发起网络请求
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("000", "走了这里");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String string = response.body().string();
                    Log.i("000", string + "8888888888888888888888");
                    if (!string.contains("查询成功")) {
                        callBack.error("查询出错");
                        return;
                    }
                    /*
                    * json解析
                    * */
                    Gson gson = new Gson();
                    Root mroot = gson.fromJson(string, Root.class);
                    List<com.my.expressquery.Json.Json_express_query.Data> dataList = mroot
                            .getResult_body().getDatas();
                    if (dataList == null) {
                        return;
                    }
                    StringBuilder builder = new StringBuilder();
                    for (com.my.expressquery.Json.Json_express_query.Data the : dataList) {
                        builder.append(the.getContext());
                        builder.append("\n");
                        builder.append("\n");
                    }
                    /*
                    *   第一个为快递信息，第二个为快递名称，第三个为快递代号
                    * */
                    callBack.success(builder.toString(), mroot.getResult_body().getExpTextName(),
                            mroot.getResult_body().getExpSpellName());
                }
            }

        });

    }

    /*
    * 联网查询数据给第二个页面
    * */
    public void queryDataForMy_prsss(final CallBckGetData callBckGetData) {
        BmobQuery<Data> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("user", BmobUser.getCurrentUser());
        bmobQuery.findObjects(new FindListener<Data>() {
            @Override
            public void done(List<Data> list, BmobException e) {
                callBckGetData.getdata(list);   //不管是不是空，直接返回就行
            }
        });
    }

    //删除第二个页面中的数据,因为目前只支持根据objectid进行删除，所以，只能先查询objectid，再删除
    public void deleteData(final CallBack callBack, String odd) {
        BmobQuery<Data> dataBmobQuery = new BmobQuery<>();
        Log.i("000", BmobUser.getCurrentUser().getObjectId() + "这是当前user的objectid");
        dataBmobQuery.addWhereEqualTo("odd", odd);
        dataBmobQuery.addWhereEqualTo("user", BmobUser.getCurrentUser());
        //需要有这个限制条件，不然会删除别的用户的数据
        dataBmobQuery.addQueryKeys("objectId");         //只查询objectId这一列
        dataBmobQuery.findObjects(new FindListener<Data>() {
            @Override
            public void done(List<Data> list, BmobException e) {
                //这里的这个list应该是正确的 ，size为1才是正确的
                Log.i("000", list.size() + "这是list的size");
                if (list.size() != 0) {
                    String objectid = list.get(0).getObjectId();
                    Data data = new Data();
                    data.setObjectId(objectid);
                    data.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                callBack.success("SUCCESS", null, null);
                            } else {
                                Log.i("000", e.toString());
                                callBack.error("ERROE");
                            }
                        }
                    });
                }
            }
        });

    }

    //网点查询
    public void networl_query(final NetWorkQuery_CallBack callBack, String address) {
        String url = "http://route.showapi.com/64-18?showapi_appid=47084&siteName=" + address +
                "&showapi_sign=d6be03fcaaac4165b608421214d1561f";
        Log.i("000", url + "url?????????>>>>>>>>>>util中的");
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.getList(null);     //出错传空回去
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                Net_Root net_root = gson.fromJson(s, Net_Root.class);
                List<Net_Data> list = net_root.getBody().getList();
//                Log.i("000",list.size()+"这是query中的");
                callBack.getList(list);
            }
        });
    }


}
