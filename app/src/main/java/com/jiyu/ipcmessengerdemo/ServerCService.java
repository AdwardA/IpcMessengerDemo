package com.jiyu.ipcmessengerdemo;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author z
 */
public class ServerCService extends Service {
    String TAG="bugly-ServerCService";
    public enum MsgWhatEnum{
        CONNECT(1),ACK(2),DISCONNECT(3);

        private int value=-1;
        MsgWhatEnum(int value){
            this.value=value;
        }
        public int value(){
            return value;
        }

    }

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            handlerMsg(msg);
        }
    };

    /**
     * 给当前服务端发送消息的信使
     */
    Messenger serverMessenger =new Messenger(handler);
    /**
     * 用于向客户端发送消息的信使
     */
    Map<String,Messenger> clientMessengers=new HashMap<>();

    public ServerCService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: " );
        return serverMessenger.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void handlerMsg(Message msg) {
        String clientTag = msg.getData().getString("client");
        Log.d(TAG, "handlerMsg: receive msg from: "+clientTag);
        //回复客户端
        try {
            if (msg.replyTo==null){
                return;
            }
            if (!"".equals(clientTag) && clientTag!=null){
                clientMessengers.put(clientTag,msg.replyTo);
            }
            Message msgReply =new Message();
            msgReply.what=MsgWhatEnum.ACK.value();
            msg.replyTo.send(msgReply);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
