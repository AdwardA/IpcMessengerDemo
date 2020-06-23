package com.jiyu.ipcmessengerdemo;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * create by z on 20/6/10
 */
public abstract class ServiceConnection implements android.content.ServiceConnection {
    String TAG="bugly-ServiceConnection";
    private String tag;
    private Messenger messengerService;
    private Messenger messengerClient;

    public ServiceConnection(String tag, Messenger messengerClient) {
        this.tag=tag;
        this.messengerClient=messengerClient;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        //根据服务端过来的IBinder建立Messenger，用于给服务端发送信息
        messengerService = new Messenger(service);
        //向服务端发送消息
        Message msg =new Message();
        msg.what= ServerCService.MsgWhatEnum.CONNECT.value();
        Bundle bundle=new Bundle();
        bundle.putString("client",tag);
        msg.setData(bundle);
        msg.replyTo=messengerClient;
        try {
            messengerService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        onServiceConnected(name, messengerService);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.e(TAG, "onServiceDisconnected: "+name );

    }

    abstract void onServiceConnected(ComponentName name, Messenger messengerService);
}
