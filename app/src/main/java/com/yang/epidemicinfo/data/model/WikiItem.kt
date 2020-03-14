package com.yang.epidemicinfo.data.model

import com.chad.library.adapter.base.entity.MultiItemEntity


/**
 * @program: EpidemicInfo
 *
 * @description: 维基知识列表 item
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 21:58
 **/

class WikiItem(override val itemType: Int) :MultiItemEntity {

    var description: String = ""
    var id: Int = 0
    var imgUrl: String = ""
    var linkUrl: String = ""
    var sort: Int = 0
    var title: String = ""

    companion object{
        val TITLE = 0
        val NORMAL = 1
    }
}