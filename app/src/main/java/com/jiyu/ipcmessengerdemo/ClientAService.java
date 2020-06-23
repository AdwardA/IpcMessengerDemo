package com.jiyu.ipcmessengerdemo;

import android.os.Message;
import android.util.Log;

/**
 * @author z
 */
public class ClientAService extends BaseClientService {
    String TAG = "bugly-ClientAService";


    public ClientAService() {
    }


    @Override
    protected String setTag() {
        return "clientA";
    }

    @Override
    protected void handlerServerMsg(Message msg) {
        Log.e(TAG, "handlerMsg: " + msg.what);
    }
}
