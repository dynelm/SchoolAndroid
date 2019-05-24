package com.dynelm.robotdarttest.Fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dynelm.robotdarttest.MainActivity;
import com.dynelm.robotdarttest.R;

/**
 * Created by Administrator on 2018/8/27 0027.
 */

public class QuestionFragement extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question, container, false);
        final MainActivity mainActivity = (MainActivity) getActivity();

        RelativeLayout relativeLayout = view.findViewById(R.id.Que_Rea);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.webView.setVisibility(View.VISIBLE);
                        mainActivity.btn_Close_Web.setVisibility(View.VISIBLE);
                        String url = "http://www.dartzn.com";
                        mainActivity.webView.loadUrl(url);
                        //mainActivity.webView.loadUrl("www.dartzn.com");
                    }
                });

            }
        });


        return view;
    }
}
