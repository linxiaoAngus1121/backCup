package com.my.expressquery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.expressquery.R;

/**
 * Created by 123456 on 2017/9/26.
 * 这个是重写ListView的
 */

public class MyAdapter extends BaseAdapter {
    String[] mString;
    private LayoutInflater mInflater;

    public MyAdapter(String[] array, Context context) {
        mString = array;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mString.length;
    }

    @Override
    public Object getItem(int position) {
        return mString[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null; //vieholder 性能优化
        if (view == null) {             //固定写法
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.myadapter_listview, null);
            viewHolder.mImageView = (ImageView) view.findViewById(R.id.iv_my_custom);
            viewHolder.mTextView = (TextView) view.findViewById(R.id.tv_my_custom);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();

        }
        viewHolder.mImageView.setImageResource(R.drawable.shentong);
        viewHolder.mTextView.setText(mString[i]);
        return view;
    }


    class ViewHolder {
        ImageView mImageView;
        TextView mTextView;
    }
}
