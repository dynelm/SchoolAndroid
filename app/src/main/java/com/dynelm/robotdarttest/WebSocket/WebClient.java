package com.dynelm.robotdarttest.WebSocket;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public abstract class WebClient {
    public abstract boolean connect();
    public abstract boolean connect(ConnectionStatusListener listener);
    public abstract void disconnect();
    public abstract void send(String json);

    public interface ConnectionStatusListener {
        public void onConnect();
        public void onDisconnect(boolean normal, String reason, int code);
        public void onError(Exception ex);
    }
}
