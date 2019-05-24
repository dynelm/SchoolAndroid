package com.dynelm.robotdarttest.Util;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class UDPThread  extends  Thread{

    public String resultdata;
    @Override
    public void run() {
        super.run();
        // UDP服务器监听的端口
        Integer port = 8052;
        // 接收的字节大小，客户端发送的数据不能超过这个大小
        byte[] buf = new byte[1024];
        // 建立Socket连接
        DatagramSocket datagramSocket = null;
        if(datagramSocket == null)
        {
            try
            {
                datagramSocket = new DatagramSocket(null);
                datagramSocket.setReuseAddress(true);
                datagramSocket.bind(new InetSocketAddress(port));
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
                try
                {
                    while (true) {
                        // 准备接收数据
//                        datagramSocket.setSoTimeout(3000);
                        datagramSocket.receive(datagramPacket);
                        String result = new String(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength());
                        this.resultdata=result;
                        Log.d("sdsdsd",resultdata);

                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        try
        {

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getResultdata() {
        return resultdata;
    }

    public void setResultdata(String resultdata) {
        this.resultdata = resultdata;
    }
}
