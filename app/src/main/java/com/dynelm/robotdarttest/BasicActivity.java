package com.dynelm.robotdarttest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Toast;

import com.dynelm.robotdarttest.Event_listeners.Wake_Up_Listen;
import com.dynelm.robotdarttest.InstanceExe.Answer_Instance;
import com.dynelm.robotdarttest.Service.MyService;
import com.dynelm.robotdarttest.Util.FucUtil;
import com.dynelm.robotdarttest.Util.GetdishuiAnswer;
import com.dynelm.robotdarttest.Util.JsonParser;
import com.dynelm.robotdarttest.Util.PocketSphinxUtil;
import com.dynelm.robotdarttest.Util.WaveformView;
import com.dynelm.robotdarttest.Util.XmlParser;
import com.dynelm.robotdarttest.WebSocket.Client;
import com.dynelm.robotdarttest.entity.Feasible_result;
import com.dynelm.robotdarttest.entity.KqwSpeechCompound;
import com.dynelm.robotdarttest.entity.PublishEvent;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;
import com.iflytek.cloud.util.ResourceUtil;
import com.iflytek.inputmethod.asr.vad.VadEngine;
import com.iflytek.util.IflyRecorder;
import com.iflytek.util.IflyRecorderListener;
import com.jilk.ros.ROSClient;
import com.jilk.ros.rosbridge.ROSBridgeClient;

import org.java_websocket.drafts.Draft_17;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;
import edu.cmu.pocketsphinx.demo.RecognitionListener;
import com.dynelm.robotdarttest.InstanceExe.instance;
import com.dynelm.robotdarttest.entity.shuiwu_Keywords;
import com.tx.printlib.Const;
import com.tx.printlib.UsbPrinter;


/**
 * Created by Dynelm on 2017/8/23 0023.
 */

public class BasicActivity extends AppCompatActivity implements IflyRecorderListener {
    private List<String> UdpList = new ArrayList<String>();
    private static int Udplisisize=0;
    int sendtoWebtimes = 0;
    float Speed;
    float tSpeed;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
//			执行接收Message后的逻辑
                    String msg1 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way2x + ",\"y\":" + way2y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way2z + ",\"w\":" + way2w + "}}}}";
                    client.send(msg1);
                    break;
                case 2:
                    String msg2 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way3x + ",\"y\":" + way3y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way3z + ",\"w\":" + way3w + "}}}}";
                    client.send(msg2);

                    break;
                case 3:
                    String msg3 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way4x + ",\"y\":" + way4y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way4z + ",\"w\":" + way4w + "}}}}";
                    client.send(msg3);

                    break;
                case 4:
                    String msg4 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way5x + ",\"y\":" + way5y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way5z + ",\"w\":" + way5w + "}}}}";
                    client.send(msg4);

                    break;
                case 5:
                    String msg5 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way1x + ",\"y\":" + way1y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way1z + ",\"w\":" + way1w + "}}}}";
                    client.send(msg5);
                    kqwSpeechCompound.speaking("我已经巡逻结束了 正在回到"+Waypoint_name1);
                    if (alreadyPorlloingtimes > (Porlloing - 1)) {
                        //ispatrol = false;
                        timesenough = true;
                        alreadyPorlloingtimes = 1;

                        break;
                    }
                    alreadyPorlloingtimes++;

                    break;
                case 111:
                    String chulichuju = (String) msg.obj;

                    dosoundlocal(chulichuju);
                    break;
                case 555:
                    String UdpString = (String) msg.obj;
//                    UdpList.add(UdpString);
                    Toast.makeText(BasicActivity.this,UdpString,Toast.LENGTH_SHORT).show();
                    if (UdpString.contains("up")){
                        float  Speed = Float.parseFloat(getSpeed(UdpString));
                        liner_x_s = Speed;
                        liner_y_s = 0;
                        angular_z_s =0;
                        isOnLongClick = true;
                        moveThread = new AngleMoveThread();
                        moveThread.start();

                    }else if (UdpString.contains("down")){
                        float  Speed = Float.parseFloat(getSpeed(UdpString));
                        liner_x_s = (float) -Speed;
                        liner_y_s = 0;
                        angular_z_s =0;
                        isOnLongClick = true;
                        moveThread = new AngleMoveThread();
                        moveThread.start();

                    }else if (UdpString.contains("left")){
                        float  Speed = Float.parseFloat(getSpeed(UdpString));
                        liner_x_s = 0;
                        liner_y_s = 0;
                        angular_z_s =Speed;
                        isOnLongClick = true;
                        moveThread = new AngleMoveThread();
                        moveThread.start();

            }else if (UdpString.contains("right")){
                        float  Speed = Float.parseFloat(getSpeed(UdpString));
                        liner_x_s = 0;
                        liner_y_s = 0;
                        angular_z_s =-Speed;
                        isOnLongClick = true;
                        moveThread = new AngleMoveThread();
                        moveThread.start();


                    }else if (UdpString.contains("stop")){
                        liner_x_s = 0;
                        liner_y_s = 0;
                        angular_z_s =0;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (twosecondsactive){
                                    isOnLongClick=false;
                                }


                            }
                        },2000);

                    }
                    break;
                case 925:
                    //sendtoWeb((String) msg.obj);
                    //sendtoWebtimes++;
                    Log.d("sendtoWeb", String.valueOf(sendtoWebtimes));
                    break;
            }
        }
    };
    private long time = 10000;
    private static final String TAG = "BasicActivity";
    // 语音合成对象
    private static SpeechSynthesizer mTts;
    //连续语音识别
    public SpeechRecognizer mIat;
    private final int SAMPLE_RATE = 16000;
    /* 录音临时保存队列 */
    private ConcurrentLinkedQueue<byte[]> mRecordQueue = null;

    /* 端点检测引擎 */
    private VadEngine vadengine = null;

    /* 是否写入数据 */
    private boolean isRunning = true;

    /* 是否用户主动结束 */
    protected boolean isUserEnd = false;
    private Toast mToast = null;


    //讯飞处理语义理解
    TextUnderstander mTextUnderstander;
    TextUnderstanderListener mTextUnderstanderListener;
    //ROS 端连接和通信
    ROSBridgeClient client;
    private static boolean denglu = true;
    //机器人行走控制
    private MoveTimerTask moveTimerTask;
    Timer MoveTimer = new Timer();

    //利用Handler 回调函数来处理角度信息
    private Handler handlermove;
    private boolean isonegetangler = true;
    private static float chushihuadian;
    private boolean turnright;
    //声源定位的角度信息
    private static int moveangle;
    private static int absmoveangle;
    //声源定位
    protected AngleMoveThread moveThread ;
    public static boolean isOnLongClick = false;

    private static float liner_x_s;
    private static float liner_y_s;
    private static float angular_z_s;

    //注册网络状态的网络广播
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;

    //自定义监听器
    Wake_Up_Listen wake_up_listen = new Wake_Up_Listen();
    //串口是否读
    private boolean chuankou = false;

    public static boolean isMainActivity;
    // 向服务器连接 Websocket
    Client c = null;
    String websocketurl = "ws://192.168.1.147:8065/real/webSocketServer?Robotname=Robot2";
    InitListener initListener;
    private boolean isjiaqiangbishu ;
    //service 相关初始化
    MyService.Binder binder;
    MyService myService;
    String WhatPM = "27";
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            binder= (MyService.Binder) service;
            myService = binder.getService();
            myService.setCallback(new MyService.Callback() {
                @Override
                public void onDataChange(String data) {
                    Message msg = new Message();
                    msg.what=111;
                    msg.obj = data;
                    handler.sendMessage(msg);
                    stopSong();
            }

                @Override
                public void onPMDataChange(String data) {
                    getPmvalue();
                    WhatPM = data;
                    Message msg= new Message();
                    msg.what = 925;
                    msg.obj=data;
                    handler.sendMessage(msg);
                }
            });

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }

    };
    public static KqwSpeechCompound kqwSpeechCompound;
    private WaveformView mWaveFormView;
    //获取导航点名称和导航点数据;
    private float way1x;
    private float way1y;
    private float way1z;
    private float way1w;
    private float way2x;
    private float way2y;
    private float way2z;
    private float way2w;
    private float way3x;
    private float way3y;
    private float way3z;
    private float way3w;
    private float way4x;
    private float way4y;
    private float way4z;
    private float way4w;
    private float way5x;
    private float way5y;
    private float way5z;
    private float way5w;
    //巡逻点名称
    public String Waypoint_name1;
    public String Waypoint_name2;
    public String Waypoint_name3;
    public String Waypoint_name4;
    public String Waypoint_name5;
    SharedPreferences sp;
    UdpThread udpThread;
    //WebSocket 客户端
     Client finalC;
    private static boolean twosecondsactive;
    //科大讯飞离线语音识别
    private SpeechRecognizer mAsr;
    // 本地语法文件
    private String mLocalGrammar = null;
    // 本地语法构建路径
    private String grmPath = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/msc/test";
    private String mEngineType = SpeechConstant.TYPE_LOCAL;
    private String mResultType = "json";
    int ret = 0;// 函数调用返回值
    //定义标志位是否开启在线语音识别
    protected static  boolean workorchat =false;
    boolean isFeasible ;//判断结果是否在本地离线库
    boolean is_ShuiWu_KeyWords;
    //检测是否有人
    public static volatile boolean is_Body =false;
    //是否显示WebView;
    protected boolean WebView_Show = false;
    //控制TTS的时候不开启语音识别
    volatile boolean TTS_Listen = true;
    public static volatile boolean Robot_Sport = false;
    private volatile boolean Tts_Control_move = false;
    //连接热敏打印机
    public UsbPrinter mUsbPrinter;
    public UsbDevice dev;
    //打印机打印动画
    AlertDialog dialog;

    public  static volatile boolean timesenough = false;
    public  static volatile int alreadyPorlloingtimes = 1;
    public static volatile int Point_I = 100;
    //巡逻的圈数
    public static  volatile  int Porlloing;
    public  static volatile boolean ispatrol = false;
    public static volatile TimerTask timerTask = null;
    public static volatile Timer timer = null;
    private int key_word_state =0;
    int maxVolume = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5975e8a1");
        // 初始化录音机
        initRecorder();

        //讯飞语义理解 测试
        setTextUnderstandParam();
        //语音端点检测初始化
        mIat = SpeechRecognizer.createRecognizer(this, null);
        vadengine = VadEngine.getInstance();
        vadengine.reset();
        mRecordQueue = new ConcurrentLinkedQueue<byte[]>();
        setTextUnderstandParam();
        client = ((RCApplication) getApplication()).getRosClient();
        //获取ROS客户端实例
        EventBus.getDefault().register(this);//绑定推送消息监听
        Log.d("isuserend", String.valueOf(isUserEnd));

        if (denglu) {
            String IP = "192.168.0.5";
            //String IP = "192.168.1.106";
            String PORT = "9090";
            try {
                //connect(IP, PORT);

            } catch (Exception e) {
                e.printStackTrace();
            }
            denglu = false;
        }

        //获得 handler回调函数 来对返回的角度进行处理
        handlermove = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        String detailName = "/angle_imu_for_xf";
                        client.send("{\"op\":\"unsubscribe\",\"topic\":\"" + detailName + "\"}");
                        if (moveThread != null) {
                            liner_y_s=0;
                            liner_x_s=0;
                            angular_z_s=0;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    isOnLongClick = false;
                                }
                            },50);
                        }
                        myService.sendTxt("set 0\n");
                        if (is_Body){
                            if (!Robot_Sport){
                                clickToStart();
                            }

                        }

                        break;
                }
                return false;
            }
        });

        //广播
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");//为过滤器添加 广播过滤
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
        // 初始化合成对象
        initListener = new InitListener() {
            @Override
            public void onInit(int code) {
                if (code != ErrorCode.SUCCESS) {
                    Log.d("tag54", "初始化失败,错误码：" + code);
                }
                Log.d("tag54", "初始化失败,q错误码：" + code);
            }
        };
        mTts = SpeechSynthesizer.createSynthesizer(BasicActivity.this, initListener);

        isMainActivity = true;
        isjiaqiangbishu=false;
        Log.d("isjiaqiangbishu", String.valueOf(isjiaqiangbishu));
