package com.yang.epidemicinfo.customview.mapview

import android.graphics.Path
import com.yang.epidemicinfo.R


/**
 * @program: KtPractice
 *
 * @description: 地图省份数据
 *
 * @author: YangRT
 *
 * @create: 2020-03-08 10:31
 **/

class MapData(val name: String = "",
                   val strokeColor: String = "",
                   val strokeWidth: String = "",
                   val pathData: Path,
                   var isSelect: Boolean = false,var allFillColor:Int = R.color.colorNumLow){
    var currentFillColor = 0
    var confirmedCount:Int = 0
    var currentConfirmedCount = 0
}