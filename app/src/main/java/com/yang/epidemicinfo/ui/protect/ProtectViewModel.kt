package com.yang.epidemicinfo.ui.protect

import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.epidemicinfo.MyApplication
import com.yang.epidemicinfo.data.ProtectRepository
import com.yang.epidemicinfo.data.model.PageStatus
import com.yang.epidemicinfo.data.model.ProtectiveKnowledgeInfo
import kotlinx.coroutines.launch


/**
 * @program: EpidemicInfo
 *
 * @description: 防护 vm
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 22:48
 **/

class ProtectViewModel: ViewModel(), LifecycleObserver {

    var protectData = MutableLiveData<ObservableArrayList<ProtectiveKnowledgeInfo>>()

    private var repository: ProtectRepository = ProtectRepository()

    var status = MutableLiveData<PageStatus>()

    init {
        protectData.value = ObservableArrayList()
        status.value = PageStatus.LOADING
    }

    fun getProtectData(){
        launch({
            val result = repository.getCachedData()
            result?.let {
                protectData.value?.clear()
                protectData.value?.addAll(result)
                protectData.postValue(protectData.value)
                status.postValue(PageStatus.SHOW_CONTENT)
            }
            if (repository.isNeedToUpdate()){
                requestWikiData()
            }
        },{
            Log.e("MapViewModel","error:${it.message}")
            status.postValue(PageStatus.NETWORK_ERROR)
        })
    }

    private fun requestWikiData(){
        launch({
            val result = repository.requestData()
            result?.let {
                protectData.value?.clear()
                protectData.value?.addAll(result)
                protectData.postValue(protectData.value)
                status.postValue(PageStatus.SHOW_CONTENT)
            }
        },{
            Log.e("MapViewModel","error:${it.message}")
            status.postValue(PageStatus.NETWORK_ERROR)
        })
    }

    fun refresh(){
        launch({
            val result = repository.refresh()
            result?.let {
                protectData.value?.clear()
                protectData.value?.addAll(result)
                protectData.postValue(protectData.value)
                status.postValue(PageStatus.SHOW_CONTENT)
                Toast.makeText(MyApplication.context,"刷新成功！", Toast.LENGTH_SHORT).show()

            }
        },{
            Log.e("MapViewModel","error:${it.message}")
            status.postValue(PageStatus.REFRESH_ERROR)
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