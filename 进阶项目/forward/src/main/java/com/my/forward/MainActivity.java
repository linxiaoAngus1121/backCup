package com.my.forward;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.my.forward.adapter.RecycleAdapter;
import com.my.forward.adapter.RecycleClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyleView;
    private List<String> list = new ArrayList<>();
    private List<Integer> im_src = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyleView = (RecyclerView) findViewById(R.id.recyle);
     //上面那个轮播图的适配器
        RollPagerView mRollPagerView = (RollPagerView) findViewById(R.id.roll);
        mRollPagerView.setAdapter(new MyAdapter());

        list.add("四六级冲刺指南|这份拿走就够了1");
        list.add("四六级冲刺指南|这份拿走就够了2");
        list.add("四六级冲刺指南|这份拿走就够了3");
        list.add("四六级冲刺指南|这份拿走就够了4");
        list.add("四六级冲刺指南|这份拿走就够了5");
        list.add("四六级冲刺指南|这份拿走就够了6");
        list.add("四六级冲刺指南|这份拿走就够了7");
        list.add("四六级冲刺指南|这份拿走就够了8");

        im_src.add(android.R.mipmap.sym_def_app_icon);
        im_src.add(R.drawable.shoe);
        im_src.add(R.drawable.animal02);
        im_src.add(R.drawable.shentong);
        im_src.add(android.R.mipmap.sym_def_app_icon);
        im_src.add(R.drawable.shoe);
        im_src.add(R.drawable.animal02);
        im_src.add(R.drawable.shentong);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager
                .VERTICAL, false);
        mRecyleView.setLayoutManager(manager);
        mRecyleView.setAdapter(new RecycleAdapter(this, list, im_src));
        mRecyleView.addOnItemTouchListener(new RecycleClickListener(this, mRecyleView, new
                RecycleClickListener.ItemClickListener() {

                    @Override
                    public void onItemClick(View v, int postion) {
                        Toast.makeText(MainActivity.this, "点击了" + list.get(postion), Toast
                                .LENGTH_SHORT).show();
                    }
                }));
    }

    /**
     * 成绩查询
     */
    public void score_query(View view) {

    }

    public void goforwd(View view) {
        Intent intent = new Intent(MainActivity.this, Express_Query.class);
        startActivity(intent);
    }

    private static class MyAdapter extends StaticPagerAdapter

    {

        int[] src = {R.drawable.animal02, R.drawable.circle, R.drawable.huojian, R.drawable
                .shentong};

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setImageResource(src[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;

        }

        @Override
        public int getCount() {
            return src.length;
        }
    }
}
