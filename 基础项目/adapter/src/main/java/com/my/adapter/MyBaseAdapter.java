package com.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

/**
 * Created by 123456 on 2017/9/10.
 */

public class MyBaseAdapter extends BaseAdapter {

    private List<Map<String, Object>> mdata;
    private LayoutInflater mInflater;
    private Context mcontext;

    public MyBaseAdapter(List<Map<String, Object>> mdata, Context mcontext) {
        this.mdata = mdata;
        mInflater = LayoutInflater.from(mcontext);
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return mdata.size();
    }

    @Override
    public Object getItem(int i) {
        return mdata.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null; //vieholder 性能优化
        if (view == null) {             //固定写法
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.baseadapter, null);
            viewHolder.imageView = view.findViewById(R.id.Image_item_base);
            viewHolder.textView = view.findViewById(R.id.Text_item_base);
            viewHolder.button = view.findViewById(R.id.but_item_base);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageResource((Integer) mdata.get(i).get("img"));
        viewHolder.textView.setText((String) mdata.get(i).get("title"));
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mcontext, "点击了我", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    class ViewHolder {
        ImageView imageView;
        TextView textView;
        Button button;
    }

}
