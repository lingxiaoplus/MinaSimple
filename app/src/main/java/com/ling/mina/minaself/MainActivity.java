package com.ling.mina.minaself;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mConnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConnBtn = findViewById(R.id.bt_client);

        mConnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Client client = new Client();
                        boolean ret = client.connect();
                        if (ret){
                            LogUtils.i("连接成功");
                        }else {
                            LogUtils.i("连接失败");
                        }
                    }
                }).start();
            }
        });
    }
}
