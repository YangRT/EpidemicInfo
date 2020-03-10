package com.yang.epidemicinfo.data.model


/**
 * @program: EpidemicInfo
 *
 * @description: 省份数据
 *
 * @author: YangRT
 *
 * @create: 2020-03-09 20:23
 **/
 // http://49.232.173.220:3001/data/getListByCountryTypeService1 返回数组
data class ProvinceData(
    val cityName: String,
    val comment: String,
    val confirmedCount: Int,
    val continents: String,
    val countryShortCode: String,
    val countryType: Int,
    val createTime: Long,
    val curedCount: Int,
    val currentConfirmedCount: Int,
    val deadCount: Int,
    val id: Int,
    val locationId: Int,
    val modifyTime: Long,
    val operator: String,
    val provinceId: String,
    val provinceName: String,
    val provinceShortName: String,
    val sort: Int,
    val suspectedCount: Int,
    val tags: String
)