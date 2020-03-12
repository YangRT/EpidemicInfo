package com.yang.epidemicinfo.data.network.api

import com.yang.epidemicinfo.data.model.NewsInfo
import com.yang.epidemicinfo.data.model.RumorsInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @program: EpidemicInfo
 *
 * @description: 获取谣言 信息
 *
 * @author: YangRT
 *
 * @create: 2020-03-12 16:42
 **/

interface GetRumorDataService {

    @GET("/nCoV/api/rumors")
    fun getNewsData(@Query("page")page:Int, @Query("num")num:Int): Call<RumorsInfo>
}