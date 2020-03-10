package com.yang.epidemicinfo.mapview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*


import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat


/**
 * @program: KtPractice
 *
 * @description: 地图
 *
 * @author: YangRT
 *
 * @create: 2020-03-08 10:38
 **/

class MapView:View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 当前改变的宽高值
     */
    private var mViewWidth = 0
    private var mViewHeight = 0

    /**
     * 判断是否竖屏
     */
    private var widthSize = -1
    private var heightSize = -1


    /**
     * 当前 地图的宽高
     *
     */
    var mMapRectWidth = 0f
    var mMapRectHeight = 0f

    /**
     * 解析出来的 map 数据
     */
    var mapDataList = ArrayList<MapData>()

    /**
     * 绘制 Text
     */
    private var mPaintPath = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPaintText = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPaintTextTitle = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * 平铺缩放比例
     */
    private var scaleWidthValues = 0f
    private var scaleHeightValues = 0f


    /**
     * 进行初始化
     */
    private fun init(context: Context) {
        mPaintText.color = Color.BLACK
        mPaintText.style = Paint.Style.FILL_AND_STROKE
        mPaintText.textSize = 12f

        mPaintTextTitle.color = Color.RED
        mPaintTextTitle.textSize = 50f




    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mViewHeight = h
        mViewWidth = w
    }


    /**
     * 开始测量
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //测量模式
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        //测量大小
        widthSize = MeasureSpec.getSize(widthMeasureSpec)
        heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (mMapRectHeight != 0f && mMapRectWidth != 0f) {
            //拿来到显示比例
            scaleHeightValues = heightSize / mMapRectHeight
            scaleWidthValues = widthSize / mMapRectWidth
        }

        //xml 文件中宽高 wrap_content
        if (widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST) {
            //如果是横屏宽保留最大，高需要适配
            if (widthSize < heightSize && mMapRectHeight != 0f) {
                setMeasuredDimension(widthSize, (mMapRectHeight * scaleWidthValues).toInt())
            } else {
                setMeasuredDimension(widthSize, heightSize)
            }
        } else {
            setMeasuredDimension(widthSize, heightSize)
        }
    }

    /**
     * 开始绘制地图
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mapDataList.size > 0)
            drawMap(canvas)
    }

    /**
     * 绘制 Map 数据
     */
    @SuppressLint("Range")
    private fun drawMap(canvas: Canvas) {
        canvas.save()
        if (widthSize > heightSize) {
            canvas.scale(scaleWidthValues, scaleHeightValues)
        } else {
            canvas.scale(scaleWidthValues, scaleWidthValues)
        }

        mapDataList.forEach { data ->
            run {
                if (data.isSelect) {
                    drawPath(data, canvas, Color.YELLOW)
                } else {
                    drawPath(data, canvas, ContextCompat.getColor(context,data.allFillColor))
                }
            }
        }
        mapDataList.forEach {data ->
            run {
                var rectF = RectF()
                data.pathData.computeBounds(rectF, true)
                if (data.name=="内蒙古" || data.name=="重庆" || data.name == "海南" || data.name == "云南" || data.name=="陕西" || data.name=="黑龙江"){
                    canvas.drawText(
                        if (data.name.isEmpty()) "" else data.name,
                        rectF.centerX() - mPaintText.measureText(data.name) / 2,
                        rectF.centerY()+rectF.height()/4, mPaintText
                    )
                }else if (data.name == "甘肃"){
                    canvas.drawText(
                        if (data.name.isEmpty()) "" else data.name,
                        rectF.centerX()+rectF.width()/4.5f - mPaintText.measureText(data.name) / 2,
                        rectF.centerY()+rectF.height()/4, mPaintText
                    )
                }else if (data.name=="山东"){
                    canvas.drawText(
                        if (data.name.isEmpty()) "" else data.name,
                        rectF.centerX()-rectF.width()/6 - mPaintText.measureText(data.name) / 2,
                        rectF.centerY()+rectF.height()/5, mPaintText
                    )
                }else if (data.name == "河北"){
                    canvas.drawText(
                        if (data.name.isEmpty()) "" else data.name,
                        rectF.centerX()-rectF.width()/5 - mPaintText.measureText(data.name) / 2,
                        rectF.centerY()+rectF.height()/4, mPaintText
                    )
                }else if(data.name == "澳门"){
                    canvas.drawText(
                        if (data.name.isEmpty()) "" else data.name,
                        rectF.centerX()-rectF.width()*12 - mPaintText.measureText(data.name) / 2,
                        rectF.centerY()+rectF.height()*15, mPaintText
                    )
                }else if(data.name == "香港"){
                    canvas.drawText(
                        if (data.name.isEmpty()) "" else data.name,
                        rectF.centerX()+rectF.width()*2 - mPaintText.measureText(data.name) / 2,
                        rectF.centerY()+rectF.height()*2, mPaintText
                    )
                }else if(data.name == "台湾"){
                    canvas.drawText(
                        if (data.name.isEmpty()) "" else data.name,
                        rectF.centerX()+rectF.width()*0.7f - mPaintText.measureText(data.name) / 2,
                        rectF.centerY(), mPaintText
                    )
                }else if(data.name == "天津"){
                    canvas.drawText(
                        if (data.name.isEmpty()) "" else data.name,
                        rectF.centerX()+rectF.width() - mPaintText.measureText(data.name) / 2,
                        rectF.centerY()+rectF.height()/2, mPaintText
                    )
                }else if(data.name == "上海"){
                    canvas.drawText(
                        if (data.name.isEmpty()) "" else data.name,
                        rectF.centerX()+rectF.width()*0.7f - mPaintText.measureText(data.name) / 2,
                        rectF.centerY()+rectF.height()/2, mPaintText
                    )
                }else{
                    canvas.drawText(
                        if (data.name.isEmpty()) "" else data.name,
                        rectF.centerX() - mPaintText.measureText(data.name) / 2,
                        rectF.centerY(), mPaintText
                    )
                }
            }
        }
        canvas.restore()
        canvas.drawText("中国🇨🇳地图", widthSize / 2 - mPaintTextTitle.measureText("中国🇨🇳地图") / 2f, 100f, mPaintTextTitle)
    }

    /**
     * 开始绘制 Path
     */
    private fun drawPath(
        data: MapData,
        canvas: Canvas,
        magenta: Int
    ) {
        mPaintPath.color = magenta
        mPaintPath.style = Paint.Style.FILL
        mPaintPath.textSize = 30f
        mPaintPath.strokeWidth = 1f
        canvas.drawPath(data.pathData, mPaintPath)
        mPaintPath.style = Paint.Style.STROKE
        mPaintPath.color = Color.BLACK
        canvas.drawPath(data.pathData,mPaintPath)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> return true
            MotionEvent.ACTION_UP -> {
                handlerTouch(event.x, event.y)
            }
        }
        return super.onTouchEvent(event)
    }


    /**
     * 处理点击事件
     */
    private fun handlerTouch(x: Float, y: Float) {
        if (mapDataList.size == 0) return

        val xScale: Float
        val yScale: Float

        if (widthSize > heightSize) {
            xScale = scaleWidthValues
            yScale = scaleHeightValues
        } else {
            xScale = scaleWidthValues
            yScale = scaleWidthValues
        }
        mapDataList.forEach { data ->
            run {
                data.isSelect = false
                if (isTouchRegion(x / xScale, y / yScale, data.pathData)) {
                    data.isSelect = true
                    postInvalidate()
                }
            }
        }
    }


}

/**
 * 判断是否在点击区域内
 */
fun isTouchRegion(x: Float, y: Float, path: Path): Boolean {
    //创建一个矩形
    val rectF = RectF()
    //获取到当前省份的矩形边界
    path.computeBounds(rectF, true)
    //创建一个区域对象
    val region = Region()
    //将path对象放入到Region区域对象中
    region.setPath(path, Region(rectF.left.toInt(), rectF.top.toInt(), rectF.right.toInt(), rectF.bottom.toInt()))
    //返回是否这个区域包含传进来的坐标
    return region.contains(x.toInt(), y.toInt())
}


