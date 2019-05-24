package com.dynelm.robotdarttest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

/**
 * Created by Dynelm on 2017/8/23 0023.
 */

public class ShowGifActivity extends Activity {
    private WebView MyWEbView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* 设定屏幕显示为横屏 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        setContentView(R.layout.showgif_layout);
//        MyWEbView = findViewById(R.id.webView);
//        String gifFilePath = "file:///android_asset/talk.gif";//首先将一张gif格式的动图放置在assets中
//
//        MyWEbView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//
//        String data = "<html lang=\"en\">\n" +
//                " <head>\n" +
//                "  <meta charset=\"UTF-8\">\n" +
//                "  <title>Document</title>\n" +
//                "  <style>\n" +
//                "    body{padding:0;margin:0;}\n" +
//                "    #bg{width:100%;height:100%;position:absolute;z-index:-1;}\n" +
//                "    #login{width:300px;height:200px;background:#fff;position:absolute;left:50%;top:50%;margin-left:-150px;margin-top:-100px}\n" +
//                "  </style>\n" +
//                " </head>\n" +
//                " <body>\n" +
//                "  <img id=\"bg\" src=\"file:///android_asset/talk.gif\"/>\n" +
//
//                " </body>\n" +
//                "</html>";//设置图片全屏显示
//
//        MyWEbView.loadDataWithBaseURL(gifFilePath, data, "text/html", "utf-8", null);


    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:
                Log.d("dasdsa", "242");
                //startActivity(new Intent(ShowGifActivity.this,MainActivity.class));
                finish();
                break;
        }
        return super.onTouchEvent(event);
    }

//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d("dasdsa", "242");
//                //startActivity(new Intent(ShowGifActivity.this,MainActivity.class));
//                finish();
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
}
