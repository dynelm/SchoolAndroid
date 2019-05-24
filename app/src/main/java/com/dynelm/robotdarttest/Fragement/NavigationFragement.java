package com.dynelm.robotdarttest.Fragement;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dynelm.robotdarttest.BasicActivity;
import com.dynelm.robotdarttest.Interface.Observe_Fragement;
import com.dynelm.robotdarttest.Service.MyService;
import com.dynelm.robotdarttest.Util.DrawCircle;
import com.dynelm.robotdarttest.entity.KqwSpeechCompound;
import com.dynelm.robotdarttest.MainActivity;
import com.dynelm.robotdarttest.R;
import com.dynelm.robotdarttest.ShowGifActivity;
import com.jilk.ros.rosbridge.ROSBridgeClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.dynelm.robotdarttest.Event_listeners.boolen_Listen;


/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class NavigationFragement extends Fragment implements Observe_Fragement {
    private TextView txt_navmap;
    private ImageView img_map;
    private ImageView setWaypoint;
    private Button btnWaypoint1;
    private Button btnWaypoint2;
    private Button btnWaypoint3;
    private Button btnWaypoint4;
    private Button btnWaypoint5;
    private boolean ashow = true;
    private boolean bshow = true;
    private boolean cshow = true;
    private boolean dshow = true;
    private boolean eshow = true;
    private String newname;
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
    private String havewaypoint1 = null;
    private String havewaypoint2 = null;
    private String havewaypoint3 = null;
    private String havewaypoint4 = null;
    private String havewaypoint5 = null;//判断是否将导航点显示出来
    SharedPreferences.Editor editor;//轻量储存，用于储存数据
    SharedPreferences sp;
    ROSBridgeClient client;
    private static int i = 0;
    // 设置初始化点
    private Button btn_init;
    //导航初始化
    private Button btn_nav_init;
    //接收ROS讯飞语音
    private Button xfwords;
    //实现巡逻的功能
    private Button btn_Patrolling;

    private Handler handler;
    //巡逻点名称
    private String Waypoint_name1;
    private String Waypoint_name2;
    private String Waypoint_name3;
    private String Waypoint_name4;
    private String Waypoint_name5;
    private float Current_X;
    private float Current_Y;
    private float Current_Z;
    private float Current_W;


    //设置是不是获取第一个点的标志位 防止位置信息被篡改或者覆盖
    private boolean is_getonepoint_amcl = false;
    //语音合成
    private KqwSpeechCompound kqwSpeechCompound;

    private List<Float> floatList = new ArrayList<Float>();
    MainActivity mainActivity;
    int[] Current={100,50};
    private final int CURRENT =33;
    private boolean show_point =false;
    Button btn_xfwords;
    ImageView mf_face;
    ImageView mf_sp;
    private String hide ;
    Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navfg, container, false);
        mainActivity = (MainActivity) getActivity();
        client = mainActivity.client;
        mainActivity.addObserve(this);

        sp = getActivity().getSharedPreferences("ROBOT",
                Activity.MODE_PRIVATE);
        editor = sp.edit();
        final DrawCircle drawCircle = view.findViewById(R.id.drawcicle);
        String detailName = "/amcl_pose";
        client.send("{\"op\":\"subscribe\",\"topic\":\"" + detailName + "\"}");



