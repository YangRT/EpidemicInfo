package com.yang.epidemicinfo.data.model


/**
 * @program: EpidemicInfo
 *
 * @description: sss
 *
 * @author: YangRT
 *
 * @create: 2020-03-10 14:09
 **/

data class ProvinceInfo(
    val cities: List<CityInfo>,
    val comment: String,
    val confirmedCount: Int,
    val curedCount: Int,
    val currentConfirmedCount: Int,
    val deadCount: Int,
    val locationId: Int,
    val provinceName: String,
    val provinceShortName: String,
    val statisticsData: String,
    val suspectedCount: Int
)

data class CityInfo(
    val cityName: String,
    val confirmedCount: Int,
    val curedCount: Int,
    val currentConfirmedCount: Int,
    val deadCount: Int,
    val locationId: Int,
    val suspectedCount: Int
)