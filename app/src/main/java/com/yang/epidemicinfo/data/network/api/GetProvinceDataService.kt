package com.yang.epidemicinfo.data.network.api

import com.yang.epidemicinfo.data.model.ProvinceData
import retrofit2.Call
import retrofit2.http.GET


/**
 * @program: EpidemicInfo
 *
 * @description: 获取所有省数据
 *
 * @author: YangRT
 *
 * @create: 2020-03-09 20:28
 **/

interface GetProvinceDataService {

    @GET("/data/getListByCountryTypeService1")
    fun getProvinceData():Call<List<ProvinceData>>
}