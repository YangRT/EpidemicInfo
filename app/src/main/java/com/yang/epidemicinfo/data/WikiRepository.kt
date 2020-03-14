package com.yang.epidemicinfo.data

import com.yang.epidemicinfo.data.db.KnowledgeDao
import com.yang.epidemicinfo.data.model.KnowledgeResult
import com.yang.epidemicinfo.data.model.ProtectiveKnowledgeInfo
import com.yang.epidemicinfo.data.model.WikiItem
import com.yang.epidemicinfo.data.network.EpidemicNetwork
import com.yang.epidemicinfo.util.getSaveTime
import com.yang.epidemicinfo.util.saveTime


/**
 * @program: EpidemicInfo
 *
 * @description: 维基知识 repository
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 21:32
 **/

class WikiRepository {

    private val dataDao =  KnowledgeDao()
    private val key = "wiki"
    private var refreshing:Boolean = false

    private fun saveWikiData(data: List<WikiItem>){
        dataDao.cachedWikiInfo(data)
        saveTime(System.currentTimeMillis(),key)
    }


    fun getCachedData(): List<WikiItem>?{
        return dataDao.getCachedWikiInfo()
    }

    suspend fun refresh(): List<WikiItem>?{
        refreshing = true
        return load()
    }

    suspend fun requestData(): List<WikiItem>?{
        return load()
    }

    private suspend fun load(): List<WikiItem>?{
        val response = EpidemicNetwork.getInstance().getWikiInfo()
        refreshing = false
        val result = ArrayList<WikiItem>()
        for (i in response.result.indices){
            val item = if (response.result[i].imgUrl == ""){
                WikiItem(WikiItem.TITLE)
            }else{
                WikiItem(WikiItem.NORMAL)
            }
            item.let {
                it.description = response.result[i].description
                it.title = response.result[i].title
                it.id = response.result[i].id
                it.sort = response.result[i].sort
                it.linkUrl = response.result[i].linkUrl
                it.imgUrl = response.result[i].imgUrl
                result.add(it)
                it
            }
        }
        if (result.isNotEmpty()){
            saveWikiData(result)
        }
        return result
    }

    fun isNeedToUpdate():Boolean{
        return System.currentTimeMillis() - getSaveTime(key) > 3600*1000*24
    }
}