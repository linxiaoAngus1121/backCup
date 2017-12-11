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
 * Created by 123456 on 2017/11/27.
 */

public class Contact_developFragment extends Fragment {

    private TextView tv_back;
    private TextView tv_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_develop, container, false);
        tv_back = (TextView) view.findViewById(R.id.back);
        tv_title = (TextView) view.findViewById(R.id.fragment_title);
        tv_title.setText(R.string.call);
        Log.i("000", "DAshang___onCreateView");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();      //回退这个framgent
            }
        });
        return view;
    }
}
