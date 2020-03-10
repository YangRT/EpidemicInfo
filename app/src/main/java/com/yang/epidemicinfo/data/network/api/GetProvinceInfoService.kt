package com.yang.epidemicinfo.data.network.api

import com.yang.epidemicinfo.data.model.ProvinceData
import com.yang.epidemicinfo.data.model.ProvinceInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @program: EpidemicInfo
 *
 * @description: 获取 各省份信息
 *
 * @author: YangRT
 *
 * @create: 2020-03-10 11:10
 **/

interface GetProvinceInfoService {

    @GET("/nCoV/api/area")
    fun getProvinceData(@Query("province")where:String): Call<ProvinceInfo>
}