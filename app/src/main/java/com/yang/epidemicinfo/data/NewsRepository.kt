package com.yang.epidemicinfo.data

import android.util.Log
import com.yang.epidemicinfo.data.db.InfoDao
import com.yang.epidemicinfo.data.model.NewsResult
import com.yang.epidemicinfo.data.model.PageResult
import com.yang.epidemicinfo.data.network.EpidemicNetwork
import com.yang.epidemicinfo.util.getSaveTime
import com.yang.epidemicinfo.util.saveTime


/**
 * @program: EpidemicInfo
 *
 * @description: 新闻信息 repository
 *
 * @author: YangRT
 *
 * @create: 2020-03-12 16:17
 **/

class NewsRepository {

    private var pageNum:Int = 1
    private var isRefreshing:Boolean = false
    private val key:String = "news"
    private val infoDao = InfoDao()
    private var isFirst:Boolean = true

    private fun saveDataInfo(data:List<NewsResult>){
        infoDao.cachedNewsInfo(data)
        saveTime(System.currentTimeMillis(),key)
    }

     fun getCachedInfo():PageResult<List<NewsResult>>{
        val result = PageResult<List<NewsResult>>()
        val data = infoDao.getCachedNewsInfo()
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

    suspend fun loadNextPage():PageResult<List<NewsResult>>{
        isRefreshing = false
        return load()
    }

    suspend fun refresh():PageResult<List<NewsResult>>{
        isRefreshing = true
        pageNum = 1
        return load()
    }

    suspend fun requestData():PageResult<List<NewsResult>>{
        return load()
    }

    private suspend fun load():PageResult<List<NewsResult>>{
        Log.e("News","$pageNum")
        val result = PageResult<List<NewsResult>>()
        val response = EpidemicNetwork.getInstance().getNewsData(pageNum)
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
        return System.currentTimeMillis() -getSaveTime(key) > 3600*1000
    }
}