package com.dynelm.robotdarttest.Fragement;

import android.content.Context;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dynelm.robotdarttest.BasicActivity;
import com.dynelm.robotdarttest.MainActivity;
import com.dynelm.robotdarttest.R;
import com.jilk.ros.rosbridge.ROSBridgeClient;

import java.util.ArrayList;
import java.util.HashMap;
import com.dynelm.robotdarttest.InstanceExe.instance;
import com.tx.printlib.Const;
import com.tx.printlib.UsbPrinter;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class MainFragement extends Fragment  {
    private ROSBridgeClient client;
    private Button button;
    ArrayList<HashMap<String, Object>> chatList = null;
    String[] from = {"image", "text","color"};
    int[] to = {R.id.chatlist_image_me, R.id.chatlist_text_me, R.id.chatlist_image_other, R.id.chatlist_text_other};
    int[] layout = {R.layout.mychatlistview, R.layout.robotchatlistview};
    public final static int OTHER = 1;
    public final static int ME = 0;

    protected ListView chatListView = null;
    protected Button chatSendButton = null;
    protected EditText editText = null;
    public String result = null;
    private ImageView img_face;
    private MainActivity mainActivity;
    TextView textView;
    private boolean Gray =false ;



    public MyChatAdapter adapter = null;
    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (msg.obj.toString().contains(".")){
                        Gray = true;
                        addTextToList((String) msg.obj,ME,Color.rgb(128,128,128));
                    }else {
                        Gray=false;
                        addTextToList((String) msg.obj,ME,Color.WHITE);
                    }

                    //相当于更新ListView

                    adapter.notifyDataSetChanged();
                    chatListView.setSelection(chatList.size() - 1);
                    break;
                case 2:

                    DO((String) msg.obj);
                    break;
            }
        }
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.new_main_fragement, container,false);

        chatList = new ArrayList<HashMap<String, Object>>();
        addTextToList("主人您好\n  ^_^", OTHER,Color.WHITE);
        addTextToList("请问有什么吩咐", OTHER,Color.WHITE);
        //TTS("我已经巡逻结束了，正在回到巡逻起始点哈");

        //editText.setInputType(InputType.TYPE_NULL);
        chatListView =  mview.findViewById(R.id.List_new_view);
        adapter = new MyChatAdapter(getActivity(), chatList, layout, from, to);
        button = mview.findViewById(R.id.Btn_ADD_Test_New);
        button.setVisibility(View.INVISIBLE);

        chatListView.setAdapter(adapter);
//        BasicActivity basicActivity = (BasicActivity) getActivity();
//        Bundle bundle = getArguments();//从activity传过来的Bundle
//        if(bundle!=null){
//            addTextToList(bundle.getString("finalRersult"),ME);
//            //相当于更新ListView
//            adapter.notifyDataSetChanged();
//            chatListView.setSelection(chatList.size() - 1);
//        }
         mainActivity= (MainActivity) getActivity();
        client = mainActivity.client;
        //Log.d("color", String.valueOf(Color.rgb(128,128,128)));









        return mview;
    }

    public void addTextToList(String text, int who,int color) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("person", who);
        map.put("image", who == ME ? R.drawable.nhuida : R.drawable.nrobot);
        map.put("text", text);
        map.put("color",color);
        chatList.add(map);

    }

    //适配器
    private class MyChatAdapter extends BaseAdapter {
        Context context = null;
        ArrayList<HashMap<String, Object>> chatList = null;
        int[] layout;
        String[] from;
        int[] to;



        public MyChatAdapter(Context context,
                             ArrayList<HashMap<String, Object>> chatList, int[] layout,
                             String[] from, int[] to) {
            super();
            this.context = context;
            this.chatList = chatList;
            this.layout = layout;
            this.from = from;
            this.to = to;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return chatList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        class ViewHolder {
            public ImageView imageView = null;
            public TextView textView = null;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder = null;
            int who = (Integer) chatList.get(position).get("person");

            convertView = LayoutInflater.from(context).inflate(
                    layout[who == ME ? 0 : 1], null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(to[who * 2 + 0]);
            holder.textView = (TextView) convertView.findViewById(to[who * 2 + 1]);


            System.out.println(holder);
            System.out.println("WHYWHYWHYWHYW");
            System.out.println(holder.imageView);
            holder.imageView.setBackgroundResource((Integer) chatList.get(position).get(from[0]));
            holder.textView.setText(chatList.get(position).get(from[1]).toString());
            holder.textView.setTextColor((Integer) chatList.get(position).get(from[2]));
//            if (!Gray){
//                holder.textView.setTextColor(Color.rgb(128,128,128));
//            }else {
//                holder.textView.setTextColor(Color.WHITE);
//            }
            return convertView;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.setHandler(mHandler);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onHiddenChanged(boolean hidden) {

    }
    private void DO(final String string){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTextToList(string, OTHER,Color.rgb(125,254,0));
                chatListView.setSelection(chatList.size() - 1);
            }
        });
        button.performClick();
    }
}





