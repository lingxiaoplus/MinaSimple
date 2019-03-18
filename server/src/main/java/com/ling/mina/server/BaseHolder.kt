package com.ling.mina.server

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


/**
 * Created by 任梦林 on 2018/4/20.
 */

class BaseHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //SparseArray是android里为<Interger,Object>
    // 这样的Hashmap而专门写的class,目的是提高效率，其核心是折半查找函数
    private val mViews: SparseArray<View>

    init {
        mViews = SparseArray()
    }

    /**
     * 通过id获取view
     * @param resId
     */
    fun <T : View> getView(resId: Int): T {
        var view: View? = mViews.get(resId)
        if (view == null) {
            view = itemView.findViewById(resId)
            mViews.put(resId, view)
        }
        return view as T
    }

    fun setText(resId: Int, text: String) {
        val textView = getView<TextView>(resId)
        textView.text = text
    }

    fun setImgBitmap(resId: Int, bitmap: Bitmap) {
        val headView = getView<ImageView>(resId)
        headView.setImageBitmap(bitmap)
    }

    /**
     * 设置背景颜色和透明度
     * @param resId
     * @param color
     * @param alpha
     */
    fun setColorAndAlph(resId: Int, color: Int, alpha: Int) {
        val layout = getView<LinearLayout>(resId)
        layout.setBackgroundColor(color)
        layout.background.mutate().alpha = alpha
    }
}