//        img_map = (ImageView) view.findViewById(R.id.img_map);
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream("/storage/emulated/0/CoolImage/mapset.jpg");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (fis == null) {
//            Toast.makeText(getActivity(), "对不起，请您先去构建地图", Toast.LENGTH_LONG).show();
//        } else {
//            try {
//                Bitmap bitmap = BitmapFactory.decodeStream(fis);
//                img_map.setBackground(new BitmapDrawable(getResources(), adjustPhotoRotation(bitmap, 90)));
//
//                img_map.setScaleY(-1);
//                Toast.makeText(getContext(), "恭喜你，地图信息载入成功", Toast.LENGTH_LONG).show();
//            } catch (NullPointerException e) {
//
//            }
//
//        }

        havewaypoint1 = sp.getString("Waypoint1", "");
        havewaypoint2 = sp.getString("Waypoint2", "");
        havewaypoint3 = sp.getString("Waypoint3", "");
        havewaypoint4 = sp.getString("Waypoint4", "");
        havewaypoint5 = sp.getString("Waypoint5", "");
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
        setWaypoint = (ImageView) view.findViewById(R.id.imasetWaypoint);
        setWaypoint.setEnabled(false);
        btnWaypoint1 = (Button) view.findViewById(R.id.WayPoint1);
        btnWaypoint2 = (Button) view.findViewById(R.id.WayPoint2);
        btnWaypoint3 = (Button) view.findViewById(R.id.WayPoint3);
        btnWaypoint4 = (Button) view.findViewById(R.id.WayPoint4);
        btnWaypoint5 = (Button) view.findViewById(R.id.WayPoint5);
        btnWaypoint1.setText(Waypoint_name1);
        btnWaypoint2.setText(Waypoint_name2);
        btnWaypoint3.setText(Waypoint_name3);
        btnWaypoint4.setText(Waypoint_name4);
        btnWaypoint5.setText(Waypoint_name5);
//        if (havewaypoint1.equals("")) {
//            btnWaypoint1.setVisibility(View.GONE);
//        } else if (havewaypoint1.equals("true")) {
//            btnWaypoint1.setVisibility(View.VISIBLE);
//            btnWaypoint1.setText(Waypoint_name1);
//        }
//        if (havewaypoint2.equals("")) {
//            btnWaypoint2.setVisibility(View.GONE);
//        } else if (havewaypoint2.equals("true")) {
//            btnWaypoint2.setVisibility(View.VISIBLE);
//            btnWaypoint2.setText(Waypoint_name2);
//        }
//        if (havewaypoint3.equals("")) {
//            btnWaypoint3.setVisibility(View.GONE);
//        } else if (havewaypoint3.equals("true")) {
//            btnWaypoint3.setVisibility(View.VISIBLE);
//            btnWaypoint3.setText(Waypoint_name3);
//        }
//        if (havewaypoint4.equals("")) {
//            btnWaypoint4.setVisibility(View.GONE);
//        } else if (havewaypoint4.equals("true")) {
//            btnWaypoint4.setVisibility(View.VISIBLE);
//            btnWaypoint4.setText(Waypoint_name4);
//        }
//        if (havewaypoint5.equals("")) {
//            btnWaypoint5.setVisibility(View.GONE);
//        } else if (havewaypoint5.equals("true")) {
//            btnWaypoint5.setVisibility(View.VISIBLE);
//            btnWaypoint5.setText(Waypoint_name5);
//        }
        i = sp.getInt("number", 0);
        setWaypoint.setOnClickListener(new View.OnClickListener() {
            private String detailName = "/amcl_pose";

            @Override
            public void onClick(View view) {
                client.send("{\"op\":\"subscribe\",\"topic\":\"" + detailName + "\"}");
                rename(i);
                if (i == 0) {
                    ashow = true;
                    editor.putString("Waypoint1", "true");
                    editor.putInt("number", 1);
                    editor.commit();
                    btnWaypoint1.setVisibility(ashow ? View.VISIBLE : View.GONE);
                } else if (i == 1) {
                    ashow = true;
                    bshow = true;
                    btnWaypoint2.setVisibility(bshow ? View.VISIBLE : View.GONE);
                    editor.putInt("number", 2);
                    editor.putString("Waypoint2", "true");

                    editor.commit();
                } else if (i == 2) {
                    cshow = true;
                    btnWaypoint3.setVisibility(cshow ? View.VISIBLE : View.GONE);
                    editor.putString("Waypoint3", "true");
                    editor.putInt("number", 3);
                    editor.commit();

                } else if (i == 3) {
                    dshow = true;
                    btnWaypoint4.setVisibility(dshow ? View.VISIBLE : View.GONE);
                    editor.putString("Waypoint4", "true");
                    editor.putInt("number", 4);
                    editor.commit();
                } else if (i == 4) {
                    eshow = true;
                    btnWaypoint5.setVisibility(eshow ? View.VISIBLE : View.GONE);
                    editor.putString("Waypoint5", "true");
                    editor.putInt("number", 5);
                    editor.commit();
                }

                if (i >= 5) {
                    Toast.makeText(getContext(), "对不起，最多只能有5个导航点", Toast.LENGTH_SHORT).show();
                    i = 5;
                    editor.putInt("number", 5);
                    editor.commit();
                }

                i++;

            }


        });
        btnWaypoint1.setOnClickListener(new View.OnClickListener() {
            String string = "map";
            float w = (float) 2.0;


            @Override
            public void onClick(View v) {
                String msg1 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way1x + ",\"y\":" + way1y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way1z + ",\"w\":" + way1w + "}}}}";
                client.send(msg1);


            }
        });
        btnWaypoint2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg1 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way2x + ",\"y\":" + way2y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way2z + ",\"w\":" + way2w + "}}}}";
                client.send(msg1);


            }
        });
        btnWaypoint3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg1 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way3x + ",\"y\":" + way3y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way3z + ",\"w\":" + way3w + "}}}}";
                client.send(msg1);

            }
        });
        btnWaypoint4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg1 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way4x + ",\"y\":" + way4y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way4z + ",\"w\":" + way4w + "}}}}";
                client.send(msg1);

            }
        });
        btnWaypoint5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg1 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way5x + ",\"y\":" + way5y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way5z + ",\"w\":" + way5w + "}}}}";
                client.send(msg1);

            }
        });
        btnWaypoint1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                i = 1;

                relongname(0);

                return false;
            }
        });
        btnWaypoint2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                i = 2;

                relongname(1);

                return false;
            }
        });
        btnWaypoint3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                i = 3;

                relongname(2);

                return false;
            }
        });
        btnWaypoint4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                i = 4;

                relongname(3);

                return false;
            }
        });
        btnWaypoint5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                i = 5;
                String detailName = "/amcl_pose";
                client.send("{\"op\":\"subscribe\",\"topic\":\"" + detailName + "\"}");
                relongname(4);

                return false;
            }
        });

        //设置回归原点
        btn_init = (Button) view.findViewById(R.id.btn_init);
        btn_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg1 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":0,\"y\":0,\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":0,\"w\":1}}}}";
                client.send(msg1);
            }
        });
        //导航初始化
        //响应导航

        //初始化语音合成
        kqwSpeechCompound = BasicActivity.kqwSpeechCompound;
        Log.d("kqwSpeec", String.valueOf(kqwSpeechCompound== BasicActivity.kqwSpeechCompound));
        //设置巡逻点
        btn_Patrolling = (Button) view.findViewById(R.id.btn_Patrolling);
        btn_Patrolling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.timer!=null){
                    MainActivity.timer.cancel();
                    MainActivity.timer=null;

                }if (mainActivity.timerTask!=null){
                    mainActivity.timerTask.cancel();
                    mainActivity.timerTask=null;
                }
                SetPorlloingTimes();
            }
        });


        handler = new Handler(new Handler.Callback() {
            int position;

            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {

//                    case 6:
//                        String msg6 ="{\"op\":\"publish\",\"topic\":\"/base/goal_set_point\",\"msg\":{\"X\":"+way3x+",\"Y\":"+way3y+",\"Z\":0,\"OR_X\":0,\"OR_Y\":0,\"OR_Z\":"+way3z+",\"OR_W\":"+way3w+"}}";
//                        client.send(msg6);
//                        break;
                    case CURRENT:
                        int[] X_Y = (int[]) msg.obj;
                        int X = (int) (X_Y[0]/10);
                        int Y = (int) (X_Y[1]/20);
                        drawCircle.Activity_draw((674-X-70),(340+Y-50));
                        break;

                }
                return true;
            }
        });
