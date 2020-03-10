package com.yang.epidemicinfo.util

import android.content.Context
import com.google.gson.Gson
import com.yang.epidemicinfo.MyApplication
import com.yang.epidemicinfo.R
import java.lang.reflect.Type

fun saveTime(time:Long,key:String){
    val sharedPreferences = MyApplication.context.getSharedPreferences(key, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putLong("time",time)
    editor.apply();
}

fun getSaveTime(key: String):Long{
    val sharedPreferences = MyApplication.context.getSharedPreferences(key, Context.MODE_PRIVATE)
    return sharedPreferences.getLong("time",0)
}

fun saveData(data:String,key:String){
    val sharedPreferences = MyApplication.context.getSharedPreferences(key, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("data",data)
    editor.apply();
}

fun getSaveData(key: String):String?{
    val sharedPreferences = MyApplication.context.getSharedPreferences(key, Context.MODE_PRIVATE)
    return sharedPreferences.getString("data",null)
}

fun <T> toJson(data: T):String{
    val json = Gson().toJson(data)
    return json
}

fun  <T> getDataFromJson(key:String,type: Type):T?{
    val data = getSaveData(key)
    if(data != null){
        val result:T = Gson().fromJson(data,type)
        return result
    }
    return null
}

fun getMapLocation(where:String):Int{
    var result =  R.raw.country_map
    when(where){
        "中国" -> result = R.raw.country_map
        "安徽" -> result = R.raw.ic_an_hui
        "福建" -> result = R.raw.ic_fu_jiang
        "甘肃" -> result = R.raw.ic_gan_su
        "广东" -> result = R.raw.ic_guang_dong
        "广西" -> result = R.raw.ic_guang_xi
        "贵州" -> result = R.raw.ic_gui_zhou
        "海南" -> result = R.raw.ic_hai_nan
        "河北" -> result = R.raw.ic_he_bei
        "河南" -> result = R.raw.ic_he_nan
        "黑龙江" -> result = R.raw.ic_hei_long_jiang
        "湖北" -> result = R.raw.ic_hu_bei
        "湖南" -> result = R.raw.ic_hu_nan
        "吉林" -> result = R.raw.ic_ji_lin
        "江苏" -> result = R.raw.ic_jiang_su
        "江西" -> result = R.raw.ic_jiang_xi
        "辽宁" -> result = R.raw.ic_liao_ning
        "内蒙古" -> result = R.raw.ic_nei_meng_gu
        "宁夏" -> result = R.raw.ic_ning_xia
        "青海" -> result = R.raw.ic_qing_hai
        "山东" -> result = R.raw.ic_shan_dong
        "山西" -> result = R.raw.ic_shan_xi
        "陕西" -> result = R.raw.ic_shanxi_xi_an
        "四川" -> result = R.raw.ic_si_chuan
        "台湾" -> result = R.raw.ic_tai_wan
        "西藏" -> result = R.raw.ic_xi_zang
        "新疆" -> result = R.raw.ic_xin_jiang
        "云南" -> result = R.raw.ic_yun_nan
        "浙江" -> result = R.raw.ic_zhe_jiang
        "北京" -> result = R.raw.ic_bei_jing
        "天津" -> result = R.raw.ic_tian_jin
    }
    return result
}