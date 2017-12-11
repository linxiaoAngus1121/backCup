package com.my.expressquery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my.expressquery.R;
import com.my.expressquery.db.Data;

import java.util.List;

/**
 * Created by 123456 on 2017/10/30.
 * 我的快递listview的适配器
 */

public class AdapterForMyPress extends BaseAdapter {
    private List<Data> mlist;
    LayoutInflater layoutInflater;
    ViewHolder holder;



    public AdapterForMyPress(List<Data> list, Context context) {
        mlist = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {      //性能优化
            convertView = layoutInflater.inflate(R.layout.my_press_item_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextView_odd.setText(mlist.get(position).getOdd());
        holder.mTextView_name.setText(mlist.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        TextView tv_info, mTextView_name, mTextView_odd;
        RelativeLayout ll_hide;
        public ViewHolder(View itemView) {
            ll_hide = (RelativeLayout) itemView.findViewById(R.id.ll_hide);
            mTextView_name = (TextView) itemView.findViewById(R.id.tv_my_press_name);
            mTextView_odd = (TextView) itemView.findViewById(R.id.tv_my_press_odd);
            tv_info = (TextView) itemView.findViewById(R.id.tv);
        }
    }

}