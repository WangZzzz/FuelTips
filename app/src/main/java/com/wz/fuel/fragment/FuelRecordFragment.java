package com.wz.fuel.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wz.fragment.WBaseFragment;
import com.wz.fuel.R;
import com.wz.util.WLog;

/**
 * A simple {@link Fragment} subclass.
 */
public class FuelRecordFragment extends WBaseFragment {

    private static final String TAG = FuelRecordFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WLog.d(TAG, "onCreate");
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuel_record, container, false);
        return view;
    }
}
