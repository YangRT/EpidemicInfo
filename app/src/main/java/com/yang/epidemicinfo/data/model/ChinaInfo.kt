package com.yang.epidemicinfo.data.model


/**
 * @program: EpidemicInfo
 *
 * @description: 中国信息
 *
 * @author: YangRT
 *
 * @create: 2020-03-09 18:06
 **/
// https://lab.isaaclin.cn//nCoV/api/overall
data class ChinaInfo(
    val results: List<ChinaResult>,
    val success: Boolean
)

data class ChinaResult(
    val abroadRemark: String,
    val confirmedCount: Int,
    val confirmedIncr: Int,
    val curedCount: Int,
    val curedIncr: Int,
    val currentConfirmedCount: Int,
    val currentConfirmedIncr: Int,
    val deadCount: Int,
    val deadIncr: Int,
    val generalRemark: String,
    val note1: String,
    val note2: String,
    val note3: String,
    val remark1: String,
    val remark2: String,
    val remark3: String,
    val remark4: String,
    val remark5: String,
    val seriousCount: Int,
    val seriousIncr: Int,
    val suspectedCount: Int,
    val suspectedIncr: Int,
    val updateTime: Long
)