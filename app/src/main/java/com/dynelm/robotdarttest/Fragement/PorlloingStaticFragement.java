package com.dynelm.robotdarttest.Fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dynelm.robotdarttest.R;

/**
 * Created by Administrator on 2018/11/3 0003.
 */

public class PorlloingStaticFragement extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.showgif_layout, container, false);



        return view;
    }
}
