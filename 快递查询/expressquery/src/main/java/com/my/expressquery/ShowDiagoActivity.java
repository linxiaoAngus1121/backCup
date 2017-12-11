package com.my.expressquery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.my.expressquery.adapter.MyAdapter;
import com.my.expressquery.adapter.MyBaseAdapter;

import java.util.Arrays;
import java.util.List;

public class ShowDiagoActivity extends BaseActivity {

    private View view;
    private String[] items;
    private String[] code;
    private ListView mListView;
    private AutoCompleteTextView auto;
    private AlertDialog dialog;
    private Intent intent;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(this).inflate(R.layout.sertch, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("请选择快递公司")
                .setView(view);
        dialog = builder.create();
        dialog.show();
        init();


    }

    private void init() {
        items = getResources().getStringArray(R.array.ItemArray);
        code = getResources().getStringArray(R.array.all_code);
        mListView = (ListView) view.findViewById(R.id.listview);

        auto = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
        //只可读，但不可写
        list = Arrays.asList(items);

        //ListView的adapter
        MyAdapter myAdapter = new MyAdapter(items, this);
        mListView.setAdapter(myAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = items[position];
                intent = new Intent();
                intent.putExtra("item", item);               //快递名称
                intent.putExtra("code_id", code[position]);  //快递代号
                setResult(RESULT_OK, intent);
                dialog.dismiss();
                finish();
            }
        });
    }


    //找到快递名称对应的快递代号
    private int findPostion(String itemAtPosition) {
        // TODO: 2017/9/26 不知有没有改进的方法
        for (int i = 0; i < items.length; i++) {
            if (items[i].contains(itemAtPosition)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyBaseAdapter my = new MyBaseAdapter(list, this);
        auto.setAdapter(my);
        auto.setThreshold(1);   //输入一个字就能提示
        auto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mListView.setVisibility(View.GONE);
                if (auto.getText().length() == 0) {
                    mListView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //parent.getItemAtPosition(position);从数据源里去取出当前postion对应的item
                String itemAtPosition = (String) parent.getItemAtPosition(position);
                //从code[]中去取出当前选中的item对应的快递代码
                int pos_id = findPostion(itemAtPosition);
                intent = new Intent();
                intent.putExtra("item", itemAtPosition);     //快递名称
                intent.putExtra("code_id", code[pos_id]);    //快递代号
                setResult(RESULT_OK, intent);
                dialog.dismiss();
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        items = null;
        code = null;
        view = null;
    }
}
