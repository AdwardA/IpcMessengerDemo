package com.jiyu.ipcmessengerdemo;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author z
 */
public abstract class BaseClientService extends Service {
    String TAG = "bugly-base "+setTag();
    /**
     * 处理服务端过来的消息
     */
    @SuppressLint("HandlerLeak")
    Handler handlerFromService = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.i(TAG, "handleMessage: receive "+msg.getData().get("content"));
            handlerServerMsg(msg);
        }
    };
    /**
     * 传递给服务端的messenger，让服务端发送消息过来
     */
    Messenger messengerAsClient=new Messenger(handlerFromService);
    /**
     * 服务端的messenger
     */
    private Messenger messengerService;


    /**
     * 处理客户端发过来的消息，转发给服务端
     */
    @SuppressLint("HandlerLeak")
    Handler handlerFromClient = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            sendMsg(msg);
        }
    };
    /**
     * 传递给客户端的messenger，让客户端发送消息过来
     */
    private Messenger messengerAsServer = new Messenger(handlerFromClient);

    public BaseClientService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messengerAsServer.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");
        ServiceConnection serviceConnection = new ServiceConnection(setTag(), messengerAsClient){
            @Override
            void onServiceConnected(ComponentName name, Messenger messenger) {
                messengerService=messenger;
            }
        };
        bindService(new Intent(this, ServerCService.class), serviceConnection, BIND_IMPORTANT);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 标识当前客户端
     *
     * @return
     */
    abstract protected String setTag();

    /**
     * 处理服务端发过来的msg
     *
     * @param msg
     */
    abstract protected void handlerServerMsg(Message msg);

    protected boolean sendMsg(Message msg) {
        if (messengerService==null){
            return false;
        }
        try {
            Log.i(TAG, "sendMsg:content  "+msg.getData().get("content"));
            msg.replyTo= messengerAsClient;
            messengerService.send(msg);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }
}
