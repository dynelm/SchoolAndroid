package com.dynelm.robotdarttest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.Response;

import org.json.simple.parser.JSONParser;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created by Dynelm on 2017/8/31 0031.
 */

public class ShowResultActivity extends BasicActivity  {
    private ImageView result_iv;
    String key = "GA2pxaLQDspK5muyxOgUz2ff9q1iZssR";//api_key
    String secret = "mdpji_CDVdo7Mun3Ua6-2NKElrfpNU-g";//api_secret
    StringBuffer sb = new StringBuffer();
    TextView mTextView;
    private TextView textView;

    FileInputStream fis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result_iv = (ImageView) findViewById(R.id.result_iv);
        mTextView = (TextView) findViewById(R.id.text);
        textView = (TextView) findViewById(R.id.showresult_textView);
        showPic2ImageView();
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(secret)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("please enter key and secret");
            builder.setTitle("");
            builder.show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    CommonOperate commonOperate = new CommonOperate(key, secret, false);
                    try {
                        Response res = commonOperate.searchByOuterId(null, null, getbitmap(), "AH_Dart", 1);
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
                                if (face_token.equals("691bf5704d60935cc80fc43bbe1c1f7f")) {

                                    TTS("您好 圣明明先生 欢迎您");

                                } else if (face_token.equals("2b30c78a3d20466bcfeafdcafafd7cf6")) {

                                    TTS("您好 牛中彬先生 欢迎您");
                                } else if (face_token.equals("48d4582cbb043140a8f9a64da6d2bcf5")) {
                                    TTS("您好 贺海波先生 欢迎您");
                                } else if (face_token.equals("8c81a09fed9ef5f427717d3764e009e5")) {
                                    Looper.prepare();

                                    Toast.makeText(ShowResultActivity.this, "你是王玉良", Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                } else if (face_token.equals("ea9aa9bf33c5bef849546a0643ed1171")) {
//                                    Looper.prepare();
//
//                                    Toast.makeText(ShowResultActivity.this,"你是王朋朋",Toast.LENGTH_LONG).show();
//                                    Looper.loop();
                                    TTS("您好 王朋朋先生 欢迎您");
                                }
                            }
                            Handler handler =new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(ShowResultActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            },3000);

                        }


                        Log.e("result", result);
                        sb.append("\n");
                        sb.append("\n");
                        sb.append("search result: ");
                        sb.append(result);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTextView.setText(sb.toString());
                            }
                        });


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }
    }

    private void showPic2ImageView() {
        String filePath = getIntent().getStringExtra(CameraActivity.FILE_PATH);
        if (!TextUtils.isEmpty(filePath)) {
            try {
                fis = new FileInputStream(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                Bitmap newbm = rotateBitmapByDegree(bitmap,180);
                result_iv.setImageBitmap(newbm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getbitmap() {
        String filePath = getIntent().getStringExtra(CameraActivity.FILE_PATH);
        if (!TextUtils.isEmpty(filePath)) {
            try {
                fis = new FileInputStream(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                bitmap = rotateBitmapByDegree(bitmap,180);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //将bitmap压缩成jpg格式，品质0.7传输，可提升人脸识别的速度
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                int compressedLen = baos.toByteArray().length;
                Log.d("compressdLem", String.valueOf(compressedLen));
                return baos.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        return null;

    }

    private byte[] getBitmap(int res) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }



    //将输入流转换成字节数组
    private static byte[] readInStream(InputStream is) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        while ((len = is.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        return baos.toByteArray();
    }
    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm
     *            需要旋转的图片
     * @param degree
     *            旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }


}