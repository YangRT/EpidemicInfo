package com.yang.epidemicinfo.data.model


/**
 * @program: EpidemicInfo
 *
 * @description: 谣言信息
 *
 * @author: YangRT
 *
 * @create: 2020-03-09 18:35
 **/
//https://lab.isaaclin.cn/nCoV/api/rumors 参数：num 返回条数
// rumorType  0：返回谣言（默认） 1：返回可信信息  2：返回尚未证实信息
data class RumorsInfo(
    val results: List<RumorsResult>,
    val success: Boolean
)

data class RumorsResult(
    val _id: Int,
    val body: String,
    val mainSummary: String,
    val rumorType: Int,
    val sourceUrl: String,
    val title: String
)