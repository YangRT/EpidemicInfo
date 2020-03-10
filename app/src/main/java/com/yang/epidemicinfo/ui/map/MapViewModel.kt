package com.yang.epidemicinfo.ui.map

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.epidemicinfo.data.model.PageStatus
import com.yang.epidemicinfo.data.model.ProvinceData
import com.yang.epidemicinfo.data.model.ProvinceResult
import com.yang.epidemicinfo.mapview.Map
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

    var chinaData = MutableLiveData<ObservableArrayList<ProvinceData>>()

    var provinceData = MutableLiveData<ObservableArrayList<ProvinceResult>>()


    fun getMapData(where:String){
        launch({
          
        },{

        })
    }

    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }

}