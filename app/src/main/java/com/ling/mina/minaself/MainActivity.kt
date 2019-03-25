package com.ling.mina.minaself

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mClient: MinaClient
    private lateinit var chatAdapter: ChatAdapter
    var chatList = arrayListOf<TextData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showProgress()
        var manager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter(chatList)
        recyclerView.layoutManager = manager
        recyclerView.adapter = chatAdapter
        mClient = MinaClient()
        mClient
                .connect("192.168.0.108", 2333)
                .setConnectCallback(object : MinaClient.ConnectCallback {
                    override fun onGetMessage(message: Any?) {
                        val msg = message.toString()
                        var textData = TextData(TextData.RECEIVER, msg)
                        chatList.add(textData)
                        chatAdapter.notifyDataSetChanged()
                        recyclerView.smoothScrollToPosition(chatList.size)
                    }
                    override fun onConnected() {
                        cancelDialog()
                    }

                    override fun onDisConnected() {
                        Toast.makeText(applicationContext, "断开连接成功", Toast.LENGTH_SHORT).show()
                    }
                    override fun onError(cause: Throwable) {
                        cancelDialog()
                        Toast.makeText(applicationContext, "服务器异常" + cause.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onSendSuccess() {
                        chatAdapter.notifyDataSetChanged()
                        recyclerView.smoothScrollToPosition(chatList.size)
                    }

                })

        bt_client.setOnClickListener {
            mClient.disConnect()
        }

        bt_send.setOnClickListener({
            var message = editText.text.trim().toString()
            if (message.isEmpty()) {
                Toast.makeText(applicationContext, "不能发送空消息", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mClient.let {
                it.sendText(message + "\n")
            }
            editText.setText("")
            var textData = TextData(TextData.SEND, message)
            chatList.add(textData)
            chatAdapter.notifyDataSetChanged()
        })

    }

    var progressDialog :ProgressDialog? = null
    fun showProgress(){
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage("连接中")
        progressDialog?.show()
    }
    fun cancelDialog(){
        progressDialog?.dismiss()
    }
}
