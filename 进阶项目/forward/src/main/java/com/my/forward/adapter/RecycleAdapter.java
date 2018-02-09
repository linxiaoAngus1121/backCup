package com.my.forward.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.forward.R;

import java.util.List;

/**
 * Created by 123456 on 2018/1/21.
 * recycleView的适配器
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyHolder> {

    private Context mContext;
    private List<String> data;
    private List<Integer> im_data;

    public RecycleAdapter(Context context, List<String> data, List<Integer> im_data) {
        mContext = context;
        this.data = data;
        this.im_data = im_data;
    }

    //创建视图的时调用
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_item, parent, false);
        return new MyHolder(view);
    }

    //绑定数据到视图时调用
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.mContent.setText(data.get(position));
     //   holder.mtargetView.setText();
        holder.mImview.setImageResource(im_data.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        TextView mContent;
        ImageView mImview;
        TextView mtargetView;

        private MyHolder(View itemView) {
            super(itemView);
            mImview = itemView.findViewById(R.id.title_image);
            mtargetView=itemView.findViewById(R.id.target);
            mContent = itemView.findViewById(R.id.content);
        }
    }
}
