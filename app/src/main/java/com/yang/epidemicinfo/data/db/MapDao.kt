package com.yang.epidemicinfo.data.db

import com.google.gson.reflect.TypeToken
import com.yang.epidemicinfo.MyApplication
import com.yang.epidemicinfo.R
import com.yang.epidemicinfo.data.model.ProvinceData
import com.yang.epidemicinfo.data.model.ProvinceResult
import com.yang.epidemicinfo.data.network.EpidemicNetwork
import com.yang.epidemicinfo.mapview.Dom2xml
import com.yang.epidemicinfo.mapview.Map
import com.yang.epidemicinfo.util.*
import java.lang.reflect.Type

/**
 * @program: EpidemicInfo
 *
 * @description: map 数据获取
 *
 * @author: YangRT
 *
 * @create: 2020-03-10 09:41
 **/

class MapDao {

    fun cachedMapInfo(where:String,map:Map?){
        if (map == null) return
        val strData = toJson(map)
        saveData(strData,where)
    }

    fun cachedChinaData(where: String,data:List<ProvinceData>?){
        if (data == null) return
        if (data.isEmpty()) return
        val strData = toJson(data)
        saveData(strData,where)
        saveTime(data[0].modifyTime,where)
    }

    fun cachedProvinceData(where:String,data: List<ProvinceResult>?){
        if (data == null) return
        if (data.isEmpty()) return
        val strData = toJson(data)
        saveData(strData,where)
        saveTime(data[0].updateTime,where)
    }

    fun getCachedMapInfo(where: String):Map?{
        return getDataFromJson<Map>(where, getMapClass())
    }

    fun getCachedChinaData(where: String):List<ProvinceData>?{
        return getDataFromJson(where,getChinaClass())
    }

    fun getCachedProvinceData(where: String):List<ProvinceResult>?{
        return getDataFromJson(where,getProvinceClass())
    }

    fun requestMapInfo(where:String):Map{
        val map = Map()
        val dom2xml = Dom2xml()
        val mapLocation = getMapLocation(where)
        val inputStream = MyApplication.context.resources.openRawResource(mapLocation)
        map.mapDataList = dom2xml.dom2xml(inputStream)
        map.mapHeight = dom2xml.MAP_RECTF.height()
        map.mapWidth = dom2xml.MAP_RECTF.width()
        return map
    }

    suspend fun requestChinaData() = EpidemicNetwork.getInstance().getProvinceData()

    suspend fun requestProvinceData(where: String) = EpidemicNetwork.getInstance().getProvinceInfo(where)

    private fun getMapClass():Type{
       return object : TypeToken<Map>(){}.type
    }

    private fun getChinaClass():Type{
        return object : TypeToken<List<ProvinceData>>(){}.type
    }

    private fun getProvinceClass():Type{
        return object : TypeToken<List<ProvinceResult>>(){}.type
    }

}