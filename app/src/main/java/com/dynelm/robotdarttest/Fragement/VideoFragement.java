package com.dynelm.robotdarttest.Fragement;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.dynelm.robotdarttest.MainActivity;
import com.dynelm.robotdarttest.R;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class VideoFragement extends Fragment {
    private Button startbutton;
    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 22;
    private static final int PERMISSION_REQ_ID_CAMERA = PERMISSION_REQ_ID_RECORD_AUDIO + 1;

    private RtcEngine mRtcEngine;// Tutorial Step 1
    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() { // Tutorial Step 1
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) { // Tutorial Step 5
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setupRemoteVideo(uid);
                }
            });
        }

        @Override
        public void onUserOffline(int uid, int reason) { // Tutorial Step 7
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserLeft();
                }
            });
        }

        @Override
        public void onUserMuteVideo(final int uid, final boolean muted) { // Tutorial Step 10
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserVideoMuted(uid, muted);
                }
            });
        }
    };
    private FrameLayout Container;
    private FrameLayout Container1;
    private static int i =0;
    int click = 0;
    private boolean video_user_end = false;
    boolean b;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.videofg, container,false);
        Container = (FrameLayout) view.findViewById(R.id.local_video_view_container);
        Container1 = (FrameLayout) view.findViewById(R.id.remote_video_view_container);
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO) && checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)) {
                    initAgoraEngineAndJoinChannel();
                }
//        startbutton = (Button) view.findViewById(R.id.btn_video_start);
//        startbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO) && checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)) {
//                    initAgoraEngineAndJoinChannel();
//                }
//            }
//        });
//        Button btn_video_stop = view.findViewById(R.id.stopvideo);
//        btn_video_stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    leaveChannel();
//                    SurfaceView surfaceView = (SurfaceView) Container.getChildAt(i);
//                    surfaceView.setVisibility( View.GONE);
//                    SurfaceView surfaceView1 = (SurfaceView) Container1.getChildAt(0);
//                    surfaceView1.setVisibility(View.GONE);
//                    i++;
//                }catch (Exception e){
//                    Log.d("Error","摄像头打开错误");
//                }
//
//            }
//        });
        ImageView imageView = view.findViewById(R.id.onLocalVideoMuteClicked);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click++;

                if (click%2==1){
                    b=true;
                }else {
                    b=false;
                }
                mRtcEngine.muteLocalVideoStream(b);

        //FrameLayout container = (FrameLayout) findViewById(R.id.local_video_view_container);
        SurfaceView surfaceView = (SurfaceView) Container.getChildAt(0);
        surfaceView.setZOrderMediaOverlay(b);
        surfaceView.setVisibility(b ? View.GONE : View.VISIBLE);
            }
        });
        ImageView stop_img = view.findViewById(R.id.onEncCallClicked);
        stop_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                leaveChannel();
                video_user_end=true;

            }
        });
        ImageView start_again = view.findViewById(R.id.start_again);
        start_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO) && checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)) {
                    initAgoraEngineAndJoinChannel();
                }
            }
        });

        return view;
    }
    private void initAgoraEngineAndJoinChannel() {
        initializeAgoraEngine();     // Tutorial Step 1
        setupVideoProfile();         // Tutorial Step 2
        setupLocalVideo();           // Tutorial Step 3
        joinChannel();// Tutorial Step 4
        video_user_end=false;
    }

    public boolean checkSelfPermission(String permission, int requestCode) {
        //Log.i(LOG_TAG, "checkSelfPermission " + permission + " " + requestCode);
        if (ContextCompat.checkSelfPermission(getContext(),
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{permission},
                    requestCode);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        //Log.i(LOG_TAG, "onRequestPermissionsResult " + grantResults[0] + " " + requestCode);

        switch (requestCode) {
            case PERMISSION_REQ_ID_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA);
                } else {
                    showLongToast("No permission for " + Manifest.permission.RECORD_AUDIO);
                    //finish();
                }
                break;
            }
            case PERMISSION_REQ_ID_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initAgoraEngineAndJoinChannel();
                } else {
                    showLongToast("No permission for " + Manifest.permission.CAMERA);
                    //finish();
                }
                break;
            }
        }
    }

    public final void showLongToast(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRtcEngine != null) {
            leaveChannel();
            RtcEngine.destroy();
            mRtcEngine = null;
        }

    }
    // Tutorial Step 10
