package com.yang.epidemicinfo.data.model


/**
 * @program: EpidemicInfo
 *
 * @description: 新闻信息
 *
 * @author: YangRT
 *
 * @create: 2020-03-09 18:14
 **/
//http://www.dzyong.top:3005/yiqing/news  参数：pageNum pageSize
data class NewsInfo(
    val data: List<NewsData>,
    val msg: String
)

data class NewsData(
    val createTime: String,
    val id: Int,
    val infoSource: String,
    val modifyTime: String,
    val provinceName: String,
    val pubDate: String,
    val pubDateStr: String,
    val sourceUrl: String,
    val summary: String,
    val title: String
)