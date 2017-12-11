package com.my.expressquery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.expressquery.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123456 on 2017/9/26.
 * 这个是重写autocomplete的
 */

public class MyBaseAdapter extends BaseAdapter implements Filterable {
    List<String> list;
    private LayoutInflater mInflater;
    private ArrayFilter mArrayFilter = null;
    private ArrayList<String> mUnfilteredData;

    public MyBaseAdapter(List<String> mlist, Context context) {
        list = mlist;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder; //vieholder 性能优化
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
        viewHolder.mTextView.setText(list.get(i));
        return view;
    }


    class ViewHolder {
        ImageView mImageView;
        TextView mTextView;
    }


    @Override
    public Filter getFilter() {
        if (mArrayFilter == null) {
            mArrayFilter = new ArrayFilter();
        }
        return mArrayFilter;
    }

    //自定义过滤规则
    class ArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<String>(list);
            }

            if (constraint == null || constraint.length() == 0) {
                ArrayList<String> list = mUnfilteredData;
                filterResults.values = list;
                filterResults.count = list.size();
            } else {
                ArrayList<String> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();
                ArrayList<String> newValues = new ArrayList<String>(count);
                String str = constraint.toString().toLowerCase(); //转化成小写进行比较
                for (int i = 0; i < count; i++) {
                    if (list.get(i).toLowerCase().contains(str)) {
                        newValues.add(list.get(i));
                    }
                }
                filterResults.values = newValues;
                filterResults.count = newValues.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
//            设置回数据源
            list = (List<String>) results.values;

           /* notifyDataSetChanged：粮仓中得粮食少了，或者多了，发送通知。
            notifyDataSetInvalidated：粮仓变换了，比如原来从A仓取粮食，现在换成了B粮仓
            设有粮仓A，我们需要把A中得粮食，显示出来。
然后，某天运输队，运来了一批粮食，这批粮食有两种方式存储。
1.把粮食放到A中，也就是我们说得List的Add方法。
2.新建一个粮仓B，把粮食放入B粮仓中。就是代码中，new List —>add.

就这两种情况分析一下，为什么不起作用。

第一种，如果使用notifyDataSetInvalidated，是不会起作用的，因为数据源没有变化，就是粮仓还在。这个时候应该使用notifyDataSetChanged。粮仓中得粮食变了。

第二种，这是最复杂的，大部分不起作用都发生在这种情况下。

分析：notifyDataSetChanged不起作用的原因，是因为A粮仓中粮食没有发生变化。Adapter中仍然保持是对A粮仓的地址引用。

notifyDataSetInvalidated不起作用的原因，是因为，Adapter中得变量仍然是保持对A的引用，没有改变引用地址。

解决方法：第一种情况，基本不会出问题。
主要是第二种：如果数据对象，是重新new的对象，我们需要把对象的引用赋值给要显示的List，然后通知Adapter数据源发生了变化。比如重新setList(),并使用notifyDataSetInvalidated。
            */
            if (results.count > 0) {        //notifyDataSetChanged 用于数据源改变了
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated(); //改变新的引用地址
            }
        }
    }
}
