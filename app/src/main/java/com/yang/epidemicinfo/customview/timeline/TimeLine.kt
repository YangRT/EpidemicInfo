package com.yang.epidemicinfo.customview.timeline

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.yang.epidemicinfo.R


/**
 * @program: EpidemicInfo
 *
 * @description: 时间轴
 *
 * @author: YangRT
 *
 * @create: 2020-03-12 20:32
 **/

class TimeLine(private val context: Context):RecyclerView.ItemDecoration() {

    private val mPaint =  Paint()

    private lateinit var bitmap:Bitmap

    private var itemView_leftinterval = 0
    private var itemView_topinterval = 0

    init {
        mPaint.textSize = 18f
        itemView_leftinterval = 200
        itemView_topinterval = 50
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.point)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(itemView_leftinterval, itemView_topinterval, 0, 0)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        for (i in 0..childCount){
            val child = parent[i]

        }
    }

}