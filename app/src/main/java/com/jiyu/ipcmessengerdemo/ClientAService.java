package com.jiyu.ipcmessengerdemo;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author z
 */
public class ClientAService extends BaseClientService {
    String TAG="bugly-ClientAService";
     @SuppressLint("HandlerLeak")
     Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            handlerMsg(msg);
        }
    };

    public ClientAService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    protected String setTag() {
        return "clientA";
    }

    @Override
    protected void handlerMsg(Message msg) {
        Log.e(TAG, "handlerMsg: "+ msg.what);
    }
}
