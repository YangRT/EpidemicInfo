package com.yang.epidemicinfo.data.db

import com.google.gson.reflect.TypeToken
import com.yang.epidemicinfo.customview.mapview.Map
import com.yang.epidemicinfo.data.model.NewsResult
import com.yang.epidemicinfo.data.model.RumorsResult
import com.yang.epidemicinfo.util.getDataFromJson
import com.yang.epidemicinfo.util.saveData
import com.yang.epidemicinfo.util.toJson
import java.lang.reflect.Type


/**
 * @program: EpidemicInfo
 *
 * @description: 新闻谣言 数据获取
 *
 * @author: YangRT
 *
 * @create: 2020-03-12 16:02
 **/

class InfoDao {

    fun cachedNewsInfo(data:List<NewsResult>?){
        if (data == null) return
        val strData = toJson(data)
        saveData(strData,"news")
    }

    fun getCachedNewsInfo():List<NewsResult>?{
        return getDataFromJson("news",getNewsClass())
    }

    fun cachedRumorInfo(data:List<RumorsResult>?){
        if (data == null) return
        val strData =  toJson(data)
        saveData(strData,"rumor")
    }

    fun getCachedRumorInfo():List<RumorsResult>?{
        return getDataFromJson("rumor",getRumorsClass())
    }

    private fun getNewsClass(): Type {
        return object : TypeToken<List<NewsResult>>(){}.type
    }

    private fun getRumorsClass(): Type {
        return object : TypeToken<List<NewsResult>>(){}.type
    }

}