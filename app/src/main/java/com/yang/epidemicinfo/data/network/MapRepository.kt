package com.yang.epidemicinfo.data.network

import com.yang.epidemicinfo.data.db.MapDao
import com.yang.epidemicinfo.data.model.ProvinceData
import com.yang.epidemicinfo.data.model.ProvinceInfo
import com.yang.epidemicinfo.data.model.ProvinceResult
import com.yang.epidemicinfo.mapview.Map
import com.yang.epidemicinfo.util.getSaveTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @program: EpidemicInfo
 *
 * @description: 地图 repository
 *
 * @author: YangRT
 *
 * @create: 2020-03-10 10:23
 **/

class MapRepository(){

    private val mapDao = MapDao()

     suspend fun getCachedMap(where:String):Map{
        var map = mapDao.getCachedMapInfo(where)
        if (map == null) {
            map = requestMap(where)
            saveMap(where,map)
        }
        return map
     }

    private suspend fun requestMap(where: String) = withContext(Dispatchers.IO){mapDao.requestMapInfo(where)}

    private fun saveMap(where: String,map: Map){
        mapDao.cachedMapInfo(where, map)
    }

    suspend fun getCachedChinaData():List<ProvinceData>{
        var data = mapDao.getCachedChinaData("中国")
        if (data == null){
            data = mapDao.requestChinaData()
            mapDao.cachedChinaData("中国",data)
        }else{
            val saveTime = getSaveTime("中国")
            if (saveTime != 0L && saveTime - System.currentTimeMillis() > 6*3600*1000){
                data = mapDao.requestChinaData()
                mapDao.cachedChinaData("中国",data)
            }
        }
        return data
    }

    suspend fun refreshChinaData():List<ProvinceData>{
        val data = mapDao.requestChinaData()
        if (data.isNotEmpty() && getSaveTime("中国") < data[0].modifyTime){
            mapDao.cachedChinaData("中国",data)
        }
        return data
    }

    suspend fun getCachedProvinceInfo(where: String):List<ProvinceResult>{
        var info = mapDao.getCachedProvinceData(where)
        if (info == null){
            val response = mapDao.requestProvinceData(where)
            info = response.results
            mapDao.cachedProvinceData(where,info)
        }else{
            val saveTime = getSaveTime(where)
            var result:ProvinceInfo? = null
            if (saveTime != 0L && saveTime - System.currentTimeMillis() > 6*3600*1000){
                try {
                    result = mapDao.requestProvinceData(where)
                }catch (e:Exception){
                    e.printStackTrace()
                }
                if (result != null) {
                    info = result.results
                    mapDao.cachedProvinceData(where, info)
                }
            }
        }
        return info
    }

    suspend fun refreshProvinceInfo(where: String):List<ProvinceResult>{
        val response = EpidemicNetwork.getInstance().getProvinceInfo(where)
        val data = response.results
        if (data.isNotEmpty() && getSaveTime("中国") < data[0].updateTime){
            mapDao.cachedProvinceData("中国",data)
        }
        return data
    }

    companion object {
        private var mapRepository: MapRepository? = null
        fun getInstance(): MapRepository {
            if (mapRepository == null) {
                synchronized(EpidemicNetwork::class.java) {
                    if (mapRepository == null) {
                        mapRepository = MapRepository()
                    }
                }
            }
            return mapRepository!!
        }
    }

}