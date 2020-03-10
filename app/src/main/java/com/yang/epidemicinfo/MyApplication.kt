package com.yang.epidemicinfo

import android.app.Application
import android.content.Context
import android.util.Log


/**
 * @program: EpidemicInfo
 *
 * @description: 全局 配置
 *
 * @author: YangRT
 *
 * @create: 2020-03-10 09:45
 **/

class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        Log.e("MyApplication","onCreate")
        context = applicationContext
    }
    companion object {
        lateinit var context: Context
    }

    fun getContext(): Context {
        return context
    }
}