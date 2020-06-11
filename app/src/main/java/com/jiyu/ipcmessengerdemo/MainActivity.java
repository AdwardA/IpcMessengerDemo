package com.jiyu.ipcmessengerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
import android.view.View;
import android.widget.Button;

/**
 * @author z
 */
public class MainActivity extends AppCompatActivity {



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
    }
}