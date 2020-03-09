package com.yang.epidemicinfo.data.model


/**
 * @program: EpidemicInfo
 *
 * @description: 百科知识 信息
 *
 * @author: YangRT
 *
 * @create: 2020-03-09 20:18
 **/
//  http://49.232.173.220:3001/data/getWikiList
data class WikiKnowledgeInfo(
    val result: List<KnowledgeResult>
)

data class KnowledgeResult(
    val description: String,
    val id: Int,
    val imgUrl: String,
    val linkUrl: String,
    val sort: Int,
    val title: String
)