//        btn_xfwords = view.findViewById(R.id.xfwords);
//        btn_xfwords.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //订阅语音消息
//                String msg_xfwords = "/move_base/result";
//                client.send("{\"op\":\"subscribe\",\"topic\":\"" + msg_xfwords + "\"}");
//            }
//        });
        //地图点击
        RelativeLayout First_Work = view.findViewById(R.id.First_Work);
        RelativeLayout Second_Work = view.findViewById(R.id.Secomd_Work);
        RelativeLayout Third_Work = view.findViewById(R.id.Three_Work);
        RelativeLayout Four_Work = view.findViewById(R.id.Four_Work);
        RelativeLayout Fuwutai = view.findViewById(R.id.Fuwutai);
        First_Work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.Do_Ros_Nav(2);
                kqwSpeechCompound.speaking("仔仔带您去"+Waypoint_name2);
            }
        });
        Second_Work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.Do_Ros_Nav(3);
                kqwSpeechCompound.speaking("仔仔带您去"+Waypoint_name3);
            }
        });
        Third_Work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.Do_Ros_Nav(4);
                kqwSpeechCompound.speaking("仔仔带您去"+Waypoint_name4);
            }
        });
        Four_Work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.Do_Ros_Nav(5);
                kqwSpeechCompound.speaking("仔仔带您去"+Waypoint_name5);
            }
        });
        Fuwutai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.Do_Ros_Nav(1);
                kqwSpeechCompound.speaking("仔仔带您去"+Waypoint_name1);
            }
        });
        img_map = view.findViewById(R.id.img_map);
        img_map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("坐标","x"+motionEvent.getX()+"                  "+"y"+motionEvent.getY());
                return false;
            }
        });

