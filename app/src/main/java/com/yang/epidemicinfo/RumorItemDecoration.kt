package com.yang.epidemicinfo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.yang.epidemicinfo.MyApplication.Companion.context


/**
 * @program: EpidemicInfo
 *
 * @description: 谣言 ItemDecoration
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 11:48
 **/

class RumorItemDecoration(val bitmapId:Int):RecyclerView.ItemDecoration() {

    private val bitmap:Bitmap = BitmapFactory.decodeResource(context.resources,bitmapId)
    private val mPaint = Paint()

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val childCount = parent.childCount
        for (i in 0 until childCount){
            val child = parent[i]
            val x = child.width/3f*2
            val y = child.top+1f
            c.drawBitmap(bitmap,x,y,mPaint)
        }
    }


}