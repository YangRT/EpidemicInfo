package com.yang.epidemicinfo.data.network.api

import com.yang.epidemicinfo.data.model.ProtectiveKnowledgeInfo
import com.yang.epidemicinfo.data.model.WikiKnowledgeInfo
import retrofit2.Call
import retrofit2.http.GET


/**
 * @program: EpidemicInfo
 *
 * @description: 获取维基知识
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 21:26
 **/

interface GetWikiInfoService {

    @GET("/data/getWikiList")
    fun getWikiInfo(): Call<WikiKnowledgeInfo>
}