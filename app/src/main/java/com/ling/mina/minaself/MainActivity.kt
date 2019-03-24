package com.ling.mina.minaself

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mClient:MinaClient
    private lateinit var chatAdapter: ChatAdapter
    var chatList = arrayListOf<TextData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_client.setOnClickListener {
            bt_client.isEnabled = false
            Thread(Runnable {
                mClient = MinaClient()
                val ret = mClient.connect("192.168.0.100", 2333)
                bt_client.post {
                    bt_client.isEnabled = true
                    if (ret) {
                        bt_client.text = "连接成功"
                        //LogUtils.i("连接成功")
                    } else {
                        bt_client.text = "连接失败"
                        //LogUtils.i("连接失败")
                    }
                }

                mClient.setConnectCallback(object :MinaClient.ConnectCallback{
                    override fun onGetMessage(message: Any?) {
                        val msg = message.toString()
                        var textData = TextData(TextData.RECEIVER,msg)
                        chatList.add(textData)
                        bt_client.post( {
                            chatAdapter.notifyDataSetChanged()
                            recyclerView.smoothScrollToPosition(chatList.size)
                        })
                    }

                    override fun onConnected() {

                    }

                    override fun onError(cause: Throwable) {

                    }

                    override fun onSendSuccess() {
                        bt_client.post( {
                            chatAdapter.notifyDataSetChanged()
                            recyclerView.smoothScrollToPosition(chatList.size)
                        })
                    }

                })

            }).start()
        }

        bt_send.setOnClickListener({
            var message = editText.text.trim().toString()
            if (message.isEmpty()){
                Toast.makeText(applicationContext,"不能发送空消息", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mClient.let {
                it.sendText(message + "\n")
            }
            var textData = TextData(TextData.SEND,message)
            chatList.add(textData)
            chatAdapter.notifyDataSetChanged()
        })
        var manager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter(chatList)
        recyclerView.layoutManager = manager
        recyclerView.adapter = chatAdapter
    }

}
