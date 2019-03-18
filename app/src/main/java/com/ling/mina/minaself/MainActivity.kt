package com.ling.mina.minaself

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_client.setOnClickListener {
            bt_client.isEnabled = false
            Thread(Runnable {
                val client = MinaClient()
                val ret = client.connect("192.168.0.108", 2333)
                bt_client.post {
                    bt_client.isEnabled = true
                    if (ret) {
                        bt_client.text = "连接成功"
                        LogUtils.i("连接成功")
                    } else {
                        bt_client.text = "连接失败"
                        LogUtils.i("连接失败")
                    }
                }
            }).start()
        }
    }
}