//          Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                Message message = new Message();
//                message.what = 33;
//                message.obj=Current;
//                handler.sendMessage(message);
//                Current[0] = Current[0]+10;
//                Current[1] = Current[1]+10;
//                Log.d("Current", String.valueOf(Current[0])+"YYYY"+Current[1]);
//            }
//        };
//        timer.schedule(timerTask,100,100);
         mf_face = view.findViewById(R.id.mf_face);
         mf_sp = view.findViewById(R.id.mf_sp);
        mf_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setChioceItem(3);
            }
        });
        mf_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setChioceItem(5);
            }
        });
        button = view.findViewById(R.id.near_point);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSingleAlertDialog();
            }
        });
        if (mainActivity.NORMAL){
            //btn_xfwords.setVisibility(View.INVISIBLE);
            btn_init.setVisibility(View.INVISIBLE);
            btn_Patrolling.setVisibility(View.INVISIBLE);
            btnWaypoint1.setVisibility(View.INVISIBLE);
            btnWaypoint2.setVisibility(View.INVISIBLE);
            btnWaypoint3.setVisibility(View.INVISIBLE);
            btnWaypoint4.setVisibility(View.INVISIBLE);
            btnWaypoint5.setVisibility(View.INVISIBLE);
            mf_face.setVisibility(View.INVISIBLE);
            mf_sp.setVisibility(View.INVISIBLE);

        }else {
            //btn_xfwords.setVisibility(View.VISIBLE);
            btn_init.setVisibility(View.VISIBLE);
            btn_Patrolling.setVisibility(View.VISIBLE);
            btnWaypoint1.setVisibility(View.VISIBLE);
            btnWaypoint2.setVisibility(View.VISIBLE);
            btnWaypoint3.setVisibility(View.VISIBLE);
            btnWaypoint4.setVisibility(View.VISIBLE);
            btnWaypoint5.setVisibility(View.VISIBLE);
            mf_face.setVisibility(View.VISIBLE);
            mf_sp.setVisibility(View.VISIBLE);

        }


        return view;
    }

