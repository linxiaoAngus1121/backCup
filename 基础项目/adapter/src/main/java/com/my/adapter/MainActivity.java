package com.my.adapter;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*第一，第二种需要把extends listactivity 改成appcompactactivity ,
    并且要把 setContentView(R.layout.activity_main);
     mListView = (ListView) findViewById(R.id.list_view)
     mListView.setAdapter(myBaseAdapter);
        还原成未注释
         并把 setListAdapter(myBaseAdapter);删掉
     */
public class MainActivity extends ListActivity {

    private ListView mListView;
    List<Map<String, Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //      mListView = (ListView) findViewById(R.id.list_view);
        //1  arrrayadapter用法，3个参数，第一个this,第二个采用的样式，第三个数据源
      /*  String s[] = {"你好啊", "你好啊", "你好啊", "你好啊", "你好啊", "你好啊",
                "你好啊", "你好啊","你好啊","你好啊","你好啊","你好啊","你好啊","你好啊",
                "你好啊","你好啊","你好啊","你好啊","你好啊","你好啊","你好啊","你好啊"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,s);
        mListView.setAdapter(arrayAdapter);*/



/*      2.第二种，simpleadapter,  new SimpleAdapter(this, list, R.layout.simpleadapter_item,
        new String[]{"img", "title"}, new int[]{R.id.Image_item_simple, R.id.Text_item_simple});
        第一个参数 this，第二个 数据源(由list 和 map构成 list 负责listview 的一行 ，map负责一行里面的控件)， 第三个 布局文件 第四个 new String[] { 必须和map 里面 “img”,"title" 一致} ，第五个参数 布局文件中的控件*/
       /* List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("img", R.drawable.littlepear);
        map.put("title", "林浩啊");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.littlepear);
        map.put("title", "诺基亚");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.littlepear);
        map.put("title", "诺基亚");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.littlepear);
        map.put("title", "诺基亚");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.littlepear);
        map.put("title", "诺基亚");
        list.add(map);

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.simpleadapter_item,
                new String[]{"img", "title"}, new int[]{R.id.Image_item_simple, R.id.Text_item_simple});
        mListView.setAdapter(simpleAdapter);*/


        list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("img", R.drawable.littlepear);
        map.put("title", "林浩啊");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.littlepear);
        map.put("title", "诺基亚");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.littlepear);
        map.put("title", "诺基亚");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.littlepear);
        map.put("title", "诺基亚");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.littlepear);
        map.put("title", "诺基亚");
        list.add(map);

        MyBaseAdapter myBaseAdapter = new MyBaseAdapter(list, this);
        //  mListView.setAdapter(myBaseAdapter);
        setListAdapter(myBaseAdapter);
    }
/*
*
* baseadapter总结，
* 1.需继承至listactivity
* 2.数据来源类型跟simpleadapter差不多
* 3.要写一个类继承自baseadapter
* 4.看mybaseadapter
*
* */

}
