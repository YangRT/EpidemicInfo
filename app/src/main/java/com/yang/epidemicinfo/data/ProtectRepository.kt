package com.yang.epidemicinfo.data

import com.yang.epidemicinfo.data.db.DataDao
import com.yang.epidemicinfo.data.db.KnowledgeDao
import com.yang.epidemicinfo.data.model.ProtectiveKnowledgeInfo
import com.yang.epidemicinfo.data.model.WorldInfo
import com.yang.epidemicinfo.data.network.EpidemicNetwork
import com.yang.epidemicinfo.util.getSaveTime
import com.yang.epidemicinfo.util.saveTime


/**
 * @program: EpidemicInfo
 *
 * @description: 防护知识 repository
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 21:16
 **/

class ProtectRepository {

    private val dataDao =  KnowledgeDao()
    private val key = "protect"
    private var refreshing:Boolean = false

    private fun saveWorldData(data: List<ProtectiveKnowledgeInfo>){
        dataDao.cachedProtectInfo(data)
        saveTime(System.currentTimeMillis(),key)
    }


    fun getCachedData(): List<ProtectiveKnowledgeInfo>?{
        return dataDao.getCachedProtectInfo()
    }

    suspend fun refresh(): List<ProtectiveKnowledgeInfo>?{
        refreshing = true
        return load()
    }

    suspend fun requestData(): List<ProtectiveKnowledgeInfo>?{
        return load()
    }

    private suspend fun load(): List<ProtectiveKnowledgeInfo>?{
        val response = EpidemicNetwork.getInstance().getProjectInfo()
        saveWorldData(response)
        refreshing = false
        return response
    }

    fun isNeedToUpdate():Boolean{
        return System.currentTimeMillis() - getSaveTime(key) > 3600*1000*24
    }

}