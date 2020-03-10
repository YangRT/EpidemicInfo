package com.yang.epidemicinfo.data.model

import com.chad.library.adapter.base.entity.MultiItemEntity


/**
 * @program: EpidemicInfo
 *
 * @description: 疫情基础信息
 *
 * @author: YangRT
 *
 * @create: 2020-03-10 15:59
 **/

class BaseEpidemicInfo(override val itemType: Int) : MultiItemEntity {

    var currentConfirmedCount:Int = 0
    var confirmedCount:Int = 0
    var curedCount:Int = 0
    var deadCount:Int = 0
    var area:String = ""

    companion object{
        const val PROVINCE = 1
        const val OTHER = 2
    }

}