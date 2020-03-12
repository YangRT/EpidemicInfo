package com.yang.epidemicinfo.data.model


/**
 * @program: WanAndroid
 *
 * @description: 返回数据结果
 *
 * @author: YangRT
 *
 * @create: 2020-02-15 11:14
 **/

class PageResult<T> {

    var data:T? = null
    var isFirst :Boolean= false
    var isEmpty : Boolean = true
    var isFromCache:Boolean = false
    var msg:String? = null
}