//    @Override
//    public void onAttach(Context context) {
//
//        MainActivity mainActivity = (MainActivity) context;
//        mainActivity.setHandler(mHandler);
//        super.onAttach(context);
//    }


    private boolean IsOne_Point() {
        if (Current_X == way1x && Current_Y == way1y && Current_Z == way1z && Current_W == way1w) {
            return true;
        }
        return false;
    }

    private void rename(final int i) {
        LayoutInflater factory = LayoutInflater.from(getContext());
        View textEntryView = factory.inflate(R.layout.myedit, null);
        final EditText mname_edit = (EditText) textEntryView
                .findViewById(R.id.rename_edit);
        new AlertDialog.Builder(getContext())
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("请输入导航点名称")
                .setView(textEntryView)
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                if (i == 0) {
                                    btnWaypoint1.setVisibility(View.GONE);
                                } else if (i == 1) {
                                    btnWaypoint2.setVisibility(View.GONE);
                                } else if (i == 2) {
                                    btnWaypoint3.setVisibility(View.GONE);
                                } else if (i == 3) {
                                    btnWaypoint4.setVisibility(View.GONE);
                                } else if (i == 4) {
                                    btnWaypoint5.setVisibility(View.GONE);
                                }

                            }

                        })
                .setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // dialog.
                                // TODO Auto-generated method stub
                                // Log.v(TAG, "你点击了确定");
                                String detailName = "/amcl_pose";
                                client.send("{\"op\":\"unsubscribe\",\"topic\":\"" + detailName + "\"}");

                                if (!mname_edit.getText().toString().equals("")) {
                                    newname = mname_edit.getText().toString();
                                }
                                // newName = mname_edit.getText().toString();
                                if (i == 0) {
                                    btnWaypoint1.setText(newname);
                                    editor.putString("Waypointname1", newname);
                                    editor.commit();
                                } else if (i == 1) {
                                    btnWaypoint2.setText(newname);
                                    editor.putString("Waypointname2", newname);
                                    editor.commit();
                                } else if (i == 2) {
                                    btnWaypoint3.setText(newname);
                                    editor.putString("Waypointname3", newname);
                                    editor.commit();
                                } else if (i == 3) {
                                    btnWaypoint4.setText(newname);
                                    editor.putString("Waypointname4", newname);
                                    editor.commit();
                                } else if (i == 4) {
                                    btnWaypoint5.setText(newname);
                                    editor.putString("Waypointname5", newname);
                                    editor.commit();
                                }

                                //Log.v(TAG, "$$$$$btn.setText(newName);");
                            }

                        }).show();


    }

    private void relongname(final int i) {
        show_point=true;
        LayoutInflater factory = LayoutInflater.from(getContext());
        View textEntryView = factory.inflate(R.layout.myedit, null);
        final EditText mname_edit = (EditText) textEntryView
                .findViewById(R.id.rename_edit);
        new AlertDialog.Builder(getContext())
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("请重新输入导航点名称")
                .setView(textEntryView)
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                String detailName = "/amcl_pose";
                                //client.send("{\"op\":\"unsubscribe\",\"topic\":\"" + detailName + "\"}");
                                show_point=false;
                            }

                        })
                .setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // dialog.
                                // TODO Auto-generated method stub
                                // Log.v(TAG, "你点击了确定");
                                String detailName = "/amcl_pose";
                                //client.send("{\"op\":\"unsubscribe\",\"topic\":\"" + detailName + "\"}");
                                show_point=false;
                                if (!mname_edit.getText().toString().equals("")) {
                                    newname = mname_edit.getText().toString();
                                }
                                // newName = mname_edit.getText().toString();
                                if (i == 0) {
                                    btnWaypoint1.setText(newname);
                                    editor.putString("Waypointname1", newname);
                                    editor.commit();

                                } else if (i == 1) {
                                    btnWaypoint2.setText(newname);
                                    editor.putString("Waypointname2", newname);
                                    editor.commit();
                                } else if (i == 2) {
                                    btnWaypoint3.setText(newname);
                                    editor.putString("Waypointname3", newname);
                                    editor.commit();
                                } else if (i == 3) {
                                    btnWaypoint4.setText(newname);
                                    editor.putString("Waypointname4", newname);
                                    editor.commit();
                                } else if (i == 4) {
                                    btnWaypoint5.setText(newname);
                                    editor.putString("Waypointname5", newname);
                                    editor.commit();
                                }


                                //Log.v(TAG, "$$$$$btn.setText(newName);");
                            }

                        }).show();


    }

    private void SetPorlloingTimes() {
        LayoutInflater factory = LayoutInflater.from(getContext());
        View textEntryView = factory.inflate(R.layout.myedit, null);
        TextView text = textEntryView.findViewById(R.id.rename_message);
        text.setText("请输入你需要巡逻的圈数");
        final EditText mname_edit = (EditText) textEntryView
                .findViewById(R.id.rename_edit);
        new AlertDialog.Builder(getContext())
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("请输入巡逻的圈数,请输入数字")
                .setView(textEntryView)
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                            }

                        })
                .setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            private String detailName = "/amcl_pose";

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {// dialog.
                                // TODO Auto-generated method stub
                                // Log.v(TAG, "你点击了确定");
                                try {
                                    mainActivity.Porlloing = Integer.parseInt(mname_edit.getText().toString());

                                } catch (NumberFormatException e) {
                                    Toast.makeText(getContext(), "请输入数字", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                mainActivity.Robot_Sport=true;
                                mainActivity.timesenough= false;
                                mainActivity.ispatrol = true;
                                mainActivity.Point_I = 1;
                                mainActivity.alreadyPorlloingtimes=1;


                                is_getonepoint_amcl = true;
                                client.send("{\"op\":\"subscribe\",\"topic\":\"" + detailName + "\"}");
                                boolean isonepoint = IsOne_Point();
                                mainActivity.setChioceItem(6);
                                if (isonepoint) {
                                    kqwSpeechCompound.speaking("我已经在第一个导航点,正在去往下一个导航点");
                                    mainActivity. Point_I = 2;
                                    String msg1 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way2x + ",\"y\":" + way2y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way2z + ",\"w\":" + way2w + "}}}}";
                                    client.send(msg1);
                                    //client.send("{\"op\":\"unsubscribe\",\"topic\":\"" + detailName + "\"}");
                                } else if (!isonepoint) {
                                    kqwSpeechCompound.speaking("我正在去往第一个导航点");
                                    String msg1 = " {\"op\":\"publish\",\"topic\":\"/move_base_simple/goal\",\"msg\":{\"header\":{\"seq\":0,\"stamp\":0,\"frame_id\":\"map\"},\"pose\":{\"position\":{\"x\":" + way1x + ",\"y\":" + way1y + ",\"z\":0},\"orientation\":{\"x\":0,\"y\":0,\"z\":" + way1z + ",\"w\":" + way1w + "}}}}";
                                    client.send(msg1);
                                    //client.send("{\"op\":\"unsubscribe\",\"topic\":\"" + detailName + "\"}");
                                }
                                //Log.v(TAG, "$$$$$btn.setText(newName);");
                            }

                        }).show();
        return ;
    }

    //选择初始化导航点的模态选择框
    // 单选提示框
    private AlertDialog alertDialog2;
    int indexdijige = 1;

    public void showSingleAlertDialog() {
        final String[] items = {Waypoint_name1, Waypoint_name2, Waypoint_name3, Waypoint_name4, Waypoint_name5};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setTitle("请到就近的导航点初始化，并且选择");
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int index) {
                Toast.makeText(getContext(), "你选择了" + items[index] + "作为导航初始化点，请按确认键", Toast.LENGTH_SHORT).show();
                indexdijige = index + 1;
            }
        });
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //TODO 业务逻辑代码
                if (indexdijige == 1) {

                    String msg1 = " {\"op\":\"publish\",\"topic\":\"/base/amcl_set_point\",\"msg\":{\"X\":"+way1x+",\"Y\":"+way1y+",\"Z\":0,\"OR_X\":0,\"OR_Y\":0,\"OR_Z\":"+way1z+",\"OR_W\":"+way1w+"}}";
                    client.send(msg1);
                } else if (indexdijige == 2) {
                    String msg1 = " {\"op\":\"publish\",\"topic\":\"/base/amcl_set_point\",\"msg\":{\"X\":"+way2x+",\"Y\":"+way2y+",\"Z\":0,\"OR_X\":0,\"OR_Y\":0,\"OR_Z\":"+way2z+",\"OR_W\":"+way2w+"}}";
                    client.send(msg1);

                } else if (indexdijige == 3) {
                    String msg1 = " {\"op\":\"publish\",\"topic\":\"/base/amcl_set_point\",\"msg\":{\"X\":"+way3x+",\"Y\":"+way3y+",\"Z\":0,\"OR_X\":0,\"OR_Y\":0,\"OR_Z\":"+way3z+",\"OR_W\":"+way3w+"}}";
                    client.send(msg1);

                } else if (indexdijige == 4) {
                    String msg1 = " {\"op\":\"publish\",\"topic\":\"/base/amcl_set_point\",\"msg\":{\"X\":"+way4x+",\"Y\":"+way4y+",\"Z\":0,\"OR_X\":0,\"OR_Y\":0,\"OR_Z\":"+way4z+",\"OR_W\":"+way4w+"}}";
                    client.send(msg1);

                } else if (indexdijige == 5) {
                    String msg1 = " {\"op\":\"publish\",\"topic\":\"/base/amcl_set_point\",\"msg\":{\"X\":"+way5x+",\"Y\":"+way5y+",\"Z\":0,\"OR_X\":0,\"OR_Y\":0,\"OR_Z\":"+way5z+",\"OR_W\":"+way5w+"}}";
                    client.send(msg1);
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

    Bitmap adjustPhotoRotation(Bitmap bitmap, int orientationDegree) {


        Matrix matrix = new Matrix();
        matrix.setRotate(orientationDegree, (float) bitmap.getWidth() / 2,
                (float) bitmap.getHeight() / 2);
        float targetX, targetY;
        if (orientationDegree == 90) {
            targetX = bitmap.getHeight();
            targetY = 0;
        } else {
            targetX = bitmap.getHeight();
            targetY = bitmap.getWidth();
        }


        final float[] values = new float[9];
        matrix.getValues(values);


        float x1 = values[Matrix.MTRANS_X];
        float y1 = values[Matrix.MTRANS_Y];


        matrix.postTranslate(targetX - x1, targetY - y1);


        Bitmap canvasBitmap = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getWidth(),
                Bitmap.Config.ARGB_8888);


        Paint paint = new Paint();
        Canvas canvas = new Canvas(canvasBitmap);
        canvas.drawBitmap(bitmap, matrix, paint);


        return canvasBitmap;
    }