//        //获取ROS
        try {
            String Ros_Body_Flag = "/Dart_ROS_People_detect";
            client.send("{\"op\":\"subscribe\",\"topic\":\"" + Ros_Body_Flag + "\"}");

        }catch (Exception e){

        }


//        String Ros_POEWR = "/ROS_START_POWER";
//        client.send("{\"op\":\"subscribe\",\"topic\":\"" + Ros_POEWR + "\"}");

        //绑定Service
        bindService(new Intent(this, MyService.class), conn, BIND_AUTO_CREATE);
         kqwSpeechCompound = new KqwSpeechCompound(this);
         kqwSpeechCompound.setCallBackTTS(new KqwSpeechCompound.CallBackTTS() {
             @Override
             public void stopTTS() {
                 if (!is_Body){
                     TTS_Listen = true;
                 }
                 clickToCancel();

             }

             @Override
             public void startTTS() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickToStart();
                        TTS_Listen = true;
                    }
                },100);
             }

             @Override
             public void startsmileface() {
                 getporllingstaticfg();
             }

             @Override
             public void startspeakface() {
                 getsmile();
             }

             @Override
             public void startmianfg() {
                 getmainfg();
             }
         });

        //sp获得储存的数据
        sp = getSharedPreferences("ROBOT",
                Activity.MODE_PRIVATE);
        Waypoint_name1 = sp.getString("Waypointname1", "");
        Waypoint_name2 = sp.getString("Waypointname2", "");
        Waypoint_name3 = sp.getString("Waypointname3", "");
        Waypoint_name4 = sp.getString("Waypointname4", "");
        Waypoint_name5 = sp.getString("Waypointname5", "");
        way1x = sp.getFloat("way1x", 0);
        way1y = sp.getFloat("way1y", 0);
        way1z = sp.getFloat("way1z", 0);
        way1w = sp.getFloat("way1w", 0);
        way2x = sp.getFloat("way2x", 0);
        way2y = sp.getFloat("way2y", 0);
        way2z = sp.getFloat("way2z", 0);
        way2w = sp.getFloat("way2w", 0);
        way3x = sp.getFloat("way3x", 0);
        way3y = sp.getFloat("way3y", 0);
        way3z = sp.getFloat("way3z", 0);
        way3w = sp.getFloat("way3w", 0);
        way4x = sp.getFloat("way4x", 0);
        way4y = sp.getFloat("way4y", 0);
        way4z = sp.getFloat("way4z", 0);
        way4w = sp.getFloat("way4w", 0);
        way5x = sp.getFloat("way5x", 0);
        way5y = sp.getFloat("way5y", 0);
        way5z = sp.getFloat("way5z", 0);
        way5w = sp.getFloat("way5w", 0);
        //在MainActivity中初始化导航点
        //导航点初始化？
        String msg_startnav = "{\"op\":\"publish\",\"topic\":\"/ROS_START_NAV\",\"msg\":{\"data\":\"startnav\"}}";

        try {
            client.send(msg_startnav);


        }catch (Exception e){
         Log.d("weizhicuowu","未知错误");
        }

        udpThread=new UdpThread();
        udpThread.start();
        try {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myService.sendTxt("set 0\n");

                }
            },5000);
        }catch (Exception e){
            Log.d("weizhicuowu","未知错误");
        }
        //订阅语音消息
//        try {
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    String msg_xfwords = "/move_base/result";
//                    client.send("{\"op\":\"subscribe\",\"topic\":\"" + msg_xfwords + "\"}");
//
//                }
//            },10000);
//        }catch (Exception e){
//            Log.d("weizhicuowu","未知错误");
//        }

        //控制打印机动画
        dialog = new AlertDialog.Builder(this).setTitle("打印信息正在生成中，请稍等...").setView(R.layout.print_layout).create();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.mystyle);  //添加动画
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

         maxVolume = mAudioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);// 获取最大声音
        mAudioManager.setStreamVolume(8, 15, 0); // 设置为最大声音，可通过SeekBar更改音量大小


//        //科大讯飞离线命令词识别
//// 初始化识别对象
//        mAsr = SpeechRecognizer.createRecognizer(this, initListener);
//        mLocalGrammar = FucUtil.readFile(this,"call.bnf", "utf-8");

        //服务器接收
        //处理服务器返回数据
//        Handler Websockethandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                if (msg.what == 0x123) {
//                    Bundle data = msg.getData();
//                    String message = data.getString("message");
//                    DoWebMessage(message);
//                    Toast.makeText(BasicActivity.this, message, Toast.LENGTH_LONG).show();
//
//                }
//            }
//
//        };
//
//            try {
//                c = new Client(new URI("ws://192.168.2.147:8065/real/webSocketServer?Robotname=Robot2"), new Draft_17(), Websockethandler);
//
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//            finalC = c;
//            try {
//                if (finalC != null) {
//                    finalC.connectBlocking();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
    }
    //处理服务器返回的数据
