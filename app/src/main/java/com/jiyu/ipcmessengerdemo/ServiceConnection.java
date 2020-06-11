package com.jiyu.ipcmessengerdemo;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/**
 * create by z on 20/6/10
 */
public class ServiceConnection implements android.content.ServiceConnection {

    private Handler handler;
    private String tag;
    private Messenger messenger;

    public ServiceConnection(String tag, Handler handler) {
        this.tag=tag;
        this.handler=handler;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        //根据服务端过来的IBinder建立Messenger，用于给服务端发送信息
        messenger = new Messenger(service);
        //创建客户端的Messenger，用于服务端回复客户端的消息
        Messenger messengerReply=new Messenger(handler);
        //像服务端发送消息
        Message msg =new Message();
        msg.what= ServerCService.MsgWhatEnum.CONNECT.value();
        Bundle bundle=new Bundle();
        bundle.putString("client",tag);
        msg.setData(bundle);
        msg.replyTo=messengerReply;
        try {
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    public Handler getHandler() {
        return handler;
    }

    public Messenger getMessenger() {
        return messenger;
    }
}
