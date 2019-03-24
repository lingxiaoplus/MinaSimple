package com.ling.mina.minaself

class TextData {
    constructor(direct: Int,message: String?){
        this.direct = direct
        this.message = message
    }
    var direct: Int = 0
    var message: String? = null

    companion object {
        val SEND = 0
        val RECEIVER = 1
    }
}
