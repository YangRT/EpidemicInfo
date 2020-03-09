package com.yang.epidemicinfo.data.model


/**
 * @program: EpidemicInfo
 *
 * @description: 时间线 数据
 *
 * @author: YangRT
 *
 * @create: 2020-03-09 17:44
 **/

data class TimeLineInfo(
    val adoptType: Int,
    val createTime: Long,
    val dataInfoOperator: String,
    val dataInfoState: Int,
    val dataInfoTime: Long,
    val entryWay: Int,
    val id: Int,
    val infoSource: String,
    val infoType: Int,
    val modifyTime: Long,
    val provinceId: String,
    val provinceName: String,
    val pubDate: Long,
    val pubDateStr: String,
    val sourceUrl: String,
    val summary: String,
    val title: String
)