package com.yang.epidemicinfo.data.model


/**
 * @program: EpidemicInfo
 *
 * @description:
 *
 * @author: YangRT
 *
 * @create: 2020-03-09 18:41
 **/
// http://49.232.173.220:3001/data/getIndexRecommendList 返回数组
data class ProtectiveKnowledgeInfo(
    val contentType: Int,
    val createTime: Long,
    val id: Int,
    val imgUrl: String,
    val linkUrl: String,
    val modifyTime: Long,
    val `operator`: String,
    val recordStatus: Int,
    val sort: Int,
    val title: String
)