//    private void DoWebMessage(String message) {
//        if (message.equals("forward")) {
//            finalResult = "前";
//            if (MoveTimer != null) {
//                if (moveTimerTask != null) {
//                    moveTimerTask.cancel();
//                }
//            }
//            moveTimerTask = new MoveTimerTask();
//            MoveTimer.schedule(moveTimerTask, 0, 20);
//
//        } else if (message.equals("back")) {
//            finalResult = "后";
//            if (MoveTimer != null) {
//                if (moveTimerTask != null) {
//                    moveTimerTask.cancel();
//                }
//            }
//            moveTimerTask = new MoveTimerTask();
//            MoveTimer.schedule(moveTimerTask, 0, 20);
//
//        } else if (message.equals("left")) {
//            finalResult = "左";
//            if (MoveTimer != null) {
//                if (moveTimerTask != null) {
//                    moveTimerTask.cancel();
//                }
//            }
//            moveTimerTask = new MoveTimerTask();
//            MoveTimer.schedule(moveTimerTask, 0, 20);
//
//        } else if (message.equals("right")) {
//            finalResult = "右";
//            if (MoveTimer != null) {
//                if (moveTimerTask != null) {
//                    moveTimerTask.cancel();
//                }
//            }
//            moveTimerTask = new MoveTimerTask();
//            MoveTimer.schedule(moveTimerTask, 0, 20);
//
//        } else if (message.equals("stop")) {
//            if (MoveTimer != null) {
//                if (moveTimerTask != null) {
//                    moveTimerTask.cancel();
//                }
//            }
//        }
//        return;
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
        PocketSphinxUtil.get(this).stop();
        if (!denglu) {
            client.disconnect();
        }
        unbindService(conn);
        android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
        System.exit(0);



    }
    //监听ROS端返回的信息
    public void onEvent(final PublishEvent event) {
        if ("/angle_imu_for_xf".equals(event.name)) {
            parsemove(event);
            return;
        }
        if ("/map".equals(event.name)) {
            parseMapTopic(event);
            return;
        }
        if ("/amcl_pose".equals(event.name)) {
            parseGetCurrent(event);
            return;
            }
        else if ("/move_base/result".equals(event.name)) {
            parseTTS(event);

            return;
        }
        else if ("/Dart_ROS_People_detect".equals(event.name)){
            ParseROS_Body(event);
            return;
        }else if ("/ROS_START_POWER".equals(event.name)){
            ParseROS_POWER(event);
            return;
        }
    }

    //语音连续识别
    // 初始化录音机
    private void initRecorder() {
        // 对三星手机的优化
        if (Build.MANUFACTURER.equalsIgnoreCase("samsung")) {
            IflyRecorder.getInstance().initRecoder(SAMPLE_RATE,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    MediaRecorder.AudioSource.VOICE_RECOGNITION);
        } else {
            IflyRecorder.getInstance().initRecoder(SAMPLE_RATE,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    MediaRecorder.AudioSource.MIC);
        }
    }

    protected void clickToStart() {
        mRecordQueue.clear();
        // 重置启动标志位
        setIsRunning(true);
        isUserEnd = false;
        // 开始录音
            //IflyRecorder.getInstance().startRecoder(this);
            setParam();
        mIat.startListening(listener);

        new Thread(myRunner).start();// 开启上传数据的线程
    }

    protected void clickToCancel() {
        isUserEnd = true;
        if (mIat != null)
            mIat.cancel();
        //IflyRecorder.getInstance().stopRecorder();
    }

    String finalresult1 = "";
    String finalResult;
    int notFeasible_Count = 0;
    int WaveFormVolue;
    private RecognizerListener listener = new RecognizerListener() {
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            String text = printResult(results);
            //String textPre = mEdit.getText().toString();
            if (!isLast) {
                finalresult1 = finalresult1 + text;
            }

            if (isLast) {
                finalResult = finalresult1;
                if (!finalResult.equals("")) {
                    isFeasible= Get_Feasible_Result(finalResult);
                    getfinalresult();
                    if (isFeasible) {
                        if (finalResult.contains("天气")){
                            mTextUnderstander.understandText(finalResult, mTextUnderstanderListener);
                        }
                        else if (finalResult.contains("名字")||finalResult.contains("是谁")){
                            String[] answer = {"我是安徽达特机器人","我的名字叫仔仔","仔仔就是我","我是你的机器人助手"};
                            TTS(answer[new Random().nextInt(4)]);
                        }
                        else if(finalResult.contains("你好")||finalResult.contains("您好")){
                            String[] answer = {"你也好，欢迎使用","你好，很高兴遇见你"};
                            TTS(answer[new Random().nextInt(2)]);
                            String Up_msg =  "{\"op\":\"publish\",\"topic\":\"/Dart_ROS_Head_Move\",\"msg\":{\"data\":\"h 1 14\"}}";
                            final String Down_msg = "{\"op\":\"publish\",\"topic\":\"/Dart_ROS_Head_Move\",\"msg\":{\"data\":\"h -1 10\"}}";
                            client.send(Up_msg);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    client.send(Down_msg);
                                }
                            },1500);
                        }

                        else if (finalResult.contains("前") || finalResult.contains("后") || finalResult.contains("左") || finalResult.contains("右")) {
                            if (finalResult.length()<5){
                                if (finalResult.contains("前")) {
                                    do_Robot_Move(1);
                                } else if (finalResult.contains("后")) {
                                    do_Robot_Move(2);
                                } else if (finalResult.contains("左")) {
                                    do_Robot_Move(3);
                                } else if (finalResult.contains("右")) {
                                    do_Robot_Move(4);
                                }
                            }

                        } else if (finalResult.contains("停")) {
                            TTS("停止了");
                            liner_x_s = 0;
                            liner_y_s = 0;
                            angular_z_s =0;
                            isOnLongClick = false;
                        } else if (finalResult.contains("歌")) {
                            if(finalResult.contains("刘德华")){
                                playsong(2);
                            }else if (finalResult.contains("张学友")){
                                playsong(3);
                            }else if (finalResult.contains("周杰伦")){
                                playsong(4);
                            }else {
                                playsong(new Random().nextInt(6));
                            }

                        } else if (finalResult.contains("带我去")||finalResult.contains("我想去")){
                            if (finalResult.contains(Waypoint_name1)||finalResult.contains(Waypoint_name2)||finalResult.contains(Waypoint_name3)
                                    ||finalResult.contains(Waypoint_name4)||finalResult.contains(Waypoint_name5)||finalResult.contains("1号窗口")
                                    ||finalResult.contains("2号窗口")||finalResult.contains("3号窗口")||finalResult.contains("4号窗口")||finalResult.contains("大厅服务中心")){
                                if (finalResult.contains(Waypoint_name1)||finalResult.contains("大厅服务中心")){
                                    Do_Ros_Nav(1);
                                }else if (finalResult.contains(Waypoint_name2)||finalResult.contains("1号窗口")){
                                    Do_Ros_Nav(2);
                                }else if (finalResult.contains(Waypoint_name3)||finalResult.contains("2号窗口")){
                                    Do_Ros_Nav(3);
                                }else if (finalResult.contains(Waypoint_name4)||finalResult.contains("3号窗口")){
                                    Do_Ros_Nav(4);
                                }else if (finalResult.contains(Waypoint_name5)||finalResult.contains("4号窗口")){
                                    Do_Ros_Nav(5);
                                }
                                String answer[] = {"好的，请跟我来","好啊，仔仔这就带您去哦","你要跟紧我哦","仔仔知道，在这边，请跟我来"};
                                Random random = new Random();
                                TTS(answer[random.nextInt(4)]);
                            }
                            else {
                                TTS("对不起，不包含该导航点，请你再说一遍");
                            }
                        }else if (finalResult.contains("介绍")){
                            TTS("你好呀，我是安徽达特智能机器人，我的名字叫仔仔，");
                        }else if (finalResult.contains("窗口")||finalResult.contains("大厅服务")){
                            if (finalResult.contains("1号窗口")||finalResult.contains("一号窗口")){
                                Do_Ros_Nav(2);
                            }else if (finalResult.contains("2号窗口")||finalResult.contains("二号窗口")){
                                Do_Ros_Nav(3);
                            }else if (finalResult.contains("3号窗口")||finalResult.contains("三号窗口")){
                                Do_Ros_Nav(4);
                            }else if (finalResult.contains("4号窗口")||finalResult.contains("四号窗口")){
                                Do_Ros_Nav(5);
                            }else if (finalResult.contains("大厅服务台")||finalResult.contains("大厅服务中心")){
                                Do_Ros_Nav(1);
                            }
                            String answer[] = {"好的，请跟我来","好啊，仔仔这就带您去哦","你要跟紧我哦","仔仔知道，在这边，请跟我来"};
                            Random random = new Random();
                            TTS(answer[random.nextInt(4)]);

                        }else if (Get_Shuiwu_KeyWord(finalResult)){
                            TTS(GetdishuiAnswer.getInstance().getAnswer(finalResult));
                        }
                        else if (finalResult.contains("取号")||finalResult.contains("取个号")||finalResult.contains("排队")){
                            Print();

                        }
                        else {
                            mTextUnderstander.understandText(finalResult, mTextUnderstanderListener);

                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                notFeasible_Count = 0;
                            }
                        }, 3000);
                    } else {
                         if (lenovo(finalResult)){
                             main_lenovo(key_word_state);
                         }else {
                             mTextUnderstander.understandText(finalResult, mTextUnderstanderListener);
                             notFeasible_Count = 0;
                         }


                    }
