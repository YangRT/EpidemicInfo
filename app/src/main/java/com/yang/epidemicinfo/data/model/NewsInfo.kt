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

data class NewsInfo(
    val results: List<NewsResult>,
    val success: Boolean
)

data class NewsResult(
    val infoSource: String,
    val provinceId: String,
    val provinceName: String,
    val pubDate: Long,
    val sourceUrl: String,
    val summary: String,
    val title: String
)