package com.yang.epidemicinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.yang.epidemicinfo.data.network.EpidemicNetwork
import com.yang.epidemicinfo.mapview.Dom2xml
import com.yang.epidemicinfo.mapview.MapView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    val util = Dom2xml()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.map)

        val provincesData = flow {
            val data = EpidemicNetwork.getInstance().getProvinceData()
            Log.e("MainActivity","${data.size}")
            emit(data)
        }
        val mapDatas = flow {
                val inputStream = this@MainActivity.resources.openRawResource(R.raw.country_map)
                val mapData = util.dom2xml(inputStream)
                Log.e("MainActivity","${mapData.size}")
                emit(mapData)
        }
        GlobalScope.launch {
            provincesData.zip(mapDatas){provincesData,mapData ->
                val provinceDataMap = provincesData.associateBy { it.provinceShortName }
                for (i in mapData.indices){
                    provinceDataMap[mapData[i].name]?.let {
                        mapData[i].confirmedCount = it.confirmedCount
                        mapData[i].currentConfirmedCount = it.currentConfirmedCount
                        Log.e("MainActivity",mapData[i].name)
                    }


                }
                mapData
            }.map { it->
                for (i in 0 until it.size){
                    when(it[i].currentConfirmedCount){
                        in 0..0 -> it[i].currentFillColor = R.color.colorNumZero
                        in 1..9 -> it[i].currentFillColor = R.color.colorNumLow
                        in 10..99 -> it[i].currentFillColor = R.color.colorNumMid
                        in 100..499 -> it[i].currentFillColor = R.color.colorNumHeight
                        in 500..999 -> it[i].currentFillColor = R.color.colorNumMany
                        in 1000..10000 -> it[i].currentFillColor = R.color.colorNumMore
                        else -> it[i].currentFillColor = R.color.colorNumMost
                    }
                }
                for (i in 0 until it.size){
                    when(it[i].confirmedCount){
                        in 0..0 -> it[i].allFillColor = R.color.colorNumZero
                        in 1..9 -> it[i].allFillColor = R.color.colorNumLow
                        in 10..99 -> it[i].allFillColor = R.color.colorNumMid
                        in 100..499 -> it[i].allFillColor = R.color.colorNumHeight
                        in 500..999 -> it[i].allFillColor = R.color.colorNumMany
                        in 1000..10000 -> it[i].allFillColor = R.color.colorNumMore
                        else -> it[i].allFillColor = R.color.colorNumMost
                    }
                }
                it
            }.collect { it ->
                mapView.mMapRectHeight = util.MAP_RECTF.height()
                mapView.mMapRectWidth = util.MAP_RECTF.width()
                mapView.mapDataList.clear()
                mapView.mapDataList.addAll(it)
                mapView.measure(mapView.measuredWidth,mapView.measuredHeight)
                mapView.postInvalidate()
            }
        }



    }
}
