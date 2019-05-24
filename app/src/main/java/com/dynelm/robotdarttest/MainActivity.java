package com.dynelm.robotdarttest;



import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dynelm.robotdarttest.Fragement.CamFragement;
import com.dynelm.robotdarttest.Fragement.MainFragement;
import com.dynelm.robotdarttest.Fragement.MapFragement;
import com.dynelm.robotdarttest.Fragement.NavigationFragement;
import com.dynelm.robotdarttest.Fragement.PorlloingFragement;
import com.dynelm.robotdarttest.Fragement.PorlloingStaticFragement;
import com.dynelm.robotdarttest.Fragement.QuestionFragement;
import com.dynelm.robotdarttest.Fragement.VideoFragement;
import com.dynelm.robotdarttest.Fragement.WebFragement;
import com.dynelm.robotdarttest.Interface.Observe_Activity;
import com.dynelm.robotdarttest.Interface.Observe_Fragement;
import com.dynelm.robotdarttest.Util.BatteryView;
import com.dynelm.robotdarttest.Util.GetdishuiAnswer;
import com.dynelm.robotdarttest.Util.HanyuPinyinHelper;
import com.dynelm.robotdarttest.Util.TimeThread;
import com.dynelm.robotdarttest.Util.WaveformView;
import com.dynelm.robotdarttest.popwindow.SelectPopupWindow;
import com.jilk.ros.rosbridge.ROSBridgeClient;
import com.dynelm.robotdarttest.InstanceExe.instance;
import com.tx.printlib.UsbPrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.dynelm.robotdarttest.entity.City_Name;
import com.dynelm.robotdarttest.Util.fullScreen;


/**
 * Created by Dynelm on 2017/8/23 0023.
 */

public class MainActivity extends BasicActivity implements Observe_Activity,SelectPopupWindow.OnPopWindowClickListener{
    private static final String TAG = "MainActivity";
    boolean isok = true;

    public  ROSBridgeClient client;
    private ImageView setmaplayout;
    private ImageView navlayout;
    private ImageView videolayout;
    private ImageView zhuyelayout;
    private static boolean denglu = true;
    // 定义FragmentManager对象管理器
    private FragmentManager fragmentManager;
    private MainFragement mainFragement;
    private MapFragement mapFragement;
    private NavigationFragement navigationFragement;
    private CamFragement camFragement;
    private PorlloingFragement porlloingFragement;
    //private ShowResultFragement showResultFragement;
    private QuestionFragement questionFragement;
    private WebFragement webFragement;
    private PorlloingStaticFragement porlloingStaticFragement;
    private VideoFragement videoFragement;
    Bundle bundle = new Bundle();
    private Handler handler;
    private getPowerThread getPowerThread;
    BatteryView batteryView;
    private TextView Batterry_left ;
    private TextView WPM;
    private WaveformView waveformView;
//    private Handler Wavehandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what==222){
//                waveformView.setmWaveColor(Color.parseColor("#32CD32"));
//                waveformView.updateAmplitude((Float.parseFloat(msg.obj + "")));
//            }
//        }
//    };
    private ImageView Face_Layout;
    int iclick =0;


    //显示网页
    public WebView webView ;
    public Button btn_Close_Web;
    //观察者模式
    List<Observe_Fragement> observe_fragementList = new ArrayList<>();
    //密码输入下拉框
    private SelectPopupWindow menuWindow;
    FragmentTransaction fragmentTransaction;

    private RelativeLayout Go_shezhi;
    private RelativeLayout Go_back;
    final static int COUNTS = 5;// 点击次数
    final static long DURATION = 1000;// 规定有效时间
    long[] mHits = new long[COUNTS];

    public static volatile boolean NORMAL = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* 隐藏标题栏 */
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        this.getWindow().setAttributes(lp);
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
        setContentView(R.layout.newmainview);
        fragmentManager = getSupportFragmentManager();
        setChioceItem(0);
        //setChioceItem(0);
        client=super.client;
//        setmaplayout = (ImageView) findViewById(R.id.maplayout);
//        setmaplayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                setChioceItem(3);
//            }
//        });
        //near_initialization();

//        navlayout = (ImageView) findViewById(R.id.Img_Nav);
//        navlayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //MainActivity.this.finish();//销毁这个实例，重新载入
//               setChioceItem(2);
//            }
//        });