int[] Current_ = {0,0};
    @Override
    public void update(List<Float> floatList) {
        Log.d("观察者模式", String.valueOf(floatList.get(0))+"hhhhhh"+floatList.get(1)+"tttttt"+floatList.get(2));
        try {
            Current_X = floatList.get(0);
            Current_Y = floatList.get(1);
            Current_Z = floatList.get(2);
            Current_W = floatList.get(3);
        } catch (IndexOutOfBoundsException e) {
            Log.d("jijijiji", "设置错误,请重新设置");
        }
        Current_[0] = (int)(Current_X*1000) ;
        Current_[1] = (int) (Current_Y*1000);
        Message message = new Message();
        message.what=CURRENT;
        message.obj =Current_;
        Log.d("Current", String.valueOf(Current_[0])+"YYYYYY"+Current_[1]);
        handler.sendMessage(message);
        if (show_point){
            if (!is_getonepoint_amcl) {
                if (i == 1) {
                    //WayPoint1 = new float[]{Current_X, Current_Y,Current_Z,Current_W};
                    way1x = Current_X;
                    way1y = Current_Y;
                    way1z = Current_Z;
                    way1w = Current_W;
                    Log.d("way1x", String.valueOf(way1x));
                    Log.d("way1y", String.valueOf(way1y));
                    Log.d("way1z", String.valueOf(way1z));
                    Log.d("way1w", String.valueOf(way1w));
                    editor.putFloat("way1x", way1x);
                    editor.putFloat("way1y", way1y);
                    editor.putFloat("way1z", way1z);
                    editor.putFloat("way1w", way1w);
                    editor.commit();
                } else if (i == 2) {
                    //WayPoint2 = new float[]{Current_X,Current_Y};
                    way2x = Current_X;
                    way2y = Current_Y;
                    way2z = Current_Z;
                    way2w = Current_W;
                    Log.d("way2x", String.valueOf(way2x));
                    Log.d("way2y", String.valueOf(way2y));
                    Log.d("way2z", String.valueOf(way2z));
                    Log.d("way2w", String.valueOf(way2w));
                    editor.putFloat("way2x", way2x);
                    editor.putFloat("way2y", way2y);
                    editor.putFloat("way2z", way2z);
                    editor.putFloat("way2w", way2w);
                    editor.commit();

                } else if (i == 3) {
                    way3x = Current_X;
                    way3y = Current_Y;
                    way3z = Current_Z;
                    way3w = Current_W;
                    editor.putFloat("way3x", way3x);
                    editor.putFloat("way3y", way3y);
                    editor.putFloat("way3z", way3z);
                    editor.putFloat("way3w", way3w);
                    editor.commit();
                } else if (i == 4) {
                    way4x = Current_X;
                    way4y = Current_Y;
                    way4z = Current_Z;
                    way4w = Current_W;
                    editor.putFloat("way4x", way4x);
                    editor.putFloat("way4y", way4y);
                    editor.putFloat("way4z", way4z);
                    editor.putFloat("way4w", way4w);
                    editor.commit();
                } else if (i == 5) {
                    way5x = Current_X;
                    way5y = Current_Y;
                    way5z = Current_Z;
                    way5w = Current_W;
                    editor.putFloat("way5x", way5x);
                    editor.putFloat("way5y", way5y);
                    editor.putFloat("way5z", way5z);
                    editor.putFloat("way5w", way5w);
                    editor.commit();
                }
            }
        }

    }

    @Override
    public void update_xfROSwords(String string) {


    }

    @Override
    public void update_PollingState(boolean b) {
        mainActivity.ispatrol = false;
    }

    @Override
    public void update_Map(Bitmap bitmap) {

    }

    public void do_hide(boolean b){
        if (b){
            //btn_xfwords.setVisibility(View.INVISIBLE);
            btn_init.setVisibility(View.INVISIBLE);
            btn_Patrolling.setVisibility(View.INVISIBLE);
            btnWaypoint1.setVisibility(View.INVISIBLE);
            btnWaypoint2.setVisibility(View.INVISIBLE);
            btnWaypoint3.setVisibility(View.INVISIBLE);
            btnWaypoint4.setVisibility(View.INVISIBLE);
            btnWaypoint5.setVisibility(View.INVISIBLE);
            mf_face.setVisibility(View.INVISIBLE);
            mf_sp.setVisibility(View.INVISIBLE);

        }else {
            //btn_xfwords.setVisibility(View.VISIBLE);
            btn_init.setVisibility(View.VISIBLE);
            btn_Patrolling.setVisibility(View.VISIBLE);
            btnWaypoint1.setVisibility(View.VISIBLE);
            btnWaypoint2.setVisibility(View.VISIBLE);
            btnWaypoint3.setVisibility(View.VISIBLE);
            btnWaypoint4.setVisibility(View.VISIBLE);
            btnWaypoint5.setVisibility(View.VISIBLE);
            mf_face.setVisibility(View.VISIBLE);
            mf_sp.setVisibility(View.VISIBLE);
        }
    }

}

