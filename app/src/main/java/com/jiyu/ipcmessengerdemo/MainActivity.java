package com.jiyu.ipcmessengerdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.jiyu.ipcmessengerdemo.databinding.ActivityMainBinding;

/**
 * @author z
 */
public class MainActivity extends AppCompatActivity {
    String TAG="bugly-MainActivity";

    private ActivityMainBinding dataBinding;
    private Messenger messengerA;
    private Messenger messengerB;
    private Messenger messengerC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnStart=findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    private void start() {
        startService(new Intent(MainActivity.this,ServerCService.class));
        startService(new Intent(MainActivity.this,ClientAService.class));
        startService(new Intent(MainActivity.this,ClientBService.class));
        //当前界面作为客户端通过bind实现发送自定义消息
        bindService(new Intent(MainActivity.this,ClientAService.class),new MyTestServiceConnection(),BIND_IMPORTANT);
        bindService(new Intent(MainActivity.this,ClientBService.class),new MyTestServiceConnection(),BIND_IMPORTANT);
        bindService(new Intent(MainActivity.this,ServerCService.class),new MyTestServiceConnection(),BIND_IMPORTANT);
    }

    class MyTestServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            String tempName=name.getClassName().replace(name.getPackageName(),"");
            switch (tempName){
                case ".ClientAService":
                    messengerA=new Messenger(service);
                    break;
                case ".ClientBService":
                    messengerB=new Messenger(service);
                    break;
                case ".ServerCService":
                    messengerC=new Messenger(service);
                    break;
                default:
                    Log.e(TAG, "onServiceConnected: 未适配： "+tempName );
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

}