package com.my.expressquery.fragment.updatePsFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.my.expressquery.R;

/**
 * Created by 123456 on 2018/1/7.
 */

public class Phone_modeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.update_ps_phone, container, false);
    }


    @Override
    public void onStart() {
        Log.i("000", "这是Phone_onStart");
        super.onStart();

    }

    @Override
    public void onResume() {
        Log.i("000", "这是Phone_onResume");
        super.onResume();

    }

    @Override
    public void onPause() {
        Log.i("000", "这是Phone_onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("000", "这是Phone_onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i("000", "这是Phone_onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("000", "这是Phone_onDestroy");
        super.onDestroy();
    }
}
