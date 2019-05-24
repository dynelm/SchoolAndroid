package com.dynelm.robotdarttest.Fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.dynelm.robotdarttest.MainActivity;
import com.dynelm.robotdarttest.R;

/**
 * Created by Administrator on 2018/8/27 0027.
 */

public class WebFragement extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.webviewfg, container, false);
        final MainActivity mainActivity = (MainActivity) getActivity();
        WebView webView = view.findViewById(R.id.web_web);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        webView.loadUrl("www.dartzn.com");
        ImageButton imageButton = view.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setChioceItem(0);
            }
        });




        return view;
    }
}
