package com.my.expressquery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.my.expressquery.R;

/**
 * Created by 123456 on 2017/11/26.
 * 打赏作者的fragment
 */

public class DaShangFragment extends Fragment {
    private TextView tv_back;
    private TextView tv_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_da_shang, container, false);
        tv_back = (TextView) view.findViewById(R.id.back);
        tv_title = (TextView) view.findViewById(R.id.fragment_title);
        tv_title.setText(R.string.title_dashang);
        Log.i("000","DAshang___onCreateView");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();      //回退这个framgent
                
            }
        });
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("000","DAshang___onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("000","DAshang___onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("000","DAshang___onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("000","DAshang___onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("000","DAshang___onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("000","DAshang___onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("000","DAshang___onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("000","DAshang___onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("000","DAshang___onDetach");
    }
}
