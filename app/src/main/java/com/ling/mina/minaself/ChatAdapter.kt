package com.ling.mina.minaself

import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ChatAdapter(chatList: List<TextData>) : RecyclerView.Adapter<BaseHolder>() {

    private var chatList:List<TextData> = chatList
    private val TYPE_TEXT_SEND = 0//文字发送方
    private val TYPE_TEXT_RECEIVER = 1//文字接收方

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        var view:View
        if (viewType == TYPE_TEXT_SEND)  {
            //发送方 文本
            view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.cell_chat_text_left, null)
        } else {
            //接收方 文本
            view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.cell_chat_text_right, null)
        }
        var baseHolder = BaseHolder(view)
        return baseHolder
    }

    override fun getItemViewType(position: Int): Int {
        return chatList.get(position).direct
    }
    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        chatList.get(position).message?.let {
            holder.setText(R.id.txt_content,it)
        }
    }
}
