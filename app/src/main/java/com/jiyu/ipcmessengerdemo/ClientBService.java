package com.jiyu.ipcmessengerdemo;

import android.os.Message;
import android.util.Log;

public class ClientBService extends BaseClientService {
    String TAG="bugly-ClientBService";
    public ClientBService() {
    }

    @Override
    protected String setTag() {
        return "clientB";
    }

    @Override
    protected void handlerServerMsg(Message msg) {
        Log.e(TAG, "handlerMsg: "+ msg.what);
    }
}
