package com.dynelm.robotdarttest.Fragement;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dynelm.robotdarttest.CameraActivity;
import com.dynelm.robotdarttest.MainActivity;
import com.dynelm.robotdarttest.R;
import com.dynelm.robotdarttest.ShowResultActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class CamFragement extends Fragment implements View.OnClickListener, SurfaceHolder.Callback {
    private SurfaceView camera_sf;
    private Button camera_btn;
    //安卓硬件相机
    private Camera mCamera;
    private SurfaceHolder mHolder;
    public static String FILE_PATH ;
    public static int requestCode = 0;
    private MainActivity mainActivity;
    private ShowResultFragement showResultFragement = new ShowResultFragement();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.camera_layout, container,false);
        camera_sf = (SurfaceView) view.findViewById(R.id.camera_sf);
        camera_btn = (Button) view.findViewById(R.id.camera_btn);
        camera_sf.setOnClickListener(this);
        camera_btn.setOnClickListener(this);

        //获取SurfaceView的SurfaceHolder对象
        mHolder = camera_sf.getHolder();
        //实现SurfaceHolder.Callback接口
        mHolder.addCallback(this);
        mainActivity = (MainActivity) getActivity();
        ImageView cam_main = view.findViewById(R.id.cam_main);
        ImageView cam_sp = view.findViewById(R.id.cam_shiping);
        cam_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setChioceItem(2);
            }
        });
        cam_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setChioceItem(5);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null == mCamera) {
            try{
                mCamera =getCustomCamera();
                if (mHolder != null) {

                    //开户相机预览
                    previceCamera(mCamera, mHolder);


                }
            }catch (Exception e){
                Toast.makeText(getActivity(),"相机资源正在被占用，请先关闭视屏流摄像头",Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.setChioceItem(5);
                    }
                },1000);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            //停止预览
            mCamera.stopPreview();
            //释放相机资源
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            //停止预览
            mCamera.stopPreview();
            //释放相机资源
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera_sf://点击可以对焦
                if (null != mCamera)
                    mCamera.autoFocus(null);
                break;
            case R.id.camera_btn://点击进行拍照
                startTakephoto();

                break;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            previceCamera(mCamera, surfaceHolder);
        }catch (Exception e){

        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        try {
            mCamera.stopPreview();
            previceCamera(mCamera, surfaceHolder);
        }catch (Exception e){
            return;
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            //停止预览
            mCamera.stopPreview();
            //释放相机资源
            mCamera.release();
            mCamera = null;
        }
    }

    private void startTakephoto() {
        //获取到相机参数
        Camera.Parameters parameters = mCamera.getParameters();
        //设置图片保存格式
        parameters.setPictureFormat(ImageFormat.JPEG);
        //设置图片大小
        parameters.setPreviewSize(1080, 720);
        //设置对焦
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        //设置自动对焦
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //dealWithCameraData(data);
                //mainActivity.setChioceItem(4);
                String re =showResultFragement.getRecResult(data);
                mainActivity.TTS(re);
                mainActivity.setChioceItem(3);

            }
        });

        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {

                }
            }
        });
    }

    //保存拍照数据
    private void dealWithCameraData(byte[] data) {
        FileOutputStream fos = null;
        String sdCardDir = Environment.getExternalStorageDirectory() + "/CoolImage/";
        //图片临时保存位置
        String fileName = sdCardDir + "face" + ".jpg";
        FILE_PATH = fileName;
        File tempFile = new File(fileName);
        try {
            fos = new FileOutputStream(fileName);
            //保存图片数据
            fos.write(data);
            fos.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
}

    private Camera getCustomCamera() {
        if (null == mCamera) {
            //使用Camera的Open函数开机摄像头硬件
            try {
                mCamera = Camera.open();
            }catch (RuntimeException e){
                Log.d("eeeeeeeeeeeeeeeeee","eeeeeeeeeeeeeeee");
                return null;
            }

            //mCamera.setDisplayOrientation(180);
            //Camera.open()方法说明：2.3以后支持多摄像头，所以开启前可以通过getNumberOfCameras先获取摄像头数目，
            // 再通过 getCameraInfo得到需要开启的摄像头id，然后传入Open函数开启摄像头，
            // 假如摄像头开启成功则返回一个Camera对象
        }
        return mCamera;
    }

    private void previceCamera(Camera camera, SurfaceHolder holder) {
        try {
            //摄像头设置SurfaceHolder对象，把摄像头与SurfaceHolder进行绑定
            camera.setPreviewDisplay(holder);
            //调整系统相机拍照角度
            //camera.setDisplayOrientation(180);
            //调用相机预览功能
            camera.startPreview();
        } catch (IOException e) {
            Log.d("eeeeeee","没有可用的摄像头");
        }
    }
}
