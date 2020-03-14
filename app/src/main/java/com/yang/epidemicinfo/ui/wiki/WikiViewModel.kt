package com.yang.epidemicinfo.ui.wiki

import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.epidemicinfo.MyApplication
import com.yang.epidemicinfo.data.WikiRepository
import com.yang.epidemicinfo.data.model.PageStatus
import com.yang.epidemicinfo.data.model.WikiItem
import kotlinx.coroutines.launch


/**
 * @program: EpidemicInfo
 *
 * @description: 维基知识 vm
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 22:12
 **/

class WikiViewModel: ViewModel(), LifecycleObserver {

    var wikiData = MutableLiveData<ObservableArrayList<WikiItem>>()

    private var repository:WikiRepository = WikiRepository()

    var status = MutableLiveData<PageStatus>()

    init {
       wikiData.value = ObservableArrayList()
        status.value = PageStatus.LOADING
    }

    fun getWikiData(){
        launch({
            val result = repository.getCachedData()
            result?.let {
                wikiData.value?.clear()
                wikiData.value?.addAll(result)
                wikiData.postValue(wikiData.value)
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
                wikiData.value?.clear()
                wikiData.value?.addAll(result)
                wikiData.postValue(wikiData.value)
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
                wikiData.value?.clear()
                wikiData.value?.addAll(result)
                wikiData.postValue(wikiData.value)
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