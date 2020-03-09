package com.yang.epidemicinfo.data.model


/**
 * @program: EpidemicInfo
 *
 * @description: 各省份信息
 *
 * @author: YangRT
 *
 * @create: 2020-03-09 17:58
 **/
// https://lab.isaaclin.cn/nCoV/api/area
// 参数;province  latest: 1.返回最新数据（默认）0.返回时间序列数据
data class ProvinceInfo(
    val results: List<ProvinceResult>,
    val success: Boolean
)

data class ProvinceResult(
    val cities: List<City>,
    val comment: String,
    val confirmedCount: Int,
    val continentEnglishName: String,
    val continentName: String,
    val countryEnglishName: String,
    val countryName: String,
    val curedCount: Int,
    val currentConfirmedCount: Int,
    val deadCount: Int,
    val locationId: Int,
    val provinceEnglishName: String,
    val provinceName: String,
    val provinceShortName: String,
    val suspectedCount: Int,
    val updateTime: Long
)

data class City(
    val cityEnglishName: String,
    val cityName: String,
    val confirmedCount: Int,
    val curedCount: Int,
    val currentConfirmedCount: Int,
    val deadCount: Int,
    val locationId: Int,
    val suspectedCount: Int
)