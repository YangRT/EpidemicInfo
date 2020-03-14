package com.yang.epidemicinfo.data.db

import com.google.gson.reflect.TypeToken
import com.yang.epidemicinfo.data.model.NewsResult
import com.yang.epidemicinfo.data.model.WorldInfo
import com.yang.epidemicinfo.util.getDataFromJson
import com.yang.epidemicinfo.util.saveData
import com.yang.epidemicinfo.util.toJson
import java.lang.reflect.Type


/**
 * @program: EpidemicInfo
 *
 * @description: 数据 dao
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 17:23
 **/

class DataDao {

    fun cachedWorldInfo(data:WorldInfo?){
        if (data == null) return
        val strData = toJson(data)
        saveData(strData,"world")
    }

    fun getCachedWorldInfo():WorldInfo?{
        return getDataFromJson("world",getWorldClass())
    }


    private fun getWorldClass(): Type {
        return object : TypeToken<List<WorldInfo>>(){}.type
    }


}