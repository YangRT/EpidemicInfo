package com.yang.epidemicinfo.data.model


/**
 * @program: EpidemicInfo
 *
 * @description: 世界信息
 *
 * @author: YangRT
 *
 * @create: 2020-03-09 18:01
 **/
// http://49.232.173.220:3001/data/getStatisticsService
data class WordInfo(
    val abroadRemark: String,
    val confirmedCount: Int,
    val confirmedIncr: Int,
    val countRemark: String,
    val createTime: Long,
    val curedCount: Int,
    val curedIncr: Int,
    val currentConfirmedCount: Int,
    val currentConfirmedIncr: Int,
    val dailyPic: String,
    val dailyPics: List<String>,
    val deadCount: Int,
    val deadIncr: Int,
    val deleted: Boolean,
    val foreignStatistics: ForeignStatistics,
    val foreignTrendChart: List<ForeignTrendChart>,
    val generalRemark: String,
    val hbFeiHbTrendChart: List<HbFeiHbTrendChart>,
    val id: Int,
    val imgUrl: String,
    val importantForeignTrendChart: List<ImportantForeignTrendChart>,
    val infectSource: String,
    val marquee: List<Marquee>,
    val modifyTime: Long,
    val note1: String,
    val note2: String,
    val note3: String,
    val passWay: String,
    val quanguoTrendChart: List<QuanguoTrendChart>,
    val remark1: String,
    val remark2: String,
    val remark3: String,
    val remark4: String,
    val remark5: String,
    val seriousCount: Int,
    val seriousIncr: Int,
    val summary: String,
    val suspectedCount: Int,
    val suspectedIncr: Int,
    val virus: String
)

data class ForeignStatistics(
    val confirmedCount: Int,
    val confirmedIncr: Int,
    val curedCount: Int,
    val curedIncr: Int,
    val currentConfirmedCount: Int,
    val currentConfirmedIncr: Int,
    val deadCount: Int,
    val deadIncr: Int,
    val suspectedCount: Int,
    val suspectedIncr: Int
)

data class ForeignTrendChart(
    val imgUrl: String,
    val title: String
)

data class HbFeiHbTrendChart(
    val imgUrl: String,
    val title: String
)

data class ImportantForeignTrendChart(
    val imgUrl: String,
    val title: String
)

data class Marquee(
    val id: Int,
    val marqueeContent: String,
    val marqueeLabel: String,
    val marqueeLink: String
)

data class QuanguoTrendChart(
    val imgUrl: String,
    val title: String
)