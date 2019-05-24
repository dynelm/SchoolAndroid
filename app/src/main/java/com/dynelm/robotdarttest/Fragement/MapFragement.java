package com.dynelm.robotdarttest.Fragement;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dynelm.robotdarttest.Interface.Observe_Fragement;
import com.dynelm.robotdarttest.MainActivity;
import com.dynelm.robotdarttest.R;
import com.jilk.ros.rosbridge.ROSBridgeClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class MapFragement extends Fragment implements View.OnClickListener ,Observe_Fragement{
    private ImageView setmap;
    private ImageView savemap;
    private TextView Building_Map;
    private TextView map_text;
    private boolean isBuilding_map = false;
    FileOutputStream out;
    private Bitmap bitmap = null;
    private Button btnRosMap;
    private Button btn_map_start;
    private ROSBridgeClient client;
    private Handler handler =new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what==1){
                map_text.setBackground(new BitmapDrawable(getResources(), (Bitmap) message.obj));
            }
            return false;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setmapfg, container, false);
        setmap = (ImageView) view.findViewById(R.id.ima_setmap);
        savemap = (ImageView) view.findViewById(R.id.ima_savemap);
        Building_Map = (TextView) view.findViewById(R.id.goujianditu);
        map_text = (TextView) view.findViewById(R.id.map_text);
        btnRosMap = (Button) view.findViewById(R.id.RosMap);
        btn_map_start = (Button) view.findViewById(R.id.ROS_MAP_START);
        btn_map_start.setOnClickListener(MapFragement.this);
        setmap.setOnClickListener(MapFragement.this);
        savemap.setOnClickListener(MapFragement.this);
        btnRosMap.setOnClickListener(MapFragement.this);
        MainActivity mainActivity = (MainActivity) getActivity();
        client = mainActivity.client;
        mainActivity.addObserve(this);

        return view;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ima_setmap:
                String detailName = "/map";
                if (isBuilding_map) {
                    client.send("{\"op\":\"unsubscribe\",\"topic\":\"" + detailName + "\"}");
                    Building_Map.setText("点击构建地图");
                } else {
                    client.send("{\"op\":\"subscribe\",\"topic\":\"" + detailName + "\"}");
                    //client.send("{\"op\":\"subscribe\",\"topic\":\"" + detailName1 + "\"}");
                    Building_Map.setText("正在构建地图");
                }
                isBuilding_map = !isBuilding_map;
                break;
            case R.id.ima_savemap:
                if (bitmap == null) {
                    Toast.makeText(getActivity(), "对不起，您还未建立地图，请先构建地图", Toast.LENGTH_LONG).show();
                }

                savePic(bitmap);
                client.send("{\"op\":\"publish\",\"topic\":\"/ROS_START_SAVER\",\"msg\":{\"data\":\"mapsaver\"}}");
                break;
            case R.id.ROS_MAP_START:
                String msg1 = "{\"op\":\"publish\",\"topic\":\"/ROS_START\",\"msg\":{\"data\":\"gmapping\"}}";
                client.send(msg1);
                break;
            case R.id.RosMap:
                String msg2 = "{\"op\":\"publish\",\"topic\":\"/ROS_START_END\",\"msg\":{\"data\":\"endmap\"}}";
                client.send(msg2);
                break;
            default:
                break;
        }
    }

    public void savePic(Bitmap b) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) // 判断是否可以对SDcard进行操作
        {    // 获取SDCard指定目录下
            String sdCardDir = Environment.getExternalStorageDirectory() + "/CoolImage/";
            File dirFile = new File(sdCardDir);  //目录转化成文件夹
            if (!dirFile.exists()) {              //如果不存在，那就建立这个文件夹
                dirFile.mkdirs();
            }
            File file = new File(sdCardDir, "mapset" + ".jpg");// 在SDcard的目录下创建图片文,以当前时间为其命名

            try {
                out = new FileOutputStream(file);
                b.compress(Bitmap.CompressFormat.JPEG, 90, out);
                System.out.println("保存到sd指定目录文件夹下");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getActivity(), "保存已经至" + Environment.getExternalStorageDirectory() + "/CoolImage/" + "目录文件夹下", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void update(List<Float> floatList) {

    }

    @Override
    public void update_xfROSwords(String string) {

    }

    @Override
    public void update_PollingState(boolean b) {

    }

    @Override
    public void update_Map(Bitmap bitmap) {
        Message message = new Message();
        message.what=1;
        message.obj=bitmap;
        handler.sendMessage(message);


    }
}
