package com.yang.epidemicinfo.data

import android.util.Log
import com.yang.epidemicinfo.data.db.InfoDao
import com.yang.epidemicinfo.data.model.NewsResult
import com.yang.epidemicinfo.data.model.PageResult
import com.yang.epidemicinfo.data.model.RumorsInfo
import com.yang.epidemicinfo.data.model.RumorsResult
import com.yang.epidemicinfo.data.network.EpidemicNetwork
import com.yang.epidemicinfo.util.getSaveTime
import com.yang.epidemicinfo.util.saveTime


/**
 * @program: EpidemicInfo
 *
 * @description: 谣言 repository
 *
 * @author: YangRT
 *
 * @create: 2020-03-12 16:47
 **/

class RumorRepository {

    private var pageNum:Int = 1
    private var isRefreshing:Boolean = false
    private val key:String = "rumor"
    private val newsDao = InfoDao()
    private var isFirst = true

    private fun saveDataInfo(data:List<RumorsResult>){
        newsDao.cachedRumorInfo(data)
        saveTime(System.currentTimeMillis(),key)
    }

    fun getCachedInfo(): PageResult<List<RumorsResult>> {
        val result = PageResult<List<RumorsResult>>()
        val data = newsDao.getCachedRumorInfo()
        data?.let {
            result.data = it
            result.isEmpty = it.isEmpty()
            result.isFromCache = true
            result.isFirst = true
            if (!isNeedToUpdate()){
                pageNum = 2
            }
        }
        return result
    }

    suspend fun loadNextPage(): PageResult<List<RumorsResult>> {
        isRefreshing = false
        return load()
    }

    suspend fun refresh(): PageResult<List<RumorsResult>> {
        isRefreshing = true
        pageNum = 1
        return load()
    }

    suspend fun requestData():PageResult<List<RumorsResult>>{
        return load()
    }

    private suspend fun load(): PageResult<List<RumorsResult>> {
        Log.e("load","$pageNum")
        val result = PageResult<List<RumorsResult>>()
        val response = EpidemicNetwork.getInstance().getRumorsData(pageNum)
        pageNum = if(isRefreshing){2}else{pageNum+1}
        result.isFirst = pageNum == 2
        result.isFromCache = false
        result.data = response.results
        result.isEmpty = response.results.isEmpty()
        if(result.isFirst){
            result.data?.let {
                saveDataInfo(it)
            }
        }
        return result
    }

    fun isNeedToUpdate():Boolean{
        return System.currentTimeMillis() - getSaveTime(key) > 3600*1000
    }
}