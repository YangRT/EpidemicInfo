package com.yang.epidemicinfo.ui.map

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.epidemicinfo.R
import com.yang.epidemicinfo.data.model.BaseEpidemicInfo
import com.yang.epidemicinfo.data.model.PageStatus
import com.yang.epidemicinfo.data.model.ProvinceData
import com.yang.epidemicinfo.data.network.MapRepository
import com.yang.epidemicinfo.mapview.Map
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch


/**
 * @program: EpidemicInfo
 *
 * @description: 地图 map
 *
 * @author: YangRT
 *
 * @create: 2020-03-10 10:33
 **/

class MapViewModel:ViewModel(), LifecycleObserver {


    var status = MutableLiveData<PageStatus>()

    var refresh = MutableLiveData<Boolean>()

    var map = MutableLiveData<Map>()


    var areaData = MutableLiveData<ObservableArrayList<BaseEpidemicInfo>>()

    init {
        areaData.value = ObservableArrayList()

    }

    fun getChinaMapData(where:String){
        launch({
            val mapInfo = flow {
                val result = MapRepository.getInstance().getCachedMap(where)
                Log.e("MapViewModel","map success")
                emit(result)
            }
            val provinceInfo = flow{
                val result = MapRepository.getInstance().getCachedChinaData()
                Log.e("MapViewModel","provinceInfo success")
                emit(result)
            }
            provinceInfo.map {
                val result = ArrayList<BaseEpidemicInfo>()
                it.forEach { data ->
                    val item = BaseEpidemicInfo(BaseEpidemicInfo.PROVINCE)
                    item.area = data.provinceShortName
                    item.confirmedCount = data.confirmedCount
                    item.curedCount = data.curedCount
                    item.currentConfirmedCount = data.currentConfirmedCount
                    item.deadCount = data.deadCount
                    result.add(item)
                }
                result
            }.collect{
                areaData.value?.clear()
                areaData.value?.addAll(it)
                areaData.postValue(areaData.value)
            }
            provinceInfo.zip(mapInfo){ data, map ->
                val provinceDataMap = data.associateBy { it.provinceShortName }
                for (i in map.mapDataList.indices){
                    provinceDataMap[map.mapDataList[i].name]?.let {
                        map.mapDataList[i].confirmedCount = it.confirmedCount
                        map.mapDataList[i].currentConfirmedCount = it.currentConfirmedCount
                    }
                }
                map
            }.map {
                setMapColor(it)
                it
            }.collect {
                map.postValue(it)
            }
        },{
            Log.e("MapViewModel","error:${it.message}")
        })
    }

    fun getProvinceMapData(where: String){
        launch({
            val mapInfo = flow {
                val result = MapRepository.getInstance().getCachedMap(where)
                emit(result)
            }
            val cityData = flow{
                val result = MapRepository.getInstance().getCachedProvinceInfo(where)
                emit(result)
            }
            cityData.map {it ->
                val result = ArrayList<BaseEpidemicInfo>()
                it[0].cities.forEach { data ->
                    val item = BaseEpidemicInfo(BaseEpidemicInfo.OTHER)
                    item.area = data.cityName
                    item.confirmedCount = data.confirmedCount
                    item.curedCount = data.curedCount
                    item.currentConfirmedCount = data.currentConfirmedCount
                    item.deadCount = data.deadCount
                    result.add(item)
                }
                result
            }.collect{
                areaData.value?.clear()
                areaData.value?.addAll(it)
                areaData.postValue(areaData.value)
            }
            cityData.zip(mapInfo){ data, map ->
                val provinceDataMap = data[0].cities.associateBy { it.cityName }
                for (i in map.mapDataList.indices){
                    provinceDataMap[map.mapDataList[i].name]?.let {
                        map.mapDataList[i].confirmedCount = it.confirmedCount
                        map.mapDataList[i].currentConfirmedCount = it.currentConfirmedCount
                    }
                }
                map
            }.map {
                setMapColor(it)
                it
            }.collect {
                map.postValue(it)
            }
        },{
            Log.e("MapViewModel","error:${it.message}")
        })
    }

    private fun setMapColor(map:Map){
        for (i in map.mapDataList.indices){
            when(map.mapDataList[i].currentConfirmedCount){
                in 0..0 -> map.mapDataList[i].currentFillColor = R.color.colorNumZero
                in 1..9 -> map.mapDataList[i].currentFillColor = R.color.colorNumLow
                in 10..99 -> map.mapDataList[i].currentFillColor = R.color.colorNumMid
                in 100..499 -> map.mapDataList[i].currentFillColor = R.color.colorNumHeight
                in 500..999 -> map.mapDataList[i].currentFillColor = R.color.colorNumMany
                in 1000..10000 -> map.mapDataList[i].currentFillColor = R.color.colorNumMore
                else -> map.mapDataList[i].currentFillColor = R.color.colorNumMost
            }
        }
        for (i in map.mapDataList.indices){
            when(map.mapDataList[i].confirmedCount){
                in 0..0 -> map.mapDataList[i].allFillColor = R.color.colorNumZero
                in 1..9 -> map.mapDataList[i].allFillColor = R.color.colorNumLow
                in 10..99 -> map.mapDataList[i].allFillColor = R.color.colorNumMid
                in 100..499 -> map.mapDataList[i].allFillColor = R.color.colorNumHeight
                in 500..999 -> map.mapDataList[i].allFillColor = R.color.colorNumMany
                in 1000..10000 -> map.mapDataList[i].allFillColor = R.color.colorNumMore
                else -> map.mapDataList[i].allFillColor = R.color.colorNumMost
            }
        }
    }

    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }

}