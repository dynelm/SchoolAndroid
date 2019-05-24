package com.dynelm.robotdarttest.Fragement;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dynelm.robotdarttest.MainActivity;
import com.dynelm.robotdarttest.R;
import com.jilk.ros.rosbridge.ROSBridgeClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2018/8/24 0024.
 */

public class PorlloingFragement extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.partrollfra, container, false);
        WebView MyWEbView = view.findViewById(R.id.wb);
        String gifFilePath = "file:///android_asset/talk.gif";//首先将一张gif格式的动图放置在assets中

        MyWEbView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        String data = "<html lang=\"en\">\n" +
                " <head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>Document</title>\n" +
                "  <style>\n" +
                "    body{padding:0;margin:0;}\n" +
                "    #bg{width:100%;height:100%;position:absolute;z-index:-1;}\n" +
                "    #login{width:300px;height:200px;background:#fff;position:absolute;left:50%;top:50%;margin-left:-150px;margin-top:-100px}\n" +
                "  </style>\n" +
                " </head>\n" +
                " <body>\n" +
                "  <img id=\"bg\" src=\"file:///android_asset/talk.gif\"/>\n" +

                " </body>\n" +
                "</html>";//设置图片全屏显示

        MyWEbView.loadDataWithBaseURL(gifFilePath, data, "text/html", "utf-8", null);


        return view;
    }




}
