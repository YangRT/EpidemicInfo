package com.yang.epidemicinfo.map

import android.graphics.Path


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
                   var isSelect: Boolean = false){
    var allFillColor:Int = 0
    var currentFillColor = 0
    var confirmedCount:Int = 0
    var currentConfirmedCount = 0
}