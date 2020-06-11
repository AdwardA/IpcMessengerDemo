package com.jiyu.ipcmessengerdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class ClientBService extends BaseClientService {
    String TAG="bugly-ClientBService";
    public ClientBService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected String setTag() {
        return "clientB";
    }

    @Override
    protected void handlerMsg(Message msg) {
        Log.e(TAG, "handlerMsg: "+ msg.what);
    }
}