//                    setParam();
//                    mIat.startListening(listener);
//                    setIsRunning(true);
                    finalresult1 = "";
                    Log.d("finalResult", finalResult);


                } else {
                    clickToStart();
                }
            }
        }

        @Override
        public void onEndOfSpeech() {
            Log.d(TAG, "onEndOfSpeech");
        }



        @Override
        public void onBeginOfSpeech() {
            Log.d(TAG, "onBeginOfSpeech");
        }

        @Override
        public void onError(SpeechError error) {
            if (error.getErrorCode() == 10118) {
                //报错10118 检测到未说话，从新开始一次
                setParam();
                mIat.startListening(listener);
                setIsRunning(true);
            }else if (error.getErrorCode()==20002){
                setParam();
                mIat.startListening(listener);
                setIsRunning(true);
            }else {
                clickToCancel();
                //mToast.setText(error.toString());
                //mToast.show();
            }
        }


        @Override
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {

        }

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
            WaveFormVolue =i;
            getVolume();
        }


    };
    int volume;
    private Runnable myRunner = new Runnable() {
        public void run() {
            while (!isUserEnd) {// 条件是否用户主动结束

                if (getIsRunning()) {
                    byte[] data = mRecordQueue.poll();
                    if (data == null) {
                        Log.d(TAG, "no---no data");
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    volume = vadengine.vadCheck(data, data.length);
                    if (volume >= 0) {
                        Log.d(TAG, "no----volume");
                        mIat.writeAudio(data, 0, data.length);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // 检测到端点
                    else {
                        Log.d(TAG, "no----checked");
                        // 重置引擎、停止监听等待返回结果
                        vadengine.reset();
                        mIat.stopListening();
                        setIsRunning(false);
                    }
                } else {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    };


    /**
     * 返回当前会话状态
     *
     * @return
     */
    protected synchronized boolean getIsRunning() {

        return isRunning;
    }

    /**
     * 设置当前会话状态
     *
     * @return
     */
    protected synchronized void setIsRunning(boolean trueOrfalse) {
        isRunning = trueOrfalse;
    }

    @Override
    public void OnReceiveBytes(byte[] data, int length) {
        // record data
        if (length > 0) {
            byte[] temp = new byte[length];
            System.arraycopy(data, 0, temp, 0, length);
            if (data == null || data.length == 0)
                return;
            // 不断的填充数据
//			Log.d(TAG, "get----data");
            mRecordQueue.add(temp);
        }
    }

    /**
     * 参数设置
     *
     * @return
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "2000");
        // 关闭sdk内部录音，使用writeAudio接口传入音频
        //mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "800");
        mIat.setParameter("domain", "fariat");
        mIat.setParameter("aue", "speex-wb;10");
        // 设置音频保存路径，保存音频格式仅为pcm，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
//        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/test"+System.currentTimeMillis()/1000+".pcm");
    }


    String getUnderstandcontent;

    private void setTextUnderstandParam() {
        mTextUnderstanderListener = new TextUnderstanderListener() {
            @Override
            public void onResult(UnderstanderResult result) {
                if (null != result) {
                    Log.d(TAG, "语义分析结果为" + result.getResultString());
                    // 显示
                    String text = result.getResultString();
                    if (!TextUtils.isEmpty(text)) {


                        try {
                            JSONObject jsonObject = new JSONObject(result.getResultString());
                            JSONObject answerobject = jsonObject.getJSONObject("answer");
                            String content = answerobject.getString("text");

//                                mHistory.add(content);
//                                mSentimentRecyclerAdapter.notifyDataSetChanged();
                            getUnderstandcontent = content;
                            if (jsonObject.toString().contains("dynasty")){
                                String pattern = "([-+*/^()\\]\\[])";
                                getUnderstandcontent = getUnderstandcontent.replaceAll(pattern,"");
                                getUnderstandcontent = getUnderstandcontent.replaceAll("[k3]","");
                                getUnderstandcontent = getUnderstandcontent.replaceAll("[k0]","");
                            }

                            getUnderstandresult();
                            String contentresult = content.replaceAll("\\p{Punct}", "");
                            TTS(getUnderstandcontent);
                            Log.d("understandcontent", getUnderstandcontent + contentresult);
                        } catch (JSONException e) {
                            //TTS("仔仔没有搜索到相应的答案，请你再说一遍。");
                            clickToStart();
                        }

                    }
                } else {
                    TTS("语义识别失败！");
//                    mHistory.add("语义识别失败");
//                    mSentimentRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(SpeechError speechError) {
                //TTS("语义识别失败！");
            }
        };
        mTextUnderstander = TextUnderstander.createTextUnderstander(this, initListener);
    }

    public String printResult(RecognizerResult results) {

        String text = parseIatResult(results.getResultString());
        return text;
    }

    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

    private void connect(String ip, String port) {
        client = new ROSBridgeClient("ws://" + ip + ":" + port);
        boolean conneSucc = client.connect(new ROSClient.ConnectionStatusListener() {
            @Override
            public void onConnect() {
                client.setDebug(true);
                ((RCApplication) getApplication()).setRosClient(client);
                showTip("Connect ROS success");
                Log.d(TAG, "Connect ROS success");
                //startActivity(new Intent(MainActivity.this,MainActivity.class));
            }

            @Override
            public void onDisconnect(boolean normal, String reason, int code) {
                showTip("ROS disconnect");
                Log.d(TAG, "ROS disconnect");
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
                showTip("ROS communication error");
                Log.d(TAG, "ROS communication error");
            }
        });
    }

    private void showTip(final String tip) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BasicActivity.this, tip, Toast.LENGTH_SHORT).show();
            }
        });
    }


//语音命令实现机器人的控制行走

    static final int DIR_UP = 0;
    static final int DIR_UP_RIGHT = 1;
    static final int DIR_LEFT = 2;
    static final int DIR_UP_LEFT = 3;
    static final int DIR_DOWN = 4;
    static final int DIR_DOWN_RIGHT = 5;
    static final int DIR_RIGHT = 6;
    static final int DIR_DOWN_LEFT = 7;
    static final int DIR_STOP = 8;

    static final String[] DirCtrlVals = new String[]{"up", "up_right", "left", "up_left", "down", "down_right",
            "right", "down_left", "stop"
    };

    private List<Integer> cmdHistory = new ArrayList<>();//命令历史记录

    void Move(final int direction) {


        cmdHistory.add(direction);
        //可以重复执行
        Querucmd(DirCtrlVals[direction]);
    }
    float liner_x = 0;
    float liner_y = 0;
    float angular_z = 0;
    void Querucmd(String direction) {

        //final float x = Float.parseFloat(Liner_X.getText().toString());
        //float z = Float.parseFloat(Angular_Z.getText().toString());
        if (direction.equals("up")) {
            //TTS("前面的人走开，仔仔来了哈");
            liner_x = (float) 0.3;
            liner_y = 0;
            angular_z = 0;

        } else if (direction.equals("down")) {
            //TTS("我正在往后面走哈");
            liner_x = (float) -0.3;
            liner_y = 0;
            angular_z = 0;


        } else if (direction.equals("left")) {
            //TTS("我正在向左边自由旋转哈");
            liner_x = 0;
            liner_y = 0;
            angular_z = (float) 1.0;

        } else if (direction.equals("right")) {
            //TTS("我正在向右边自由旋转哈");
            liner_x = 0;
            liner_y = 0;
            angular_z = (float) -1.0;
        } else if (direction.equals("stop")) {
            liner_x = 0;
            liner_y = 0;
            angular_z = 0;
        } else if (direction.equals("up_right")) {
            liner_x = (float) 0.3;
            liner_y = (float) 1.0;
            angular_z = (float) -0.1;
        } else if (direction.equals("up_left")) {
            liner_x = (float) 0.3;
            liner_y = (float) 1.0;
            angular_z = (float) 0.1;
        } else if (direction.equals("down_right")) {
            liner_x = (float) -0.3;
            liner_y = (float) 1.0;
            angular_z = (float) -0.1;
        } else if (direction.equals("down_left")) {
            liner_x = (float) -0.3;
            liner_y = (float) 1.0;
            angular_z = (float) 0.1;
        }

        String msg = "";
        msg = "{\"op\":\"publish\",\"topic\":\"/cmd_vel\",\"msg\":{\"linear\":{\"x\":" + liner_x + ",\"y\":" + liner_y + ",\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":" + angular_z + "}}}";
        //Log.i(TAG, String.valueOf(liner_x +liner_y +angular_z));
        client.send(msg);


    }


    class MoveTimerTask extends TimerTask {

        @Override
        public void run() {
            if (finalResult.contains("前")) {
                Move(DIR_UP);
            }
            if (finalResult.contains("后")) {
                Move(DIR_DOWN);
            }
            if (finalResult.contains("左")) {
                Move(DIR_LEFT);
            }
            if (finalResult.contains("右")) {
                Move(DIR_RIGHT);
            }

        }
    }



    private void dosoundlocal(String string) {
        String hexresult = hexStringToString(string);
        if (hexresult != null) {
            if (hexresult.contains("up")){
                try {
                    moveangle = Integer.parseInt(returnNumber(hexresult));
                    chuankou = true;
                    if (ispatrol){
                        ispatrol=false;
                        Robot_Sport=false;
                        String msg = "{\"op\":\"publish\",\"topic\":\"/move_base/cancel\",\"msg\":{\"stamp\":0,\"id\":\"\"}}";
                        client.send(msg);
                        TTS("巡逻已经取消");
                    }
                } catch (NumberFormatException e) {
                    Log.d("angleerrol", "没有角度信息输出");
                    moveangle=0;
                }
                kqwSpeechCompound.stopSpeaking();
                if (MoveTimer != null) {
                    if (moveTimerTask != null) {
                        moveTimerTask.cancel();
                    }
                }
                Rotation_Angle(moveangle);
                Log.d("moveangle", String.valueOf(moveangle));

            }else {
                Log.d("donothing","donothing");
            }




            System.out.println(moveangle);
            System.out.println(hexresult);
        } else {
            //Log.d("angleerrol", "没有角度信息输出");
        }


    }

    // 转化十六进制编码为字符串
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "gbk");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    //从字符串中提取数字
    private String returnNumber(String string) {
        string = string.trim();
        String str2 = "";
        if (string != null && !"".equals(string)) {
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) >= 48 && string.charAt(i) <= 57) {
                    str2 += string.charAt(i);
                }
            }

        }
        return str2;

    }

    //机器人转动角度动作
    protected void Rotation_Angle(int angle) {
        //订阅ROS关于角度消息？？
        String detailName = "/angle_imu_for_xf";
        client.send("{\"op\":\"subscribe\",\"topic\":\"" + detailName + "\"}");


        chuankou = false;
        //想ROS发布转动信息
        if (angle < 360&&angle>180||angle==180) {
            liner_x_s = 0;
            liner_y_s = 0;
            angular_z_s = (float) 0.4;
            isOnLongClick = true;
            moveThread = new AngleMoveThread();
            moveThread.start();
            turnright = false;
            absmoveangle = Math.abs(angle - 360);

        } else if (angle > 0&&angle<180) {
            liner_x_s = 0;
            liner_y_s = 0;
            angular_z_s = (float) -0.4;
            isOnLongClick = true;
            moveThread = new AngleMoveThread();
            moveThread.start();
            turnright = true;
            absmoveangle = Math.abs(angle);

        } else if (angle == 360&&angle==0) {
            TTS("你就在仔仔前面呢");

        }



    }

    //机器人转动线程
    int iiiii = 0;
    class AngleMoveThread extends Thread {
        @Override
        public void run() {
            while (isOnLongClick) {
                try {
                    Thread.sleep(50);
                    String msg = "";
                    msg = "{\"op\":\"publish\",\"topic\":\"/cmd_vel\",\"msg\":{\"linear\":{\"x\":" + liner_x_s + ",\"y\":" + liner_y_s + ",\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":" + angular_z_s + "}}}";
                    //Log.i(TAG, String.valueOf(liner_x +liner_y +angular_z));
                    client.send(msg);
                    iiiii++;
                    Log.d("iiiii", String.valueOf(iiiii)+"x,y,z="+liner_x_s+liner_y_s+angular_z_s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                super.run();
            }
        }
    }

    ArrayList<String> arrayList = new ArrayList<String>();

    public void parsemove(PublishEvent event) {
        try {
            JSONParser parser = new JSONParser();
            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(event.msg);
            String jsondata = (String) jsonObject.get("data");
            arrayList.add(jsondata);
            chushihuadian = Float.parseFloat(arrayList.get(0));
            mTts.stopSpeaking();
            if (arrayList.size() > 30) {
                Message msg = new Message();
                msg.what = 1;
                handlermove.sendMessage(msg);
                arrayList.clear();
                return;
            }

            if (turnright) {

                Message msg = new Message();
                if (Math.abs(chushihuadian - absmoveangle) >= 180) {

                    if ((360 - Math.abs(Float.parseFloat(jsondata)) + chushihuadian) > absmoveangle) {
                        msg.what = 1;
                        handlermove.sendMessage(msg);
                        arrayList.clear();
                    }

                } else {
                    if ((chushihuadian - Float.parseFloat(jsondata)) > absmoveangle) {
                        msg.what = 1;
                        handlermove.sendMessage(msg);
                        arrayList.clear();
                    }
                }
            } else if (!turnright) {
                Message msg = new Message();
                if (chushihuadian + absmoveangle >= 180) {
                    if (360 - (chushihuadian - Float.parseFloat(jsondata)) < absmoveangle) {
                        msg.what = 1;
                        handlermove.sendMessage(msg);
                        arrayList.clear();
                    }

                } else {
                    if (Float.parseFloat(jsondata) - chushihuadian > absmoveangle) {
                        msg.what = 1;
                        handlermove.sendMessage(msg);
                        arrayList.clear();

                    }

                }
            }
            Log.d("jsondata", String.valueOf(chushihuadian));

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    boolean islixianorzaixian;

    //注册广播
    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
//                Toast.makeText(context, "网络打开",
//                        Toast.LENGTH_SHORT).show();
                islixianorzaixian = true;
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(BasicActivity.this);
                builder.setTitle("网络故障");
                builder.setMessage("您是否去设置界面打开无线网？");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
//                        System.exit(0);
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)); //直接进入手机中的wifi网络设置界面
                    }
                });
                builder.create().show();
                islixianorzaixian = false;
            }

        }
    }


    public void getfinalresult() {
    }

    public void getUnderstandresult() {
    }
    private boolean Get_Feasible_Result(String string) {
        long start_time = System.currentTimeMillis();
        for (int i = 0; i < Feasible_result.Feasible.length; i++) {
            if (string.contains(Feasible_result.Feasible[i])) {
                return true;
            }
        }
        long endTime = System.currentTimeMillis();
        long time = endTime - start_time;
        Log.d("timmmmmm", String.valueOf(time));
        return false;
    }
    private boolean Get_Shuiwu_KeyWord(String s){
        for (int i=0;i<shuiwu_Keywords.KeyWords_Shuiwu.length;i++){
            if (s.contains(shuiwu_Keywords.KeyWords_Shuiwu[i])){
                return true;
            }
        }
        return false;
    }

