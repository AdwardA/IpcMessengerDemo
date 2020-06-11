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
import androidx.annotation.Nullable;

/**
 * @author z
 */
public abstract class BaseClientService extends Service {
    String TAG="bugly-BaseClientService";
     @SuppressLint("HandlerLeak")
     Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            handlerMsg(msg);
        }
    };
    private Messenger messenger;

    public BaseClientService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: " );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: " );
        ServiceConnection serviceConnection=new ServiceConnection(setTag(),handler);
        bindService(new Intent(this,ServerCService.class),serviceConnection,BIND_IMPORTANT);
        messenger = serviceConnection.getMessenger();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 标识当前客户端
     * @return
     */
    abstract protected String setTag();
    /**
     * 处理服务端发过来的msg
     * @param msg
     */
    abstract protected void handlerMsg(Message msg);

    protected boolean sendMsg(Message msg){
        try {
            messenger.send(msg);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }
}
