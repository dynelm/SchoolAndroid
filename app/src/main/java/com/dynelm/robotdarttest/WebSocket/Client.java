package com.dynelm.robotdarttest.WebSocket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class Client extends WebSocketClient {
    Handler handler;
    private WebClient.ConnectionStatusListener listener;


    public Client(URI serverURI, Handler handler) {
        super(serverURI);
        this.handler =handler;

    }
    Client(URI serverURI) {
        super(serverURI);
        listener = null;
    }


    public Client(URI serverUri, Draft draft, Handler handler) {
        super(serverUri, draft);
        this.handler=handler;
    }




    public static Client create(String URIString) {
        Client client = null;
        try {
            URI uri = new URI(URIString);
            client = new Client(uri);
        }
        catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        return client;
    }
    public Client(URI serverUri, Draft draft, Map<String, String> headers) {
        super(serverUri, draft, headers);
    }

    public void setListener(WebClient.ConnectionStatusListener listener){
        this.listener = listener;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        Log.i("websocket","opened connection");
        if (listener != null)
            listener.onConnect();

    }

    @Override
    public void onMessage(String s) {
        Log.i("websocket", "received: " + s);
        Message handlerMessage=new Message();
        handlerMessage.what=0x123;
        Bundle data=new Bundle();
        data.putString("message",s);
        handlerMessage.setData(data);
        handler.sendMessage(handlerMessage);

    }

    @Override
    public void onClose(int i, String s, boolean b) {
        Log.i("websocket","Connection closed by " );
        if (listener != null) {
            boolean normal = (b || (i == CloseFrame.NORMAL));
            listener.onDisconnect(normal, s, i);
        }
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
