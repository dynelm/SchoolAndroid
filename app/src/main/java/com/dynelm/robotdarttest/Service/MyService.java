package com.dynelm.robotdarttest.Service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dynelm.robotdarttest.BasicActivity;
import com.dynelm.robotdarttest.SerialDataUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.TimerTask;

import android_serialport_api.SerialPort;

public class MyService extends Service {
    private boolean connecting = false;
    private Callback callback;
    //通过串口获取声源定位信息
    private String sPort = "/dev/ttyS1";
    private int iBaudRate = 115200;
    public SerialPort mSerialPort;
    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    private String sPMPort = "/dev/ttySAC2";
    private int iPMBaudRate = 2400;
    public SerialPort mPMSerialPort;
    private OutputStream mPMOutputStream;
    private InputStream mPMInputStream;
    private ReadPMThread mPMReadThread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    public class Binder extends android.os.Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        connecting = true;
        //        获取串口实例
//                获取串口实例
        try {
            mSerialPort = new SerialPort(new File(sPort), iBaudRate, 0);
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();

            mReadThread = new ReadThread();
            mReadThread.start();
            mPMSerialPort = new SerialPort(new File(sPMPort),iPMBaudRate,0);
            mPMOutputStream = mPMSerialPort.getOutputStream();
            mPMInputStream = mPMSerialPort.getInputStream();
            mPMReadThread = new ReadPMThread();
            mPMReadThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public static interface Callback {
        void onDataChange(String data);
        void onPMDataChange(String data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        connecting = false;
        mSerialPort.close();
    }

    /**
     * 读串口线程
     */
    String result = "";
    class ReadThread extends Thread {
        @Override
        public void run() {

            super.run();
            while (!isInterrupted()) {

                if (mInputStream != null) {

                    byte[] buffer = new byte[512];
                    int size = 0;
                    try {
                        size = mInputStream.read(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (size > 0) {

                        byte[] buffer2 = new byte[size];
                        for (int i = 0; i < size; i++) {
                            buffer2[i] = buffer[i];
                        }

                        try {
                            final String receiveString = SerialDataUtils.ByteArrToHex(buffer2).trim();
//                            if (receiveString != null && receiveString.length() > 30) {
//                                int lenth = receiveString.length();
//                                Log.d("receiveString", String.valueOf(receiveString.length()));
//                                String chulistring = null;
//                                try {
//                                    if (lenth % 2 == 0) {
//                                        chulistring = receiveString.substring(0, 55);
//                                    } else if (!(lenth % 2 == 0)) {
//                                        chulistring = receiveString.substring(0, 57);
//                                    }
//                                }catch (Exception e){
//                                    Log.e("shuju","数据长度不够");
//                                }
//
//                                Log.d("原始数据",hexStringToString(chulistring));
//                                if (callback!=null){
//                                    callback.onDataChange(chulistring);
//                                }
//
////                                mTts.stopSpeaking();
////                                if (MoveTimer != null) {
////                                    if (moveTimerTask != null) {
////                                        moveTimerTask.cancel();
////                                    }
////                                }
////
////                                if (!chulistring.equals("")) {
////                                    //TTS("仔仔在这呢  哈");
////                                    if (isMainActivity) {
//////                                        if (!isjiaqiangbishu) {
//////                                            Log.d("chulshuju", chulistring);
//////                                            dosoundlocal(chulistring);
//////                                        } else {
//////                                            isjiaqiangbishu = false;
//////                                        }
////                                        dosoundlocal(chulistring);
////
////                                    } else {
////                                        Log.d("isMainActivity", "不是主Activity");
////                                    }
////                                }
//
//
//                            }
//                            if (receiveString.length()==23){
//                                if (callback!=null){
//                                    callback.onDataChange(receiveString);
//                                }
//                            }
                            result+=receiveString;
                            if (result.contains("0D 0A")){
                                if (callback!=null){
                                    callback.onDataChange(result);
                                    result="";
                                }
                            }
                            System.out.println(receiveString);
                        } catch (NullPointerException e) {
                            System.out.println("eeeee");

                        }


                    }
                    try {
                        //延时500ms
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;

                    }
                }
            }
            return;
        }
    }
    /**
     * 读PM串口线程
     */
    class ReadPMThread extends Thread {
        @Override
        public void run() {

            super.run();
            while (!isInterrupted()) {

                if (mPMInputStream != null) {

                    byte[] buffer = new byte[512];
                    int size = 0;
                    try {
                        size = mPMInputStream.read(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (size > 0) {

                        byte[] buffer2 = new byte[size];
                        for (int i = 0; i < size; i++) {
                            buffer2[i] = buffer[i];
                        }
                         String receiveString = SerialDataUtils.ByteArrToHex(buffer2).trim();
                        if (receiveString != null && receiveString.length() >= 30) {
                            try {
                                int start = receiveString.indexOf("AA");
                                char gaozijie1 = receiveString.charAt(start + 3);
                                char gaozijie2 = receiveString.charAt(start + 4);
                                char dizijie1 = receiveString.charAt(start + 6);
                                char dizijie2 = receiveString.charAt(start + 7);
                                //Log.d("dad", String.valueOf(dizijie2));
                                String gaozijie = "" + gaozijie1 + gaozijie2;
                                String dizijie = "" + dizijie1 + dizijie2;
                                int intgaozijie = Integer.parseInt(gaozijie, 16);
                                int intdizijie = Integer.parseInt(dizijie, 16);

                                Log.d("123", String.valueOf(intdizijie));
                                Log.d("123", receiveString);

                                char[] data = receiveString.toCharArray();
                                float result = ((float) (((((char) intgaozijie << 8) + (char) intdizijie) / 128.0 * 1000)/2));
                                final String finalresult = String.valueOf(result);
                                Log.d("pmRESULT",finalresult);
                                if (callback!=null){
                                    callback.onPMDataChange(finalresult);
                                }
                            }catch (NumberFormatException e){
                                Log.d("PMeeee","error");
                            }

                        }
                    }
                    try {
                        //延时500ms
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;

                    }
                }
            }
            return;
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

    /**
     * 发串口数据
     */
    int iResult = 0;

    public int send(byte[] bOutArray) {
        try {
            if (mOutputStream == null) {
                return 0;
            }
            mOutputStream.write(bOutArray);
            iResult = bOutArray.length;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return iResult;
    }



    public void sendTxt(String sTxt) {
        byte[] bOutArray = new byte[0];
        try {
            bOutArray = sTxt.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int result = send(bOutArray);
        if (result == 6) {
            //mSerialPort.close();
        }
        Log.d("sendTxt","sTXt");
    }

}