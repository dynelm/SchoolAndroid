package com.dynelm.robotdarttest.Fragement;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dynelm.robotdarttest.entity.KqwSpeechCompound;
import com.dynelm.robotdarttest.MainActivity;
import com.dynelm.robotdarttest.R;
import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.Response;

import org.json.simple.parser.JSONParser;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class ShowResultFragement{
    StringBuffer sb = new StringBuffer();
    String key = "GA2pxaLQDspK5muyxOgUz2ff9q1iZssR";//api_key
    String secret = "mdpji_CDVdo7Mun3Ua6-2NKElrfpNU-g";//api_secret


    public  String getRecResult(byte[] bitmap){
        CommonOperate commonOperate = new CommonOperate(key, secret, false);
        try {
            Response res = commonOperate.searchByOuterId(null, null, bitmap, "AH_Dart", 1);
            String result = new String(res.getContent());
            JSONParser parser = new JSONParser();
            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(result);
            org.json.simple.JSONArray jsonresult = (org.json.simple.JSONArray) jsonObject.get("results");
            if (jsonresult != null) {
                org.json.simple.JSONObject jsonresult1 = (org.json.simple.JSONObject) jsonresult.get(0);
                Double confidence = (Double) jsonresult1.get("confidence");
                String face_token = (String) jsonresult1.get("face_token");
                Log.d("confidence", String.valueOf(confidence));
                Log.d("face_token", face_token);
                if (confidence > 70) {
                    if (face_token.equals("87679014989c078fed6dac9e4aaf1268")||face_token.equals("691bf5704d60935cc80fc43bbe1c1f7f")) {

                       return  "您好 圣明明先生 欢迎您";



                    } else if (face_token.equals("2b30c78a3d20466bcfeafdcafafd7cf6")) {

                         return "您好 牛中彬先生 欢迎您";

                    } else if (face_token.equals("48d4582cbb043140a8f9a64da6d2bcf5")) {
                        return "您好 贺海波先生 欢迎您";

                    } else if (face_token.equals("8c81a09fed9ef5f427717d3764e009e5")) {

                      return  "您好 王玉良先生 欢迎您";
                    } else if (face_token.equals("ea9aa9bf33c5bef849546a0643ed1171")) {

                        return "您好 王朋朋 欢迎您";
                    }
                }



            }
            else {

                return "对不起,请先建立档案";
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return "对不起,请先建立档案";
    }





}