MediaPlayer mediaPlayer;
    AssetFileDescriptor fileDescriptor;
    String[] Songname = {"apple.mp3","tf.mp3","liu.mp3","zhang.mp3","zhou.mp3","xihuanni.mp3"};
    public void playsong(int i) {

        //Log.d("musicVolume", String.valueOf(mVolume));

        try {

            Random random = new Random();
            fileDescriptor = BasicActivity.this.getAssets().openFd(Songname[i]);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(8);
            AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            int mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); // 获取当前音乐音量
            int maxVolume = mAudioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC);// 获取最大声音
            mAudioManager.setStreamVolume(8, 15, 0); // 设置为最大声音，可通过SeekBar更改音量大小
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),

                    fileDescriptor.getStartOffset(),

                    fileDescriptor.getLength());

            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.release();
                    if (is_Body){
                        clickToStart();
                    }

                    Log.d("complete","songs completed");
                }
            });
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    clickToCancel();
                    Log.d("prepared","prepared");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void stopSong(){
        try {
            if(mediaPlayer!=null){
                mediaPlayer.stop();//停止播放
                mediaPlayer.release();//释放资源
                mediaPlayer=null;
            }
        }
        catch (Exception e){

        }
    }
//
//    public void startvideo(){
//        try {
//            fileDescriptor = BasicActivity.this.getAssets().openFd("example.mp4");
//            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
//                    fileDescriptor.getStartOffset(),
//                    fileDescriptor.getLength());
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 开始合成
     *
     * @param text
     */
    public void TTS(String text) {
        TTS_Listen=false;
      kqwSpeechCompound.speaking(text);

    }

    protected Bitmap bitmap;
    public void parseMapTopic(PublishEvent event) {
        try {
            JSONParser parser = new JSONParser();
            org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject) parser.parse(event.msg);
            org.json.simple.JSONArray dataArray = (org.json.simple.JSONArray) jsonObj.get("data");
            org.json.simple.JSONObject jsonInfo = (org.json.simple.JSONObject) jsonObj.get("info");
            int width = (int) (long) jsonInfo.get("width");
            int height = (int) (long) jsonInfo.get("height");


            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            int len = dataArray.size();
            int x, y, d, p;//底色黑色
            for (int i = 0; i < len; i++) {
                x = i % width;
                y = i / width;
                d = (int) (long) dataArray.get(i);
                if (d == -1) {
                    bitmap.setPixel(x, y, Color.rgb(0x59, 0x59, 0x59));
                } else {
                    p = (int) ((100 - d) / 100f * 255);
                    bitmap.setPixel(x, y, Color.rgb(p, p, p));
                }
            }
            getBitmap();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getBitmap(){

    }
    float Current_X;
    float Current_Y;
    float Current_Z;
    float Current_W;
    public void parseGetCurrent(PublishEvent event) {
        try {
            JSONParser parser = new JSONParser();
            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(event.msg);
            org.json.simple.JSONObject jsonheader = (org.json.simple.JSONObject) jsonObject.get("pose");
            org.json.simple.JSONObject jsonpose = (org.json.simple.JSONObject) jsonheader.get("pose");
            //org.json.simple.JSONObject jsonpose1 = (org.json.simple.JSONObject)jsonpose.get("pose");
            org.json.simple.JSONObject jsonposition = (org.json.simple.JSONObject) jsonpose.get("position");
            org.json.simple.JSONObject jsonorientation = (org.json.simple.JSONObject) jsonpose.get("orientation");
            double x = (double) jsonposition.get("x");
            double y = (double) jsonposition.get("y");
            double z = (double) jsonorientation.get("z");
            double w = (double) jsonorientation.get("w");

            Log.d("ceshi123", x + "              " + y + "z" + z + "w" + w);
            Current_X = (float) x;
            Current_Y = (float) y;
            Current_Z = (float) z;
            Current_W = (float) w;
            getCurrent();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void getCurrent(){

    }
    public void allready(){

    }
    String jsondata;
    volatile LinkedList<Integer> Robot_Com_back = new LinkedList<>();
    public void parseTTS(PublishEvent event) {
        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject jsonObject = null;
        try {
            jsonObject = (org.json.simple.JSONObject) parser.parse(event.msg);
            org.json.simple.JSONObject sta_object = (org.json.simple.JSONObject) jsonObject.get("status");
             jsondata= (String) sta_object.get("text");
            if (jsondata.equals("Goal reached.")){
                if (ispatrol) {
                    if (!timesenough) {
                        int ii = Point_I % 5;
                        //Log.d("iiiiii", String.valueOf(i));
                        if (ii == 1) {
                            kqwSpeechCompound.speaking("我已经到达"+Waypoint_name1+"正在去往" + Waypoint_name2);
                            Message message = new Message();
                            message.what = 1;
                            message.obj = "haha";
                            handler.sendMessage(message);

                        }
                        if (ii == 2) {
                            kqwSpeechCompound.speaking("我已经到达"+Waypoint_name2+"正在去往" + Waypoint_name3);
                            Message message = new Message();
                            message.what = 2;
                            message.obj = "haha";
                            handler.sendMessage(message);
                        }
                        if (ii == 3) {
                            kqwSpeechCompound.speaking("我已经到达"+Waypoint_name3+"正在去往" + Waypoint_name4);
                            Message message = new Message();
                            message.what = 3;
                            message.obj = "haha";
                            handler.sendMessage(message);
                        }
                        if (ii == 4) {
                            kqwSpeechCompound.speaking("我已经到达"+Waypoint_name4+"正在去往" + Waypoint_name5);
                            Message message = new Message();
                            message.what = 4;
                            message.obj = "haha";
                            handler.sendMessage(message);
                        }
                        if (ii == 0) {
                            //kqwSpeechCompound.speaking("我已经巡逻结束,谢谢");
                            Message message = new Message();
                            message.what = 5;
                            message.obj = "haha";
                            handler.sendMessage(message);
                        }

//               if (Point_I ==6){
//                    Message message=new Message();
//                    message.what=6;
//                    message.obj="haha";
//                    handler.sendMessage(message);
//                }
                        Log.d("Point_I", String.valueOf(Point_I));
                        Point_I++;
                    } else {
                        kqwSpeechCompound.speaking("本次巡逻已经结束，谢谢！");
                        ispatrol=false;
                        Robot_Sport=false;
                        allready();
                        //mainActivity.setChioceItem(2);
                    }
                }else {
                    if (!Robot_Com_back.isEmpty()){
                        TTS("我已经到达目的地");
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                TTS("我回去了，谢谢使用");
                                Do_Ros_Nav(1);
                                Robot_Com_back.clear();
                            }
                        },3000);

                    }else {
                        if (Tts_Control_move){
                            Robot_Sport=false;
                            Tts_Control_move =false;
                        }
                        TTS("我已经到达目的地");

                        allready();
                    }
                }


            } else {
                if (jsondata.equals("Failed to find a valid plan. Even after executing recovery behaviors.")) {
                    if (!ispatrol) {
                        kqwSpeechCompound.speaking("对不起，我不能到达该导航点，请下达另外的指令");
                       Robot_Sport=false;
                    } else if (ispatrol) {
                        if (!(Point_I % 5 == 0)) {
                            kqwSpeechCompound.speaking("我不能到达这个导航点,正在去往下一个导航点");
                            Point_I++;
                            Message message = new Message();
                            int t = Point_I % 5;
                            message.what = t;
                            message.obj = "hahah";
                            handler.sendMessage(message);
                        } else {
                            String msg1 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":0,\"y\":0,\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":0,\"w\":1}}}}";
                            client.send(msg1);

                        }
                    }
                }
                if (jsondata.equals("This goal was canceled because another goal was recieved by the simple action server")) {
                    if (!ispatrol) {
                        kqwSpeechCompound.speaking("对不起,导航点已经取消");
                    } else if (ispatrol) {
                        if (!(Point_I % 5 == 0)) {
                            kqwSpeechCompound.speaking("导航点已经取消,正在去往下一个导航点");
                            Point_I++;
                            Message message = new Message();
                            int t = Point_I % 5;
                            message.what = t;
                            message.obj = "hahah";
                            handler.sendMessage(message);
                        } else {
                            String msg1 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":0,\"y\":0,\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":0,\"w\":1}}}}";
                            client.send(msg1);

                        }
                    }
                } else {
                    kqwSpeechCompound.speaking("未知错误,GG");
                    Robot_Sport=false;
                }




            }
            //getxfRoswords();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    LinkedList<String> Body_list = new LinkedList<>();
    boolean isListen = false;
    //触摸屏点击事件监听


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (timer!=null){
                    timer.cancel();
                    timer=null;
                    getEndTimer();
                }if (timerTask!=null){
                timerTask.cancel();
                timerTask=null;
            }
            break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void getStartTimer(){}
    public void getEndTimer(){}
    public void ParseROS_Body(PublishEvent event){
        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject jsonObject = null;
        try {
            jsonObject = (org.json.simple.JSONObject) parser.parse(event.msg);
            String jsondataBody = (String) jsonObject.get("data");
            synchronized (Body_list){
                Body_list.offer(jsondataBody);


                if (Body_list.size()==6){
                    Body_list.poll();
                    if (Body_list.get(0).equals("false")&&Body_list.get(1).equals("false")&&Body_list.get(2).equals("false")&&Body_list.get(3).equals("false")&&Body_list.get(4).equals("false")){
                        if (isListen){
                            if (timer==null){
                                timer =new Timer();
                                if (timerTask==null){
                                    timerTask = new TimerTask() {
                                        @Override
                                        public void run() {
                                            getStartTimer();
                                        }
                                    };
                                }
                                timer.schedule(timerTask,15000);
                            }



                            if (is_Body){
                                clickToCancel();

                            }
                            is_Body=false;
                            isListen=false;
                        }





                    }
                    if (Body_list.get(0).equals("true")&&Body_list.get(1).equals("true")&&Body_list.get(2).equals("true")&&Body_list.get(3).equals("true")&&Body_list.get(4).equals("true")){
                        if (!is_Body){
                            if (!Robot_Sport){
                                is_Body=true;
                                String msg_head = "{\"op\":\"publish\",\"topic\":\"/head_control\",\"msg\":{\"data\":true}}";
                                client.send(msg_head);
                                TTS("你好");
                                if (timer!=null){
                                    timer.cancel();
                                    timer=null;
                                    getEndTimer();
                                }if (timerTask!=null){
                                    timerTask.cancel();
                                    timerTask=null;
                                }


                            }
                        }else {
                            is_Body=true;
                        }
                        isListen=true;

                    }
            }

            }
            Log.d("红外", String.valueOf(Body_list.size()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    int BatteryPower;
    public void ParseROS_POWER(PublishEvent event){
        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject jsonObject = null;
        try {
            jsonObject = (org.json.simple.JSONObject) parser.parse(event.msg);
            Long jsondataBody = (Long) jsonObject.get("data");
            BatteryPower = new Long(jsondataBody).intValue();
            Log.d("电量", String.valueOf(jsondataBody));
            getBatteryPower();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void getxfRoswords(){
    }
    public void getPollingState(){
    }
    public void getBatteryPower(){

    }
    public void getPmvalue(){

    }

    public void getVolume(){

    }

    public void updateVolume(int volume) {
        Message message = Message.obtain();
        message.what = 222;
        message.obj = volume / 5;
        handler.sendMessage(message);
    }

     List<Long> longs = new ArrayList<Long>();
    List<String> timeString = new CopyOnWriteArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Long diff;

    //内部类 就收远程数
    class UdpThread extends Thread{
        @Override
        public void run() {
            try {
                DatagramSocket socket = null;
                socket = new DatagramSocket(8888);
                while (true) {
                    byte data[] = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    socket.receive(packet);
                    long timeMillis = System.currentTimeMillis();
                    String time = stampToDate(timeMillis);
                    String result = new String(packet.getData(), packet.getOffset(), packet.getLength());
                    UdpList.add(result);
                    timeString.add(time);
                    if (UdpList.size()>2){

                        if (!(UdpList.get(UdpList.size()-1).contains("stop"))&&UdpList.get(UdpList.size()-2).contains("stop")){
                            if (timeString.size()>2){
                                try{
                                    Date d1 = format.parse(timeString.get(timeString.size()-1)); //当前时间
                                    Date d2 = format.parse(timeString.get(timeString.size()-2)); //之前记录的时间
                                    Log.d("t1t2",timeString.get(timeString.size()-1)+"+hahha"+timeString.get(timeString.size()-2));
                                    diff = d1.getTime() - d2.getTime(); //两时间差，精确到毫秒
                                }catch (Exception e){

                                }
                                //以天数为单位取整
                                Long day = diff / (1000 * 60 * 60 * 24);
                                //以小时为单位取整
                                Long hour=(diff/(60*60*1000)-day*24);
                                //以分钟为单位取整
                                Long min=((diff/(60*1000))-day*24*60-hour*60);

                                Long t=(diff/1000-day*24*60*60-hour*60*60-min*60);

                                Log.d("t1t2", String.valueOf(t));

                                if ((t)>2){
                                    twosecondsactive = true;
                                    //longs.clear();

                                }else {
                                    twosecondsactive=false;
                                }
                            }
                        }else {
                            twosecondsactive=true;
                        }
                    }else {
                        twosecondsactive=false;
                    }
                    Message message = new Message();
                    message.what=555;
                    message.obj=result;
                    handler.sendMessage(message);
                    Log.d("sdsdsd",result);

                    System.out.println("receive client's data: " + result);
                }

            }catch (Exception e){
                System.out.println("UDP Error");
            }


        }
        }
      //正则表达式
    private String getSpeed(String a){
        char[] b = a.toCharArray();
        String result = "";
        for (int i = 0; i < b.length; i++)
        {
            if (("0123456789.").indexOf(b[i] + "") != -1)
            {
                result += b[i];
            }
        }
        return result;
    }

    //向服务器发送数据
    public void sendtoWeb(String s){
        try {
            finalC.send(s);
        }catch (Exception e){
            Log.d("Websocket","请检查服务器");
            Toast.makeText(BasicActivity.this, "未连接服务器", Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * 将时间戳转换为时间
     */
    public String stampToDate(long timeMillis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    /**
     * 识别监听器。
     */
    static int lixiancishu =0;
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            //showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据："+data.length);
        }

        @Override
        public void onResult(final RecognizerResult result, boolean isLast) {
            if (null != result && !TextUtils.isEmpty(result.getResultString())) {
                Log.d(TAG, "recognizer result：" + result.getResultString());
                String text = "";
                if (mResultType.equals("json")) {
                    text = JsonParser.parseGrammarResult(result.getResultString(), mEngineType);
                } else if (mResultType.equals("xml")) {
                    text = XmlParser.parseNluResult(result.getResultString());
                }
                // 显示
               showTip(text);
                String regEx="[^0-9]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(text);
                int zhixingdu = Integer.parseInt(m.replaceAll("").trim());

                if (zhixingdu<25){
                    //TTS("您好，未能听清，请您重说一遍");
                    clickToStart();
                    finalResult ="您好，未能听清，请您重说一遍";
                    getfinalresult();
                }else {
                    finalResult=text;
                    getfinalresult();
                    lixiancishu=0;
                    if (text.contains("前")) {
                        do_Robot_Move(1);
                    } else if (text.contains("后")) {
                        do_Robot_Move(2);
                    } else if (text.contains("左")) {
                       do_Robot_Move(3);
                    } else if (text.contains("右")) {
                        do_Robot_Move(4);
                    }else if (text.contains("天气")){
                        String jieguo = text.substring(0,text.indexOf("【"));
                        mTextUnderstander.understandText(jieguo, mTextUnderstanderListener);
                    }
                    else if (text.contains("名字")||text.contains("介绍")){
                        String[] answer = {"我是安徽达特机器人","我的名字叫仔仔","仔仔就是我","我是你的机器人助手"};
                        TTS(answer[new Random().nextInt(4)]);

                    }
                    else if (text.contains("带我去")||text.contains("我想去")){
                        if (text.contains("大厅服务中心")){
                            Do_Ros_Nav(1);
                        }else if (text.contains("一号窗口")){
                            Do_Ros_Nav(2);
                        }else if (text.contains("二号窗口")){
                            Do_Ros_Nav(3);
                        }else if (text.contains("三号窗口")){
                           Do_Ros_Nav(4);
                        }else if (text.contains("四号窗口")){
                           Do_Ros_Nav(5);
                        }
                        String answer[] = {"好的，请跟我来","好啊，仔仔这就带您去哦","你要跟紧我哦","仔仔知道，在这边，请跟我来"};
                        Random random = new Random();
                        TTS(answer[random.nextInt(4)]);

                    }else if (text.contains("唱首")||text.contains("来首")||text.contains("来首歌")||text.contains("随便来一首歌")){
                        if(text.contains("刘德华")){
                            playsong(2);
                        }else if (text.contains("张学友")){
                            playsong(3);
                        }else if (text.contains("周杰伦")){
                            playsong(4);
                        }else {
                            playsong(new Random().nextInt(6));
                        }

                    }else if (text.contains("背首")){
                        String jieguo = text.substring(0,text.indexOf("【"));
                        mTextUnderstander.understandText(jieguo, mTextUnderstanderListener);
                    }else if (text.contains("你好")||text.contains("hello")){
                        TTS("你好，欢迎使用仔仔商用服务机器人");
                    }else {
                        Offline_Answer(text);
                    }
                }






            } else {
                Log.d(TAG, "recognizer result : null");
            }
//            if (!setParamlocal()) {
//                //showTip("请先构建语法。");
//                return;
//            };
//
//            ret = mAsr.startListening(mRecognizerListener);
//            setIsRunning(true);
        }

        @Override
        public void onEndOfSpeech() {
            //clickToStart();
        }

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            //showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            if (error.getErrorCode() == 20005){
                showTip("没有识别结果");
                if (lixiancishu<12){

                    if (!setParamlocal()) {
                        //showTip("请先构建语法。");
                        return;
                    };

                    ret = mAsr.startListening(mRecognizerListener);
                    setIsRunning(true);
                    lixiancishu++;
                }else {
                    lixiancishu=0;
                }
                Log.d("lixiancishu", String.valueOf(lixiancishu));

            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }

    };
    public boolean setParamlocal(){
        boolean result = false;
        // 清空参数
        mAsr.setParameter(SpeechConstant.PARAMS, null);
//        mAsr.setParameter(SpeechConstant.VAD_BOS, "2000");
//        // 关闭sdk内部录音，使用writeAudio接口传入音频
//        mAsr.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
//        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
//        mAsr.setParameter(SpeechConstant.VAD_EOS, "800");
//        mAsr.setParameter("domain", "fariat");
//        mAsr.setParameter("aue", "speex-wb;10");
        // 设置识别引擎
        mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置本地识别资源
        mAsr.setParameter(ResourceUtil.ASR_RES_PATH, getResourcePath());
        // 设置语法构建路径
        mAsr.setParameter(ResourceUtil.GRM_BUILD_PATH, grmPath);
        // 设置返回结果格式
        mAsr.setParameter(SpeechConstant.RESULT_TYPE, mResultType);
        // 设置本地识别使用语法id
        mAsr.setParameter(SpeechConstant.LOCAL_GRAMMAR, "call");
        // 设置识别的门限值
        mAsr.setParameter(SpeechConstant.MIXED_THRESHOLD, "50");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理

        // 使用8k音频的时候请解开注释
//			mAsr.setParameter(SpeechConstant.SAMPLE_RATE, "8000");
        result = true;


        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        //mAsr.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        //mAsr.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/asr.wav");
        return result;
    }

    //获取识别资源路径
    private String getResourcePath(){
        StringBuffer tempBuffer = new StringBuffer();
        //识别通用资源
        tempBuffer.append(ResourceUtil.generateResourcePath(this, ResourceUtil.RESOURCE_TYPE.assets, "asr/common.jet"));
        //识别8k资源-使用8k的时候请解开注释
//		tempBuffer.append(";");
//		tempBuffer.append(ResourceUtil.generateResourcePath(this, RESOURCE_TYPE.assets, "asr/common_8k.jet"));
        return tempBuffer.toString();
    }

    private void Offline_Answer(String s){

        if (s.contains("地税局")||s.contains("税务局")){
            TTS(Answer_Instance.Where_Local_Tax_Bureau);
            getUnderstandcontent = Answer_Instance.Where_Local_Tax_Bureau;
        }else if (s.contains("缴纳税款的规定")){
            TTS(Answer_Instance.Tax_payment_requirements);
            getUnderstandcontent = Answer_Instance.Tax_payment_requirements;
        }else if (s.contains("企业所得税的纳税人")){
            TTS(Answer_Instance.Qiyesuodeshuinashuiren);
            getUnderstandcontent = Answer_Instance.Qiyesuodeshuinashuiren;
        }else if (s.contains("企业所得税的税率")){
            TTS(Answer_Instance.Qiyesuideshuishuilv);
            getUnderstandcontent=Answer_Instance.Qiyesuideshuishuilv;
        }else if (s.contains("个人所得税的纳税人")){
            TTS(Answer_Instance.gerensuodeshuinashuiren);
            getUnderstandcontent=Answer_Instance.gerensuodeshuinashuiren;
        }else if (s.contains("个人所得税的征税范围")){
            TTS(Answer_Instance.gerensuideshuizhengshuifanwei);
            getUnderstandcontent=Answer_Instance.gerensuideshuizhengshuifanwei;
        }else if (s.contains("个人所得税的税率")){
            TTS(Answer_Instance.gerensuodeshuishuilv);
            getUnderstandcontent = Answer_Instance.gerensuodeshuishuilv;
        }else if (s.contains("个人所得税的计算公式")){
            TTS("请看屏幕");
            getUnderstandcontent = "请看屏幕";
        }else if (s.contains("土地使用税的纳税人")){
            TTS(Answer_Instance.tudishiyongshuinashuiren);
            getUnderstandcontent = Answer_Instance.tudishiyongshuinashuiren;
        }else if (s.contains("土地使用税的征税范围")){
            TTS(Answer_Instance.tudishuiyongshuizhengshuifanwei);
            getUnderstandcontent=Answer_Instance.tudishuiyongshuizhengshuifanwei;
        }else if (s.contains("房产税的纳税人")){
            TTS(Answer_Instance.fanchanshuinashuiren);
            getUnderstandcontent=Answer_Instance.fanchanshuinashuiren;
        }else if (s.contains("房产税的征税范围")){
            TTS(Answer_Instance.fanchanshuizhengshuifanwei);
            getUnderstandcontent=Answer_Instance.fanchanshuizhengshuifanwei;
        }else if (s.contains("车船税的纳税人")){
            TTS(Answer_Instance.chechuanshuidenashuiren);
            getUnderstandcontent = Answer_Instance.chechuanshuidenashuiren;
        }else if (s.contains("车船税的扣缴义务人")){
            TTS(Answer_Instance.chechuanshuidedaijiaoyiwuren);
            getUnderstandcontent = Answer_Instance.chechuanshuidedaijiaoyiwuren;
        }else if (s.contains("印花税的纳税人")){
            TTS(Answer_Instance.yinghuashuinashuiren);
            getUnderstandcontent = Answer_Instance.yinghuashuinashuiren;
        }else if (s.contains("印花税的征税范围")){
            TTS(Answer_Instance.yinghuashuizhengshuifanwei);
            getUnderstandcontent = Answer_Instance.yinghuashuizhengshuifanwei;
        }else if (s.contains("延期申报的规定")){
            TTS(Answer_Instance.yanqishenbaoguiding);
            getUnderstandcontent =Answer_Instance.yanqishenbaoguiding;
        }else if (s.equals("延期申报的条件")){
            TTS(Answer_Instance.yanqishengbaotiaojian);
            getUnderstandcontent = Answer_Instance.yanqishengbaotiaojian;
        }else if (s.contains("办理延期申报的流程")||s.equals("怎么办理延期申报")){
            TTS("请看屏幕");
            getUnderstandcontent = "请看屏幕";
        }else if (s.contains("你今年几岁了")||s.contains("你今年多大了")||s.contains("能告诉我你的年龄么")||s.contains("你几岁了")||s.contains("多大了")){
            String[] answer = {"我今年一岁啦","我还是个孩子呢","我是去年出生的"};
            String a = answer[new Random().nextInt(3)];
            TTS(a);
            getUnderstandcontent=a;
        }else if (s.contains("我想办理个人所得税")||s.contains("去哪办理个人所得税")||s.contains("我想资讯个人所得税")||s.contains("去哪资讯个人所得税")){
            TTS("请去往一号窗口办理个人所得税的相关业务");
            getUnderstandcontent ="请去往一号窗口办理个人所得税的相关业务";
        }else if (s.contains("我想办理企业所得税")||s.contains("去哪办理企业所得税")||s.contains("我想资讯企业所得税")||s.contains("去哪资讯企业所得税")){
            TTS("请去往二号窗口办理企业所得税的相关业务");
            getUnderstandcontent ="请去往二号窗口办理企业所得税的相关业务";
        }else if (s.contains("我想办理印花税")||s.contains("去哪办理印花税")||s.contains("我想资讯印花税")||s.contains("去哪资讯印花税")){
            TTS("请去往三号窗口办理印花税的相关业务");
            getUnderstandcontent ="请去往三号窗口办理印花税的相关业务";
        }else if (s.contains("我想办理车船税")||s.contains("去哪办理车船税")||s.contains("我想资讯车船税")||s.contains("去哪资讯车船税")){
            TTS("请去往四号窗口办理车船税的相关业务");
            getUnderstandcontent ="请去往四号号窗口办理车船税的相关业务";
        }
        else {
            TTS("答案太难了，请去服务大厅资讯大堂经理");
            getUnderstandcontent = "答案太难了，请去服务大厅资讯大堂经理";
        }
        getUnderstandresult();

    }

    public void Do_Ros_Nav(int cmd){
        Tts_Control_move=true;
        Robot_Sport=true;
        String msg1 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way1x + ",\"y\":" + way1y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way1z + ",\"w\":" + way1w + "}}}}";
        String msg2 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way2x + ",\"y\":" + way2y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way2z + ",\"w\":" + way2w + "}}}}";
        String msg3 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way3x + ",\"y\":" + way3y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way3z + ",\"w\":" + way3w + "}}}}";
        String msg4 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way4x + ",\"y\":" + way4y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way4z + ",\"w\":" + way4w + "}}}}";
        String msg5 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way5x + ",\"y\":" + way5y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way5z + ",\"w\":" + way5w + "}}}}";
        switch (cmd){
            case 1:
                client.send(msg1);
                if (!Robot_Com_back.isEmpty()){
                    Robot_Com_back.clear();
                }
                Robot_Com_back.offer(1);
                break;
            case 2:
                client.send(msg2);
                if (!Robot_Com_back.isEmpty()){
                    Robot_Com_back.clear();
                }
                Robot_Com_back.offer(2);
                break;
            case 3:
                client.send(msg3);
                if (!Robot_Com_back.isEmpty()){
                    Robot_Com_back.clear();
                }
                Robot_Com_back.offer(3);
                break;
            case 4:
                client.send(msg4);
                if (!Robot_Com_back.isEmpty()){
                    Robot_Com_back.clear();
                }
                Robot_Com_back.offer(4);
                break;
            case 5:
                client.send(msg5);
                if (!Robot_Com_back.isEmpty()){
                    Robot_Com_back.clear();
                }
                Robot_Com_back.offer(5);
                break;
            default:
                break;
        }
    }

    /**
     *
     * @param move_cmd
     * move_cmd=1 前进 ;2 后退; 3 左转; 4 右转
     */
    private void do_Robot_Move(int move_cmd){
        Handler handler_Robot_Move = new Handler();
        switch (move_cmd){
            case 1:
                TTS("好的，往前走了");
                liner_x_s = (float) 0.3;
                liner_y_s = 0;
                angular_z_s =0;
                isOnLongClick = true;
                moveThread = new AngleMoveThread();
                moveThread.start();

                handler_Robot_Move.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("postDelayed", "延时成功");
                        liner_x_s = 0;
                        liner_y_s = 0;
                        angular_z_s =0;

                    }
                }, 3000);
                handler_Robot_Move.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isOnLongClick=false;
                    }
                },4000);
                break;
            case 2:
                TTS("好的，往后走了");
                liner_x_s = (float) -0.3;
                liner_y_s = 0;
                angular_z_s =0;
                isOnLongClick = true;
                moveThread = new AngleMoveThread();
                moveThread.start();

                handler_Robot_Move.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("postDelayed", "延时成功");
                        liner_x_s = 0;
                        liner_y_s = 0;
                        angular_z_s =0;

                    }
                }, 3000);
                handler_Robot_Move.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isOnLongClick=false;
                    }
                },4000);
                break;
            case 3:
                TTS("收到，向左转了");
                liner_x_s = 0;
                liner_y_s = 0;
                angular_z_s = (float) 0.8;
                isOnLongClick = true;
                moveThread = new AngleMoveThread();
                moveThread.start();

                handler_Robot_Move.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("postDelayed", "延时成功");
                        liner_x_s = 0;
                        liner_y_s = 0;
                        angular_z_s =0;

                    }
                }, 2500);
                handler_Robot_Move.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isOnLongClick=false;
                    }
                },4000);
                break;
            case 4:
                TTS("收到，向右转了");
                liner_x_s = 0;
                liner_y_s = 0;
                angular_z_s = (float) -0.8;
                isOnLongClick = true;
                moveThread = new AngleMoveThread();
                moveThread.start();

                handler_Robot_Move.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("postDelayed", "延时成功");
                        liner_x_s = 0;
                        liner_y_s = 0;
                        angular_z_s =0;

                    }
                }, 2500);
                handler_Robot_Move.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isOnLongClick=false;
                    }
                },4000);
                break;
            default:
                break;
        }
    }


