package com.ling.mina.server

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException


class MainActivity : AppCompatActivity() {
    private lateinit var mServer: MinaServer
    private lateinit var chatAdapter: ChatAdapter
    var chatList = arrayListOf<TextData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_server.setOnClickListener { v ->
            showProgress()
            mServer = MinaServer()
            mServer
                    .connect(2333)
                    .setConnectCallback(object : MinaServer.ConnectCallback {
                        override fun onSendSuccess() {
                            bt_server.post({
                                chatAdapter.notifyDataSetChanged()
                                recyclerView.smoothScrollToPosition(chatList.size)
                            })
                        }

                        override fun onGetMessage(message: Any?) {
                            val msg = message.toString()
                            var textData = TextData(TextData.RECEIVER, msg)
                            chatList.add(textData)
                            bt_server.post({
                                chatAdapter.notifyDataSetChanged()
                                recyclerView.smoothScrollToPosition(chatList.size)
                            })

                        }

                        override fun onOpened() {
                            cancelDialog()
                            bt_server.text = "开启成功"
                        }
                        override fun onConnected() {

                        }

                        override fun onDisConnected() {

                        }

                        override fun onError(cause: Throwable) {
                            cancelDialog()
                            Toast.makeText(applicationContext, "服务器异常" + cause.toString(), Toast.LENGTH_SHORT).show()
                        }

                    })
        }
        bt_send.setOnClickListener {
            var message = editText.text.trim().toString()
            if (message.isEmpty()) {
                Toast.makeText(applicationContext, "不能发送空消息", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mServer.let {
                it.sendText(message + "\n")
            }
            editText.setText("")
            var textData = TextData(TextData.SEND, message)
            chatList.add(textData)

        }
        text_ip.text = "IP地址: " + getIPV4()
        var manager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter(chatList)
        recyclerView.layoutManager = manager
        recyclerView.adapter = chatAdapter

    }

    /**
     * 获取ipv4地址
     * @param context
     * @return
     */
    fun getIPV4(): String {
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.getHostAddress()
                    }
                }
            }
        } catch (ex: SocketException) {
            ex.printStackTrace()
        }

        return "null"
    }

    var progressDialog: ProgressDialog? = null
    fun showProgress() {
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage("连接中")
        progressDialog?.show()
    }

    fun cancelDialog() {
        progressDialog?.dismiss()
    }
}
