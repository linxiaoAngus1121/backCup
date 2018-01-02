package com.my.expressquery.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.my.expressquery.util.QueryUtils;
import com.my.expressquery.MyInterFace.CallBack;
import com.my.expressquery.MyInterFace.CallBckGetData;
import com.my.expressquery.R;
import com.my.expressquery.adapter.AdapterForMyPress;
import com.my.expressquery.db.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123456 on 2017/9/24.
 */

public class my_press extends Fragment implements CallBack, SwipeRefreshLayout.OnRefreshListener, CallBckGetData, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ListView mListView;
    private List<Data> mList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AdapterForMyPress adpter;
    private QueryUtils queryUtils;
    private PopupWindow popupWindow;
    private View finalConvertView;
    private View inflate;
    private LayoutInflater layoutInflater;
    private ProgressDialog dialog;

    public my_press() {
        queryUtils = new QueryUtils();
        mList = new ArrayList<Data>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment, container, false);
        mListView = (ListView) view.findViewById(R.id.list_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_purple);
        layoutInflater = LayoutInflater.from(getContext());
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        Log.i("000", "执行了onCreateView+++22222");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        queryUtils.queryDataForMy_prsss(my_press.this);
    }


    @Override
    public void onStart() {
        super.onStart();

        dialog = new ProgressDialog(getContext());
        Log.i("000", "执行了onstart+++222");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("000", "执行了onPause+++222");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("000", "执行了onStop+++222");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("000", "执行了onResume+++222");
    }

    @Override
    public void onRefresh() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                //这个时候应该再次请求网络，刷新数据
                queryUtils.queryDataForMy_prsss(my_press.this);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    //重写接口中的方法(用户之前查过的快递记录)
    @Override
    public void getdata(List<Data> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        if (adpter == null) {
            adpter = new AdapterForMyPress(mList, getContext());   //通过adapter显示数据
            mListView.setAdapter(adpter);
        } else {
            adpter.notifyDataSetChanged();      //调用notifyDataSetChanged()的时候，要保证一定要在同一个数据源
        }
        if (list.size() > 0) {       //返回的list有数据
            Log.i("000", list.size() + "list_isex");
        } else {        //返回的lsit没有数据
            Toast.makeText(getContext(), "还有没任何快递记录哦", Toast.LENGTH_SHORT).show();
        }

    }


    //短按实现查询快递
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        queryUtils.queryNoName(my_press.this, mList.get(position).getOdd(), mList.get(position).getCode());    //无名称方式查询快递
        Log.i("000", "这是listview的onitemclick");
        dialog.show();
    }

    //长按实现删除
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.i("000", "这是listview的onItemLongClick");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("确定删除");
        builder.setIcon(R.drawable.gantanhao);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String odd = mList.get(position).getOdd();
                Log.i("000", mList.get(position).getOdd() + "这是要删除的快递odd");
                queryUtils.deleteData(my_press.this, odd);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
        return true;
    }

    //请求快递数据成功，然后弹出popuwindow显示快递详情
    @Override
    public void success(String strings, String ex_name, String code) {
        if (strings.equals("SUCCESS")) {       //证明是删除数据的
            Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
            //   adpter.notifyDataSetChanged();
            queryUtils.queryDataForMy_prsss(my_press.this);
        } else {            //这里是查询快递的
            Log.i("000", strings + "success方法中的");
            Looper.prepare();
            finalConvertView = layoutInflater.inflate(R.layout.spinner_item, null);     //popowindow要显示的内容
            inflate = layoutInflater.inflate(R.layout.activity_main, null);  //把他放在这个view的左上
            TextView textView = (TextView) finalConvertView.findViewById(R.id.tv);
            textView.setText(strings);
            popupWindow = new PopupWindow(finalConvertView,
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT, true);      //popowindow初始化
            popupWindow.setBackgroundDrawable(new BitmapDrawable());    //一定要加上这句，不然无法通过返回键关闭popuwindow
            popupWindow.setOutsideTouchable(true);                  //设置是否可以点击外面，让popuwindow消失
            popupWindow.setAnimationStyle(R.style.anim);            //设置popuwindow开始和结束的动画
            dialog.cancel();
            popupWindow.showAtLocation(inflate, Gravity.START | Gravity.TOP, 0, 0);  //显示popuwindow
            Looper.loop();
        }
    }

    @Override
    public void error(String strings) {
        Looper.prepare();
        dialog.cancel();
        Toast.makeText(getContext(), "发生未知错误，请重试", Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("000", "执行了onDestroyView+++22222");
    }

}
