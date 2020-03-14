package com.yang.epidemicinfo.data

import com.yang.epidemicinfo.data.db.DataDao
import com.yang.epidemicinfo.data.model.WorldInfo
import com.yang.epidemicinfo.data.network.EpidemicNetwork
import com.yang.epidemicinfo.util.getSaveTime
import com.yang.epidemicinfo.util.saveTime


/**
 * @program: EpidemicInfo
 *
 * @description: 世界信息汇总
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 17:31
 **/

class WorldRepository {

    private val dataDao =  DataDao()
    private val key = "world"
    private var refreshing:Boolean = false

    private fun saveWorldData(data:WorldInfo){
        dataDao.cachedWorldInfo(data)
        saveTime(data.createTime,key)
    }


    fun getCachedData():WorldInfo?{
        return dataDao.getCachedWorldInfo()
    }

    suspend fun refresh():WorldInfo?{
        refreshing = true
        return load()
    }

    suspend fun requestData():WorldInfo?{
        return load()
    }

    private suspend fun load():WorldInfo?{
        val response = EpidemicNetwork.getInstance().getWorldInfo()
        if (refreshing){
            if (response.createTime > getSaveTime(key)){
                saveWorldData(response)
            }
        }else{
            saveWorldData(response)
        }
        refreshing = false
        return response
    }

    private fun isNeedToUpdate():Boolean{
        return System.currentTimeMillis() - getSaveTime(key) > 3600*1000*12
    }
}