public void Print(){
    dialog.show();
    if (dev != null && mUsbPrinter.open(dev)) {
        mUsbPrinter.init();
        mUsbPrinter.doFunction(Const.TX_FONT_ULINE, Const.TX_ON, 0);
        mUsbPrinter.outputStringLn("This is Font A with underline.");
        mUsbPrinter.doFunction(Const.TX_SEL_FONT, Const.TX_FONT_B, 0);
        mUsbPrinter.doFunction(Const.TX_FONT_ULINE, Const.TX_OFF, 0);
        mUsbPrinter.doFunction(Const.TX_FONT_BOLD, Const.TX_ON, 0);
        mUsbPrinter.outputStringLn("This is Font B with bold.");
        mUsbPrinter.resetFont();
        mUsbPrinter.doFunction(Const.TX_ALIGN, Const.TX_ALIGN_CENTER, 0);
        mUsbPrinter.outputStringLn("center");
        mUsbPrinter.doFunction(Const.TX_ALIGN, Const.TX_ALIGN_RIGHT, 0);
        mUsbPrinter.outputStringLn("right");
        mUsbPrinter.doFunction(Const.TX_ALIGN, Const.TX_ALIGN_LEFT, 0);
        mUsbPrinter.doFunction(Const.TX_FONT_ROTATE, Const.TX_ON, 0);
        mUsbPrinter.outputStringLn("left & rotating");
        mUsbPrinter.resetFont();
        mUsbPrinter.doFunction(Const.TX_CHINESE_MODE, Const.TX_ON, 0);
        mUsbPrinter.outputStringLn("中文");
        mUsbPrinter.doFunction(Const.TX_FONT_SIZE, Const.TX_SIZE_3X, Const.TX_SIZE_2X);
        mUsbPrinter.doFunction(Const.TX_UNIT_TYPE, Const.TX_UNIT_MM, 0);
        mUsbPrinter.doFunction(Const.TX_HOR_POS, 20, 0);
        mUsbPrinter.outputStringLn("安徽达特智能科技有限公司");
        mUsbPrinter.resetFont();
        mUsbPrinter.doFunction(Const.TX_FEED, 30, 0);
        mUsbPrinter.outputStringLn("feed 30mm");
        mUsbPrinter.doFunction(Const.TX_BARCODE_HEIGHT, 15, 0);
        mUsbPrinter.printBarcode(Const.TX_BAR_UPCA, "12345678901");
        //mUsbPrinter.printImage("/storage/sdcard0/a1.png");
        //mUsbPrinter.printImage(getExternalFilesDir(null).getPath()+"/../../../../a1.png");
        mUsbPrinter.doFunction(Const.TX_UNIT_TYPE, Const.TX_UNIT_PIXEL, 0);
        mUsbPrinter.doFunction(Const.TX_FEED, 140, 0);
        mUsbPrinter.doFunction(Const.TX_CUT, Const.TX_CUT_FULL, 0);
        mUsbPrinter.close();
    }
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            dialog.dismiss();
        }
    },2000);

}
public boolean lenovo(String s){
    GetdishuiAnswer getdishuiAnswer = GetdishuiAnswer.getInstance();
    if (getdishuiAnswer.haveketwords(s,"个人所得税")){
        key_word_state=1;
        return true;
    }
    if (getdishuiAnswer.haveketwords(s,"企业所得税")){
        key_word_state=2;
        return true;
    }
    if (getdishuiAnswer.haveketwords(s,"车床税")){
        key_word_state=3;
        return true;
    }
    if (getdishuiAnswer.haveketwords(s,"印花税")){
        key_word_state=4;
        return true;
    }
    key_word_state=0;
    return false;
}
public void main_lenovo(int i){}


    public void getporllingstaticfg(){}
    public void getsmile(){}
    public void getmainfg(){}
}







