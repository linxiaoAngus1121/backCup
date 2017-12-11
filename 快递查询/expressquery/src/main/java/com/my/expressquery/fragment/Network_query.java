package com.my.expressquery.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.my.expressquery.HttpUtil.QueryUtil;
import com.my.expressquery.Json.Json_Network_Query.Net_Data;
import com.my.expressquery.MyInterFace.NetWorkQuery_CallBack;
import com.my.expressquery.R;
import com.my.expressquery.baiduMap.MyOrientationListener;

import java.util.ArrayList;
import java.util.List;

import static com.baidu.mapapi.map.MapStatusUpdateFactory.newLatLng;

/**
 * Created by 123456 on 2017/9/24.
 * <p>
 * 百度map中POI简称兴趣点（point of interst）
 * 主要流程：
 * 先从快递接口查询到快递网点的信息，然后进行地理位置编码（正向地理位置编码），再使用百度地图添加覆盖物
 */

public class Network_query extends Fragment implements View.OnClickListener, NetWorkQuery_CallBack {


    private EditText mEdit;
    private ImageView mSearch_Website;
    private TextView mAll;

    private BaiduMap map;
    //地理位置编码
    private GeoCoder geoCoder;

    private MapView mapView;
    private BitmapDescriptor mIconLocation;
    private MyOrientationListener listener;
    private float mCurrentX;

    private double myLongitude; // 经度
    private double myLatitude; // 纬度
    boolean isFirstLoc = true;// 是否首次定位
    private LocationClient client;

    private List<LatLng> latLngList = new ArrayList<>();

    private Marker marker;

    private Animation animation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.thrid_fragment, container, false);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);
        animation = new AlphaAnimation(0.1f, 1.0f);  //从看不见到看见
        animation.setDuration(4000);
        layout.setAnimation(animation);
        mEdit = (EditText) view.findViewById(R.id.id_maker_ly);
        mSearch_Website = (ImageView) view.findViewById(R.id.search_Website);
        mapView = (MapView) view.findViewById(R.id.baiduMap);
        mAll = (TextView) view.findViewById(R.id.all);
        mSearch_Website.setOnClickListener(this);
        map = mapView.getMap();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(15.0f);

        map.setMapStatus(mapStatusUpdate);          //设置缩放的比列
        client = new LocationClient(getContext());
        client.registerLocationListener(new myListener());

        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");   //设置坐标类型
        option.setOpenGps(true);        //打开GPS
        option.setScanSpan(1000);          //设置多少秒请求一次
        option.setIsNeedAddress(true);      //返回位置信息
        client.setLocOption(option);

        // 初始化图标
        mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
        listener = new MyOrientationListener(getActivity().getApplicationContext());        //设置手机传感器支持的类
        listener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
            }
        });

        geoCoder = GeoCoder.newInstance();

        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = marker.getExtraInfo();
                Net_Data net_data = (Net_Data) bundle.getSerializable("info");
                TextView tv = new TextView(getContext());
                tv.setBackgroundColor(Color.parseColor("#cc4e5a6b"));
                tv.setGravity(Gravity.CENTER);
                tv.setText(net_data.getExpName());
                InfoWindow infoWindow = new InfoWindow(tv, net_data.getLatLng(), -130);
                map.showInfoWindow(infoWindow);

                return true;
            }
        });
        Log.i("000", "onCreateView333333");
        return view;


    }


    @Override
    public void onClick(View v) {
        QueryUtil util = new QueryUtil();
        util.networl_query(this, mEdit.getText().toString());
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    //接受在工具类中解析完成的list(回调接口)
    @Override
    public void getList(List<Net_Data> strings) {
        if (strings == null) {
            Looper.prepare();
            Toast.makeText(getContext(), "查询出错咯！！请更换关键字", Toast.LENGTH_SHORT).show();
            Looper.loop();
            return;
        }

        geoCoder.setOnGetGeoCodeResultListener(new MyOngetGeoCodeResultLIstener(strings));


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                map.clear();
            }
        });
        if (latLngList.size() > 0) {
            latLngList.clear();
        }
        for (Net_Data cur : strings) {
            Log.i("000", cur.getAddr());
            geoCoder.geocode(new GeoCodeOption().address(cur.getAddr()).city("广州"));        //地理编码
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        map.setMyLocationEnabled(true);     //开启定位图层
        if (!client.isStarted()) {
            client.start();
        }
        listener.start();
        animation.startNow();
        Log.i("000", "onStart333333");
    }


    //地理位置回调
    class myListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            myLatitude = bdLocation.getLatitude();   //纬度
            myLongitude = bdLocation.getLongitude();  //经度
            if (bdLocation == null || mapView == null) {      // mapView bdLocation 销毁后不在处理新接收的位置
                return;
            }
            //这个是为了让自己显示在地图上（有个小点）
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .direction(mCurrentX)                //方向
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();

            if (data != null && map != null) {      //当data不为空，并且map还在的时候
                map.setMyLocationData(data);        //为map设置数据
                /**
                 * 参数说明
                 * mode - 定位图层显示方式, 默认为 LocationMode.NORMAL 普通态, Compass罗盘模式 FOLLOWING跟随模式
                 enableDirection - 是否允许显示方向信息
                 customMarker - 设置用户自定义定位图标，可以为 null
                 *
                 */
                MyLocationConfiguration configuration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mIconLocation);
                map.setMyLocationConfiguration(configuration);
            }
            if (isFirstLoc) {       //第一次进入
                   isFirstLoc = false;
                LatLng lng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());   //用这个类设置经纬度
                MapStatusUpdate mapStatusUpdate = newLatLng(lng);    //设置经度和纬度
                map.animateMapStatus(mapStatusUpdate);      //采用动画效果过去
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        Log.i("000", "onResume333333");
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        Log.i("000", "onPause333333");
    }

    @Override
    public void onStop() {
        super.onStop();
        map.setMyLocationEnabled(false);     //关闭定位图层
        client.stop();
        listener.stop();
        Log.i("000", "onStop333333");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (map != null) {
            map = null;
        }
        mapView.onDestroy();
        geoCoder.destroy();
        Log.i("000", "onDestroyView333333");
    }

    class MyOngetGeoCodeResultLIstener implements OnGetGeoCoderResultListener {

        private List<Net_Data> strings;

        public MyOngetGeoCodeResultLIstener(List<Net_Data> strings) {

            this.strings = strings;
        }


        //地址转坐标
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(getContext(), "地址转换出错", Toast.LENGTH_SHORT).show();
            } else {
                LatLng latLng = geoCodeResult.getLocation();
                latLngList.add(latLng);
                OverlayOptions option = new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.maker));
                Log.i("000", latLngList.size() + "????>>>>>????");
                marker = (Marker) map.addOverlay(option);
                Bundle bundle = new Bundle();
                strings.get(latLngList.size() - 1).setLatLng(latLng);
                bundle.putSerializable("info", strings.get(latLngList.size() - 1));
                marker.setExtraInfo(bundle);//把数据传给map.setonmarkerlistener
            }
            Log.i("000", latLngList.size() + "这个sise");
            if (latLngList.size() == 1) {    //第一个数就跳进去
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(latLngList.get(0), 12.5f);    //设置经度和纬度,缩放比列调整（不会）
                map.animateMapStatus(mapStatusUpdate);      //采用动画效果过去
            }
        }

        //坐标转地址
        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

        }
    }
}