        //动态显示时间
        TextView what_time = (TextView) findViewById(R.id.Time_What);
        TimeThread timeThread = new TimeThread(what_time);
        timeThread.start();
//        batteryView= (BatteryView) findViewById(R.id.battery);
//        Batterry_left = (TextView) findViewById(R.id.BatteryLeft);
//        WPM = (TextView) findViewById(R.id.WhatPM);
//        waveformView = (WaveformView) findViewById(R.id.WaveFormView);
//        Button btn_ceshi = (Button) findViewById(R.id.btn_ceshhi);
//
//        btn_ceshi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //sendtoWeb(WhatPM);
//                 clickToStart();
//            }
//        });
       mUsbPrinter = new UsbPrinter(getApplicationContext());
         dev= getCorrectDevice();
//        ImageView img_print = (ImageView) findViewById(R.id.Img_Contact);
//        img_print.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final UsbDevice dev = getCorrectDevice();
//                if (dev != null && mUsbPrinter.open(dev)) {
//                    mUsbPrinter.init();
//                    mUsbPrinter.doFunction(Const.TX_FONT_ULINE, Const.TX_ON, 0);
//                    mUsbPrinter.outputStringLn("This is Font A with underline.");
//                    mUsbPrinter.doFunction(Const.TX_SEL_FONT, Const.TX_FONT_B, 0);
//                    mUsbPrinter.doFunction(Const.TX_FONT_ULINE, Const.TX_OFF, 0);
//                    mUsbPrinter.doFunction(Const.TX_FONT_BOLD, Const.TX_ON, 0);
//                    mUsbPrinter.outputStringLn("This is Font B with bold.");
//                    mUsbPrinter.resetFont();
//                    mUsbPrinter.doFunction(Const.TX_ALIGN, Const.TX_ALIGN_CENTER, 0);
//                    mUsbPrinter.outputStringLn("center");
//                    mUsbPrinter.doFunction(Const.TX_ALIGN, Const.TX_ALIGN_RIGHT, 0);
//                    mUsbPrinter.outputStringLn("right");
//                    mUsbPrinter.doFunction(Const.TX_ALIGN, Const.TX_ALIGN_LEFT, 0);
//                    mUsbPrinter.doFunction(Const.TX_FONT_ROTATE, Const.TX_ON, 0);
//                    mUsbPrinter.outputStringLn("left & rotating");
//                    mUsbPrinter.resetFont();
//                    mUsbPrinter.doFunction(Const.TX_CHINESE_MODE, Const.TX_ON, 0);
//                    mUsbPrinter.outputStringLn("中文");
//                    mUsbPrinter.doFunction(Const.TX_FONT_SIZE, Const.TX_SIZE_3X, Const.TX_SIZE_2X);
//                    mUsbPrinter.doFunction(Const.TX_UNIT_TYPE, Const.TX_UNIT_MM, 0);
//                    mUsbPrinter.doFunction(Const.TX_HOR_POS, 20, 0);
//                    mUsbPrinter.outputStringLn("安徽达特智能科技有限公司");
//                    mUsbPrinter.resetFont();
//                    mUsbPrinter.doFunction(Const.TX_FEED, 30, 0);
//                    mUsbPrinter.outputStringLn("feed 30mm");
//                    mUsbPrinter.doFunction(Const.TX_BARCODE_HEIGHT, 15, 0);
//                    mUsbPrinter.printBarcode(Const.TX_BAR_UPCA, "12345678901");
//                    //mUsbPrinter.printImage("/storage/sdcard0/a1.png");
//                    mUsbPrinter.printImage(getExternalFilesDir(null).getPath()+"/../../../../a1.png");
//                    mUsbPrinter.doFunction(Const.TX_UNIT_TYPE, Const.TX_UNIT_PIXEL, 0);
//                    mUsbPrinter.doFunction(Const.TX_FEED, 140, 0);
//                    mUsbPrinter.doFunction(Const.TX_CUT, Const.TX_CUT_FULL, 0);
//                    mUsbPrinter.close();
//                }
//            }
//        });

