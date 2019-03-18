package com.ling.mina.server

import org.apache.mina.core.session.AbstractIoSession
import org.apache.mina.core.session.IoSession

class TextResponse {
    var text :String? = null

    fun setText(str:String) :TextResponse{
        text = str
        return this
    }

    fun sendTo(ioSession: IoSession) :String{
        return ""
    }
}