//    public void onLocalVideoMuteClicked(View view) {
//        ImageView iv = (ImageView) view;
//        if (iv.isSelected()) {
//            iv.setSelected(false);
//            iv.clearColorFilter();
//        } else {
//            iv.setSelected(true);
//            iv.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
//        }
//
//        mRtcEngine.muteLocalVideoStream(iv.isSelected());
//
//        //FrameLayout container = (FrameLayout) findViewById(R.id.local_video_view_container);
//        SurfaceView surfaceView = (SurfaceView) container.getChildAt(0);
//        surfaceView.setZOrderMediaOverlay(!iv.isSelected());
//        surfaceView.setVisibility(iv.isSelected() ? View.GONE : View.VISIBLE);
//    }
//
    // Tutorial Step 9
    public void onLocalAudioMuteClicked() {
//        ImageView iv = (ImageView) view;
//        if (iv.isSelected()) {
//            iv.setSelected(false);
//            iv.clearColorFilter();
//        } else {
//            iv.setSelected(true);
//            iv.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
//        }

        mRtcEngine.muteLocalAudioStream(true);
    }



    // Tutorial Step 1
    private void initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(getContext(), getString(R.string.agora_app_id), mRtcEventHandler);
        } catch (Exception e) {
            //  Log.e(LOG_TAG, Log.getStackTraceString(e));

            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    // Tutorial Step 2
    private void setupVideoProfile() {
        mRtcEngine.enableVideo();
        mRtcEngine.setVideoProfile(Constants.VIDEO_PROFILE_360P, true);
    }

    // Tutorial Step 3
    private void setupLocalVideo() {
        //FrameLayout container = (FrameLayout) getView().findViewById(R.id.local_video_view_container);
        SurfaceView surfaceView = RtcEngine.CreateRendererView(getContext());
        surfaceView.setZOrderMediaOverlay(true);
        Container.addView(surfaceView);


        mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_ADAPTIVE, 0));
        mRtcEngine.setLocalRenderMode(1);
    }

    // Tutorial Step 4
    private void joinChannel() {
        mRtcEngine.joinChannel(null, "demoChannel1", "Extra Optional Data", 0); // if you do not specify the uid, we will generate the uid for you
    }

    // Tutorial Step 5
    private void setupRemoteVideo(int uid) {
        //FrameLayout container = (FrameLayout) getView().findViewById(R.id.remote_video_view_container);

        if (Container1.getChildCount() >= 1) {
            return;
        }

        SurfaceView surfaceView = RtcEngine.CreateRendererView(getContext());

        Container1.addView(surfaceView);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_ADAPTIVE, uid));

        surfaceView.setTag(uid); // for mark purpose
//        View tipMsg = findViewById(R.id.quick_tips_when_use_agora_sdk); // optional UI
//        tipMsg.setVisibility(View.GONE);
    }

    // Tutorial Step 6
    public void leaveChannel() {
        mRtcEngine.leaveChannel();
    }

    // Tutorial Step 7
    private void onRemoteUserLeft() {
        //FrameLayout container = (FrameLayout) getView().findViewById(R.id.remote_video_view_container);
        Container1.removeAllViews();

//        View tipMsg = findViewById(R.id.quick_tips_when_use_agora_sdk); // optional UI
//        tipMsg.setVisibility(View.VISIBLE);
    }

    // Tutorial Step 10
    private void onRemoteUserVideoMuted(int uid, boolean muted) {
        //FrameLayout container = (FrameLayout) getView().findViewById(R.id.remote_video_view_container);

        SurfaceView surfaceView = (SurfaceView) Container1.getChildAt(0);
        try {
            Object tag = surfaceView.getTag();
            if (tag != null && (Integer) tag == uid) {
                surfaceView.setVisibility(muted ? View.GONE : View.VISIBLE);
            }
        }catch (NullPointerException e){

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (video_user_end){
            Container.removeViewAt(0);
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO) && checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)) {
                initAgoraEngineAndJoinChannel();
            }

        }
    }
}
