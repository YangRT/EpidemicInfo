package com.yang.epidemicinfo.data.db

import com.google.gson.reflect.TypeToken
import com.yang.epidemicinfo.data.model.*
import com.yang.epidemicinfo.util.getDataFromJson
import com.yang.epidemicinfo.util.saveData
import com.yang.epidemicinfo.util.toJson
import java.lang.reflect.Type


/**
 * @program: EpidemicInfo
 *
 * @description: 相关知识 dao
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 21:17
 **/

class KnowledgeDao {

    fun cachedProtectInfo(data:List<ProtectiveKnowledgeInfo>?){
        if (data == null) return
        val strData = toJson(data)
        saveData(strData,"protect")
    }

    fun getCachedProtectInfo():List<ProtectiveKnowledgeInfo>?{
        return getDataFromJson("protect",getProtectClass())
    }

    fun cachedWikiInfo(data:List<WikiItem>?){
        if (data == null) return
        val strData =  toJson(data)
        saveData(strData,"wiki")
    }

    fun getCachedWikiInfo():List<WikiItem>?{
        return getDataFromJson("wiki",getKnowledgeClass())
    }

    private fun getProtectClass(): Type {
        return object : TypeToken<List<ProtectiveKnowledgeInfo>>(){}.type
    }

    private fun getKnowledgeClass(): Type {
        return object : TypeToken<List<WikiItem>>(){}.type
    }

}