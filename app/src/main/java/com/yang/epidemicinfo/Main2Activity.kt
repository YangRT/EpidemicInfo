package com.yang.epidemicinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yang.epidemicinfo.MyApplication.Companion.context
import com.yang.epidemicinfo.data.model.BaseEpidemicInfo
import com.yang.epidemicinfo.data.network.MapRepository
import com.yang.epidemicinfo.databinding.ActivityMain2Binding
import com.yang.epidemicinfo.customview.mapview.Dom2xml
import com.yang.epidemicinfo.customview.mapview.Map
import com.yang.epidemicinfo.customview.mapview.MapView
import com.yang.epidemicinfo.ui.map.TableAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class Main2Activity : AppCompatActivity() {

    val dom2xml = Dom2xml()
    lateinit var binding:ActivityMain2Binding
    private lateinit var adapter: TableAdapter
    private lateinit var tableTitleView: LinearLayout
    private lateinit var headView: LinearLayout
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main2)
        initView()
        GlobalScope.launch {
            val provinceData = flow {
                val result = MapRepository.getInstance().getCachedChinaData()
                emit(result)
            }
            provinceData.map {
                val result = ArrayList<BaseEpidemicInfo>()
                it.forEach { data ->
                    val item = BaseEpidemicInfo(BaseEpidemicInfo.PROVINCE)
                    item.area = data.provinceShortName
                    item.confirmedCount = data.confirmedCount
                    item.curedCount = data.curedCount
                    item.currentConfirmedCount = data.currentConfirmedCount
                    item.deadCount = data.deadCount
                    result.add(item)
                }
                result
            }.collect{
                runOnUiThread {
                    Log.e("Main2Activity","recycler")
                    adapter.setNewData(it)
                }
            }
            val mapData = flow {
               // val map = MapDao().requestMapInfo("中国")
                val map = MapRepository.getInstance().getCachedMap("中国")
                emit(map)
            }
            provinceData.zip(mapData){data,map->
                val provinceDataMap = data.associateBy { it.provinceShortName }
                for (i in map.mapDataList.indices){
                    provinceDataMap[map.mapDataList[i].name]?.let {
                        map.mapDataList[i].confirmedCount = it.confirmedCount
                        map.mapDataList[i].currentConfirmedCount = it.currentConfirmedCount
                    }
                }
                map
            }.map {
                setMapColor(it)
                it
            }.collect {
                mapView.mMapRectHeight = it.mapHeight
                mapView.mMapRectWidth = it.mapWidth
                mapView.mapDataList.clear()
                mapView.mapDataList.addAll(it.mapDataList)
                mapView.measure(mapView.measuredWidth,mapView.measuredHeight)
                mapView.postInvalidate()
            }
        }
    }

    private fun initView(){
        headView = LayoutInflater.from(context).inflate(R.layout.map,null) as LinearLayout
        mapView = headView.findViewById(R.id.map)
        tableTitleView = LayoutInflater.from(context).inflate(R.layout.province_table_title,null) as LinearLayout
        adapter = TableAdapter(ArrayList())
        adapter.addHeaderView(headView)
        adapter.addHeaderView(tableTitleView)
        binding.mapTable.layoutManager = LinearLayoutManager(context)
        binding.mapTable.addItemDecoration( DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL)
        )
        binding.mapTable.adapter = adapter
    }

    private fun setMapColor(map:Map){
        for (i in map.mapDataList.indices){
            when(map.mapDataList[i].currentConfirmedCount){
                in 0..0 -> map.mapDataList[i].currentFillColor = R.color.colorNumZero
                in 1..9 -> map.mapDataList[i].currentFillColor = R.color.colorNumLow
                in 10..99 -> map.mapDataList[i].currentFillColor = R.color.colorNumMid
                in 100..499 -> map.mapDataList[i].currentFillColor = R.color.colorNumHeight
                in 500..999 -> map.mapDataList[i].currentFillColor = R.color.colorNumMany
                in 1000..10000 -> map.mapDataList[i].currentFillColor = R.color.colorNumMore
                else -> map.mapDataList[i].currentFillColor = R.color.colorNumMost
            }
        }
        for (i in map.mapDataList.indices){
            when(map.mapDataList[i].confirmedCount){
                in 0..0 -> map.mapDataList[i].allFillColor = R.color.colorNumZero
                in 1..9 -> map.mapDataList[i].allFillColor = R.color.colorNumLow
                in 10..99 -> map.mapDataList[i].allFillColor = R.color.colorNumMid
                in 100..499 -> map.mapDataList[i].allFillColor = R.color.colorNumHeight
                in 500..999 -> map.mapDataList[i].allFillColor = R.color.colorNumMany
                in 1000..10000 -> map.mapDataList[i].allFillColor = R.color.colorNumMore
                else -> map.mapDataList[i].allFillColor = R.color.colorNumMost
            }
        }
    }
}
