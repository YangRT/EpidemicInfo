package com.yang.epidemicinfo.data.network.api

import com.yang.epidemicinfo.data.model.ProvinceInfo
import com.yang.epidemicinfo.data.model.WorldInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * @program: EpidemicInfo
 *
 * @description: 获取世界数据
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 17:36
 **/

interface GetWorldInfoService {

    @GET("/data/getStatisticsService")
    fun getWorldInfo(): Call<WorldInfo>
}