        //显示网页
        webView = (WebView) findViewById(R.id.n_wv);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        btn_Close_Web = (Button) findViewById(R.id.btn_closewv);
        btn_Close_Web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.setVisibility(View.INVISIBLE);
                btn_Close_Web.setVisibility(View.INVISIBLE);
            }
        });
        webView.setVisibility(View.INVISIBLE);
        btn_Close_Web.setVisibility(View.INVISIBLE);
        Go_shezhi = (RelativeLayout) findViewById(R.id.click_area);
        Go_back = (RelativeLayout) findViewById(R.id.back_area);
        Go_shezhi .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continuousClick(COUNTS,DURATION,false);
            }
        });
        Go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continuousClick(COUNTS,DURATION,true);
            }
        });
        ImageView Main_Nav = (ImageView) findViewById(R.id.Main_Nav);
        ImageView Main_Print = (ImageView) findViewById(R.id.Main_Print);
        ImageView Main_quest = (ImageView) findViewById(R.id.Main_Ask);
        Main_Nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NORMAL = true;
                setChioceItem(4);
            }
        });
        Main_Print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Print();
            }
        });
        Main_quest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Main_quest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChioceItem(7);
            }
        });




    }

    //下拉菜单实现就近点导航点初始化
    //选择初始化导航点的模态选择框
    // 单选提示框
    private AlertDialog alertDialog2;
    int indexdijige = 1;
    private void near_initialization(int i){
        final String[] items = {};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("请点击确定");
        indexdijige = i;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog2.dismiss();
            }
        },10000);
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                if (indexdijige == 1) {
                    TTS(GetdishuiAnswer.getdishuiAnswer.getAnswer("个人所得税")+"如需进一步了解，请去一号窗口");
//
                } else if (indexdijige == 2) {
                    TTS(GetdishuiAnswer.getdishuiAnswer.getAnswer("企业所得税")+"如需进一步了解，请去二号窗口");

                } else if (indexdijige == 3) {
                    TTS(GetdishuiAnswer.getdishuiAnswer.getAnswer("印花税")+"如需进一步了解，请去三号号窗口");

                } else if (indexdijige == 4) {
                    TTS(GetdishuiAnswer.getdishuiAnswer.getAnswer("车船税")+"如需进一步了解，请去四号窗口");

                } else if (indexdijige == 5) {
//
                }


                // 关闭提示框
                alertDialog2.dismiss();
            }
        });
        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {


                // 关闭提示框
                alertDialog2.dismiss();
            }
        });
        alertDialog2 = alertBuilder.create();
        alertDialog2.show();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    public void setChioceItem(int index){
        fragmentTransaction = fragmentManager.beginTransaction();
        clearChioce(); // 清空, 重置选项, 隐藏所有Fragment
        hideFragments(fragmentTransaction);

        switch (index){
            case 0:
                //MessageLayout.setBackgroundColor(gray);
                // 如果fg1为空，则创建一个并添加到界面上
                if (mainFragement == null) {
                    mainFragement = new MainFragement();
                    fragmentTransaction.add(R.id.Fragement_layout, mainFragement);
                } else {
// 如果不为空，则直接将它显示出来
                    fragmentTransaction.show(mainFragement);
                    mainFragement.onResume();
                }

                fragmentTransaction.commit();
                break;
            case 1:
                //MessageLayout.setBackgroundColor(gray);
                // 如果fg1为空，则创建一个并添加到界面上
                if (mapFragement == null) {
                    mapFragement = new MapFragement();
                    fragmentTransaction.add(R.id.Fragement_layout, mapFragement);
                } else {
// 如果不为空，则直接将它显示出来
                    fragmentTransaction.show(mapFragement);
                }

                fragmentTransaction.commit();
                break;
            case 2:
                NORMAL = false;
                inoutPsw();

                break;
            case 3:
                if (camFragement == null) {
                    camFragement = new CamFragement();
                    //videoFragement.leaveChannel();
                    fragmentTransaction.add(R.id.Fragement_layout, camFragement);
                } else {
                    //videoFragement.leaveChannel();
                      camFragement.onResume();

// 如果不为空，则直接将它显示出来
                    fragmentTransaction.show(camFragement);
                }

                fragmentTransaction.commit();
                break;
            case 4:
                if (navigationFragement == null) {
                    navigationFragement = new NavigationFragement();
                    fragmentTransaction.add(R.id.Fragement_layout, navigationFragement);

                } else {
                    fragmentTransaction.show(navigationFragement);
                    navigationFragement.do_hide(NORMAL);
                }

                fragmentTransaction.commit();
                break;
            case 5:
                if (videoFragement == null) {
                    videoFragement = new VideoFragement();
                    fragmentTransaction.add(R.id.Fragement_layout, videoFragement);
                } else {
// 如果不为空，则直接将它显示出来
                    videoFragement.onResume();
                    fragmentTransaction.show(videoFragement);
                }

                fragmentTransaction.commit();
                break;
            case 6:
                if (porlloingFragement==null){
                    porlloingFragement = new PorlloingFragement();
                    fragmentTransaction.add(R.id.show_pollart,porlloingFragement);
                }else {
                    fragmentTransaction.show(porlloingFragement);
                }
                fragmentTransaction.commit();
                break;
            case 7:
                if (questionFragement==null){
                    questionFragement=new QuestionFragement();
                    fragmentTransaction.add(R.id.Fragement_layout,questionFragement);
                }else {
                    fragmentTransaction.show(questionFragement);
                }
                fragmentTransaction.commit();
                break;
            case 8:
                if (webFragement==null){
                    webFragement=new WebFragement();
                    fragmentTransaction.add(R.id.Fragement_layout,webFragement);
                }else {
                    fragmentTransaction.show(webFragement);
                }
                fragmentTransaction.commit();
                break;
            case 9:
                if (porlloingStaticFragement==null){
                    porlloingStaticFragement=new PorlloingStaticFragement();
                    fragmentTransaction.add(R.id.show_pollart,porlloingStaticFragement);
                }else {
                    fragmentTransaction.show(porlloingStaticFragement);
                }
                fragmentTransaction.commit();
                break;
            default:
                break;


    }
    }

    private void hideFragments(android.support.v4.app.FragmentTransaction fragmentTransaction) {
        if (mainFragement != null){
            fragmentTransaction.hide(mainFragement);
        }if (mapFragement !=null){

            fragmentTransaction.hide(mapFragement);
        }if (navigationFragement!=null){

            fragmentTransaction.hide(navigationFragement);
        }if (camFragement!=null){
            camFragement.onStop();

            fragmentTransaction.hide(camFragement);
        }if (videoFragement!=null){
            fragmentTransaction.hide(videoFragement);
        }if (porlloingFragement!=null){
            fragmentTransaction.hide(porlloingFragement);
        }if (questionFragement!=null){
            fragmentTransaction.hide(questionFragement);
        }if (webFragement!=null){
            fragmentTransaction.hide(webFragement);
        }
        if (porlloingStaticFragement!=null){
            fragmentTransaction.hide(porlloingStaticFragement);
        }
    }

    private void clearChioce() {


    }
    public String getUrl(String a,String b){
      return HanyuPinyinHelper.toHanyuPinyin(a)+"-"+HanyuPinyinHelper.toHanyuPinyin(b);
    }

    String Gray_Result ="";
    List<String> City_List = new ArrayList<>();
    @Override

    public void getfinalresult() {
        final String sss = this.finalResult;
//         final boolean Gray = this.isFeasible;
//        if (!Gray){
//            Gray_Result = ".";
//        }else {
//            Gray_Result = "";
//        }
//        bundle.putString("finalRersult",sss);
        Message message = new Message();
        message.what=instance.Voice_Result;
        message.obj=sss+Gray_Result;
        handler.sendMessage(message);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (!Gray){
//                    addTextToList(sss,ME,Color.rgb(128,128,128));
//                }else {
//
//                    addTextToList(sss,ME,Color.WHITE);
//                }
//                //相当于更新ListView
//
//                adapter.notifyDataSetChanged();
//                chatListView.setSelection(chatList.size() - 1);
//            }
//        });
        if (sss.contains("火车票")||sss.contains("高铁票")||sss.contains("动车票")||sss.contains("车票")||sss.contains("票")){
            webView.setVisibility(View.VISIBLE);
            btn_Close_Web.setVisibility(View.VISIBLE);
            City_List.clear();
            for (int i=0;i<City_Name.city.length;i++){
                if (finalResult.contains(City_Name.city[i])){
                    City_List.add(City_Name.city[i]);
                }
            }
            try {
                String url = "http://trains.ctrip.com/TrainBooking/"+getUrl(City_List.get(0),City_List.get(1));
                webView.loadUrl(url);
            }catch (Exception e){
                Toast.makeText(this,"请说请正确地名",Toast.LENGTH_SHORT).show();
            }



        }
    }


    public void setHandler(Handler handler){
        this.handler = handler;
    }

    @Override
    public void getUnderstandresult() {
        String sss = this.getUnderstandcontent;
        Message message = new Message();
        message.what=instance.UnderStand_Result;
        message.obj=sss;
        handler.sendMessage(message);
    }
    Bitmap mbitmap;
    @Override
    public void getBitmap() {
        mbitmap = this.bitmap;
        set_bitmap(mbitmap);

    }
    List<Float> floatList = new ArrayList<Float>();
    @Override
    public void getCurrent() {
        float Current_X = this.Current_X;
        float Current_Y=this.Current_Y;
        float Current_Z = this.Current_Z;
        float Current_W = this.Current_W;
        floatList.clear();
        floatList.add(Current_X);
        floatList.add(Current_Y);
        floatList.add(Current_Z);
        floatList.add(Current_W);
        setCurrent(floatList);
    }
    String zhiling;
    @Override
    public void getxfRoswords() {
        zhiling = this.jsondata;
        setROS_Words(zhiling);
    }

    @Override
    public void getPollingState() {
        boolean pollingstate = instance.stopPalling;
        set_PollingState();
    }

    @Override
    public void getBatteryPower() {
        try {
            final int bpo = this.BatteryPower;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    batteryView.setPower(bpo*100/240);
                    Batterry_left.setText(bpo*100/240+"%");
                }

            });
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }catch (NullPointerException e){

        }

    }
    //观察者模式实现信息传递

    @Override
    public void addObserve(Observe_Fragement observe_fragement) {
        observe_fragementList.add(observe_fragement);
    }

    @Override
    public void notifyObserve_Current() {
        for (int i=0;i<observe_fragementList.size();i++){
            Observe_Fragement observe_fragement = observe_fragementList.get(i);
            observe_fragement.update(floatList);
        }

    }

    @Override
    public void notifyObserve_xfROSwords() {
        for (int i=0;i<observe_fragementList.size();i++){
            Observe_Fragement observe_fragement = observe_fragementList.get(i);
            observe_fragement .update_xfROSwords(zhiling);
        }
    }

    @Override
    public void notifyObserve_PollingState() {
        for (int i=0;i<observe_fragementList.size();i++){
            Observe_Fragement observe_fragement = observe_fragementList.get(i);
            observe_fragement .update_PollingState(false);
        }
    }

    @Override
    public void notifuObserve_Map() {
        for (int i=0;i<observe_fragementList.size();i++){
            Observe_Fragement observe_fragement = observe_fragementList.get(i);
            observe_fragement .update_Map(mbitmap);
        }

    }

    public void setCurrent(List<Float> floatList){
        this.floatList=floatList;
        notifyObserve_Current();
    }
    public void setROS_Words(String s){
        this.zhiling = s;
        notifyObserve_xfROSwords();
    }
    public void set_PollingState(){
        notifyObserve_PollingState();
    }
    public void set_bitmap(Bitmap bitmap){
        this.mbitmap = bitmap;
        notifuObserve_Map();
    }

    //初始化密码输入界面
    //打开输入密码的对话框
    public void inoutPsw(){
        menuWindow = new SelectPopupWindow(this, this);
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = getWindow().getDecorView().getHeight();
        menuWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, winHeight - rect.bottom);
    }
    //监听密码输入界面是否完成

    @Override
    public void onPopWindowClickListener(String psw, boolean complete) {
        if (complete){
            if (psw.equals("1234")){
                if (navigationFragement == null) {
                    navigationFragement = new NavigationFragement();
                    fragmentTransaction.add(R.id.Fragement_layout, navigationFragement);

                } else {
// 如果不为空，则直接将它显示出来
                    fragmentTransaction.show(navigationFragement);
                    navigationFragement.do_hide(NORMAL);
                    String msg_xfwords = "/move_base/result";
                    client.send("{\"op\":\"subscribe\",\"topic\":\"" + msg_xfwords + "\"}");
                }


                fragmentTransaction.commit();
            }else {
               Toast.makeText(MainActivity.this,"密码输入错误",Toast.LENGTH_SHORT).show();
            }
        }

    }

    class  getPowerThread extends Thread{
      @Override
      public void run() {
          super.run();
          getBatteryPower();
          try {
              Thread.sleep(10000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }

  }

    @Override
    public void getPmvalue() {

        final String s = this.WhatPM;
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   WPM.setText(s+"ug/m3");
                }
            });
        }
        catch (NullPointerException e){

        }

    }

    @Override
    public void getVolume() {
        int WaveForm = this.WaveFormVolue;
        Log.d("WaveForm", String.valueOf(WaveForm));
        updateVolume(WaveForm);

    }
    public void updateVolume(int volume) {
        Message message = Message.obtain();
        message.what = 222;
        message.obj = volume / 5;
        //Wavehandler.sendMessage(message);
    }

    //获得打印机硬件支持
    private UsbDevice getCorrectDevice() {
        final UsbManager usbMgr = (UsbManager)getSystemService(Context.USB_SERVICE);
        final Map<String, UsbDevice> devMap = usbMgr.getDeviceList();
        for(String name : devMap.keySet()) {
            Log.v("LOG_TAG", "check device: " + name);
            if (UsbPrinter.checkPrinter(devMap.get(name)))
                return devMap.get(name);
        }
        return null;
    }


    private void continuousClick(int count, long time,boolean b) {
        //每次点击时，数组向前移动一位
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //为数组最后一位赋值
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
            mHits = new long[COUNTS];//重新初始化数组
            if (b){
                setChioceItem(0);
            }else {
                setChioceItem(2);
            }
        }
    }

    @Override
    public void Do_Ros_Nav(int cmd) {
        super.Do_Ros_Nav(cmd);
        NORMAL = true;
        setChioceItem(4);
    }

    @Override
    public void allready() {
        //setChioceItem(0);



    }

    @Override
    public void getEndTimer() {
        //super.getEndTimer();
        //setChioceItem(0);


    }

    @Override
    public void getStartTimer() {


    }

    @Override
    public void main_lenovo(int i) {
        if (i==1){
            TTS("您想问关于个人所得税的相关问题么？");
            near_initialization(1);
        }
        if (i==2){
            TTS("您想问关于企业所得税的相关问题么？");
            near_initialization(2);
        }
        if (i==3){
            TTS("您想问关于印花的相关问题么？");
            near_initialization(3);
        }if (i==4){
            TTS("您想问关于车船的相关问题么？");
            near_initialization(4);
        }
    }

    @Override
    public void getporllingstaticfg() {
        setChioceItem(9);
    }

    @Override
    public void getmainfg() {
        setChioceItem(0);
    }

    @Override
    public void getsmile() {
        setChioceItem(6);
    }
}
