package com.yang.epidemicinfo.data.network.api

import com.yang.epidemicinfo.data.model.NewsInfo
import com.yang.epidemicinfo.data.model.ProvinceData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @program: EpidemicInfo
 *
 * @description: 获取新闻信息
 *
 * @author: YangRT
 *
 * @create: 2020-03-12 16:33
 **/

interface GetNewsDataService {
    @GET("/nCoV/api/news")
    fun getNewsData(@Query("page")page:Int,@Query("num")num:Int): Call<NewsInfo>
}