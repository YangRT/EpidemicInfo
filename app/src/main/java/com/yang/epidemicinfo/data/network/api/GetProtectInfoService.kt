package com.yang.epidemicinfo.data.network.api

import com.yang.epidemicinfo.data.model.ProtectiveKnowledgeInfo
import com.yang.epidemicinfo.data.model.WorldInfo
import retrofit2.Call
import retrofit2.http.GET


/**
 * @program: EpidemicInfo
 *
 * @description: 获取 防护数据
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 17:56
 **/

interface GetProtectInfoService {
    
    @GET("/data/getIndexRecommendList")
    fun getProtectInfo(): Call<List<ProtectiveKnowledgeInfo>>
}