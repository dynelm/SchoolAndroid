package com.dynelm.robotdarttest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dynelm.robotdarttest.entity.KqwSpeechCompound;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import java.util.Locale;

/**
 * Created by Dynelm on 2017/9/6 0006.
 */

public class WeclomeActivity extends Activity {
    private final long SPLASH_LENGTH = 13000;
    //TTS
    SpeechSynthesizer mSpeechSynthesizer;
    SynthesizerListener mSynthesizerListener;
    public static  boolean isonline = false;
    private RequestQueue requestQueue;
    InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int i) {

        }
    };
    Handler handler = new Handler();
    private WebView MyWEbView;
    private KqwSpeechCompound kqwSpeechCompound;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         /* 隐藏标题栏 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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

        /* 设定屏幕显示为横屏 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.welcome_layout);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5975e8a1");
        initSpeechSynthesizerParam();
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_SYSTEM );

        mAudioManager.setStreamVolume(8, 15, 0); // 设置为最大声音，可通过SeekBar更改音量大小
        kqwSpeechCompound = new KqwSpeechCompound(this);


                MyWEbView = (WebView) findViewById(R.id.webView);
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
                handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转

                    public void run() {
                        Intent intent = new Intent(WeclomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, SPLASH_LENGTH);//6秒后跳转至应用主界面MainActivity
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //execute the task
                        kqwSpeechCompound.speaking("你好，欢迎使用安徽达特智能服务机器人");
                    }
                }, 1000);






        //向服务器请求登陆
        requestQueue = Volley.newRequestQueue(WeclomeActivity.this);
        /** Volley Get方法
         *
         * */


//        String path = "http://192.168.2.147:8065/real/Register/UserLogin?name=45&password=45";
//        StringRequest stringRequest = new StringRequest(path,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("TAG", response);
//                        if (response.equals("yes")){
//                            isonline = true;
//                        }else {
//                            isonline = false;
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("TAG", error.getMessage(), error);
//            }
//        });
//        requestQueue.add(stringRequest);



    }

    private void initSpeechSynthesizerParam() {
        mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(this, mInitListener);
        mSpeechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoqi");
        mSpeechSynthesizer.setParameter(SpeechConstant.SPEED, "50");
        mSpeechSynthesizer.setParameter(SpeechConstant.VOLUME, "80");
        mSpeechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        mSynthesizerListener = new SynthesizerListener() {
            @Override
            public void onSpeakBegin() {

            }

            @Override
            public void onBufferProgress(int i, int i1, int i2, String s) {

            }

            @Override
            public void onSpeakPaused() {

            }

            @Override
            public void onSpeakResumed() {

            }

            @Override
            public void onSpeakProgress(int i, int i1, int i2) {

            }

            @Override
            public void onCompleted(SpeechError speechError) {


            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        };
    }

    public boolean isWifiConnect() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }
    private String getConnectWifiSsid(){
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getSSID();
    }

}
