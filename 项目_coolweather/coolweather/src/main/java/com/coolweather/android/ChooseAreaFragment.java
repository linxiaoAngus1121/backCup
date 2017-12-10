package com.coolweather.android;

import android.annotation.TargetApi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 123456 on 2017/8/12.
 */

public class ChooseAreaFragment extends Fragment {
    public static  final int LEVEL_PROVINCE=0;
    public static  final int LEVEL_CITY=1;
    public static  final int LEVEL_COUNTY=2;
    private NavigationView design_navigation_view;
    private ProgressDialog dialog;
    private TextView titleTet;
    private Button backButton;
    private ListView listView;
    private TextView loginname;     //显示账号
    private TextView loginpass;         //显示密码
    private ArrayAdapter<String> adapter;
    private List<String> datalist=new ArrayList<>();
    //省列表
    private List<Province> provincesList;
    //市列表
    private  List<City> citiyList;
    //县列表
    private  List<County> countyList;
    //选中的省
    private Province selectProvince;
    //选中的市
    private City selectCity;
    //当前选中的级别
    private int currentLevel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        titleTet= view.findViewById(R.id.title_text);
        backButton= view.findViewById(R.id.back_button);
        listView= view.findViewById(R.id.list_view);
        design_navigation_view=view.findViewById(R.id.nav_View);
        loginname= view.findViewById(R.id.name_tv);
        loginpass= view.findViewById(R.id.name_pw);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,datalist);
        listView.setAdapter(adapter);
        return  view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentLevel==LEVEL_PROVINCE){
                    //把选中的省放到selectProvince 这里,然后查询城市
                    selectProvince=provincesList.get(i);
                    queryCities();
                    Log.i("000","查询省中");
                }else if(currentLevel==LEVEL_CITY){
                    selectCity=citiyList.get(i);
                    queryCounties();
                    Log.i("000","查询市中");
                }else if (currentLevel==LEVEL_COUNTY){
                    Log.i("000","查询县中");
                    String weather_id=countyList.get(i).getWeatherId();
                    Log.i("000","choosearea的weather_id"+weather_id);
                    //getactivity获得当前的activity，如果是在weatherActivity中，则关闭滑动菜单，并显示下拉刷新，加载新数据，在mainactivity则跳转到weatherActivity
                    //instanceof判断一个对象是否属于某个类的实例
                    if (getActivity() instanceof MainActivity){
                        //跳转到weatheractivity显示查询到的数据
                        Intent intent=new Intent(getActivity(),WeatherActivity.class);
                        intent.putExtra("weather_id",weather_id);
                        startActivity(intent);
                        getActivity().finish();
                    }else if(getActivity() instanceof WeatherActivity){

                        WeatherActivity weatherActivity = (WeatherActivity) getActivity();
                        weatherActivity.drawerLayout.closeDrawers();
                        weatherActivity.swipeRefreshLayout.setRefreshing(true);
                        weatherActivity.request(weather_id);
                    }
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentLevel==LEVEL_COUNTY){
                    queryCities();
                }else if(currentLevel==LEVEL_CITY){
                    queryprovince();
                }
            }
        });
        queryprovince();
    }

    private void queryprovince() {
        titleTet.setText("中国");
        backButton.setVisibility(View.GONE);
        provincesList = DataSupport.findAll(Province.class);
        if (provincesList.size()>0){
            //list view 的数据源是datalist
            datalist.clear();
            for (Province p :provincesList){
                datalist.add(p.getProvinceNane());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_PROVINCE;
        }else {
            String address="http://guolin.tech/api/china";
            //从服务器查询数据，传入地址还有当前的级别
            queryFromServer(address,"province");
        }
    }

    private void queryCities() {
        titleTet.setText(selectProvince.getProvinceNane());
        backButton.setVisibility(View.VISIBLE);
        design_navigation_view.removeAllViews();
        citiyList=DataSupport.where("provinceid=?",String.valueOf(selectProvince.getId())).find(City.class);
        if (citiyList.size()>0){
            datalist.clear();
            for (City city:citiyList){
                datalist.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_CITY;
        }else {
            int provinceCode=selectProvince.getProvinceCode();
            String address="http://guolin.tech/api/china/"+provinceCode;
            Log.i("000",address);
            queryFromServer(address,"city");
        }
    }

    private void queryCounties() {
        titleTet.setText(selectCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList= DataSupport.where("cityid=?",String.valueOf(selectCity.getId())).find(County.class);
        if (countyList.size()>0){
            datalist.clear();
            for (County county:countyList){
                datalist.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_COUNTY;
        }else {
            int provinceCode=selectProvince.getProvinceCode();
            int cityCode = selectCity.getCityCode();
            String address="http://guolin.tech/api/china/"+provinceCode
                    +"/"+cityCode;
            Log.i("000","provinceCode+cityCode"+address);
            queryFromServer(address,"county");
        }

    }
    //从服务器那里查询数据
    private void queryFromServer(String address, final String type) {
        showDiago();
        //调用工具类中的sendOkHttprequest,响应的数据就会返回到Call back()的接口
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            //失败了
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        closeDiago();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }


            //成功的
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responsetext = response.body().string();
                boolean result=false;
                if ("province".equals(type)){
                    //Utility.handleProvinceResponse 解析服务器返回的json数据，并写入数据库
                    result = Utility.handleProvinceResponse(responsetext);
                }else if("city".equals(type)){
                    result=Utility.handleCityResponese(responsetext,selectProvince.getId());
                }else if("county".equals(type)){
                    result=Utility.handleCountyResponse(responsetext,selectCity.getId());
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeDiago();
                            if ("province".equals(type)){
                                queryprovince();
                            }else if("city".equals(type)){
                                queryCities();
                            }else if("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });

    }

    private void showDiago() {
        if(dialog==null){
            dialog=new ProgressDialog(getActivity());
            dialog.setMessage("正在加载");
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.show();
    }

    private void closeDiago(){
        if (dialog!=null){
            dialog.dismiss();
        }
    }
}
