package com.yang.epidemicinfo.ui.rumor

import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.epidemicinfo.MyApplication
import com.yang.epidemicinfo.data.RumorRepository
import com.yang.epidemicinfo.data.model.PageResult
import com.yang.epidemicinfo.data.model.PageStatus
import com.yang.epidemicinfo.data.model.RumorsResult
import kotlinx.coroutines.launch


/**
 * @program: EpidemicInfo
 *
 * @description: 谣言 vm
 *
 * @author: YangRT
 *
 * @create: 2020-03-12 16:56
 **/

class RumorViewModel: ViewModel(), LifecycleObserver {

    var data = MutableLiveData<ObservableArrayList<RumorsResult>>()

    var refresh = MutableLiveData<Boolean>()

    lateinit var repository:RumorRepository

    var status = MutableLiveData<PageStatus>()

    var isRequesting = false

    var isFirst = true

    init {
        data.value = ObservableArrayList()
        status.value = PageStatus.LOADING
    }

    fun getCacheData(){
        launch({
            var result = repository.getCachedInfo()
            Log.e("getCacheData","result:"+result.data.toString())
            dealWithResult(result)
            if (repository.isNeedToUpdate()){
                requestData()
            }
        },{
            Log.e("BaseViewModel",it.message)
        })
    }

    private fun requestData(){
        launch({
            Log.e("launch",Thread.currentThread().name)
            var result = repository.requestData()
            isFirst = false
            dealWithResult(result)
        },{
            Log.e("BaseViewModel",it.message+"requestData")
            isFirst = false
            if (data.value?.size == 0){
                status.postValue(PageStatus.NETWORK_ERROR)
            }
            Toast.makeText(MyApplication.context,"网络错误！",Toast.LENGTH_SHORT).show()
        })
    }


    fun refresh(){
        if (status.value != PageStatus.LOADING){
            Log.e("BaseViewModel","refresh")
            refresh.value = true
            launch({
                var result = repository.refresh()
                refresh.value = false
                dealWithResult(result)
            },{
                refresh.value = false
                status.postValue(PageStatus.REFRESH_ERROR)
            })
        }

    }

    fun loadNextPage(){
        launch({
            if(!isFirst){
                var result = repository.loadNextPage()
                dealWithResult(result)
            }
        },{
            Log.e("BaseViewModel",it.message)
            status.postValue(PageStatus.LOAD_MORE_FAILED)
        })
    }

    private fun dealWithResult(result: PageResult<List<RumorsResult>>){
        Log.e("BaseViewModel","result："+result.data.toString()+" "+result.isFirst)
        if(result.isEmpty){
            if (!result.isFromCache && result.msg != null){
                Toast.makeText(MyApplication.context,result.msg, Toast.LENGTH_SHORT).show()
            }
            if(!result.isFromCache && data.value?.size == 0){
                status.postValue(PageStatus.EMPTY)
            }else if (!result.isFromCache && result.isFirst){
                status.postValue(PageStatus.EMPTY)
            }else if(!result.isFromCache){
                status.postValue(PageStatus.NO_MORE_DATA)
            }
        } else{
            if(result.isFirst){
                Log.e("BaseViewModel","clear")
                data.value?.clear()
            }
            result.data?.let { data.value?.addAll(it) }
            data.postValue(data.value)
            status.postValue(PageStatus.SHOW_CONTENT)

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