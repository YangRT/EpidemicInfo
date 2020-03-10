package com.yang.epidemicinfo.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yang.epidemicinfo.R
import com.yang.epidemicinfo.data.model.BaseEpidemicInfo
import com.yang.epidemicinfo.databinding.FragmentMapBinding
import com.yang.epidemicinfo.mapview.MapView


/**
 * @program: EpidemicInfo
 *
 * @description: 地图 ui
 *
 * @author: YangRT
 *
 * @create: 2020-03-10 14:54
 **/

class MapFragment:Fragment(),Observer<Any> {

    private lateinit var binding:FragmentMapBinding
    private lateinit var adapter:TableAdapter
    private lateinit var tableTitleView:LinearLayout
    private lateinit var headView: LinearLayout
    private lateinit var mapView:MapView
    private var viewModel: MapViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map,container,false)
        retainInstance = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(viewModel())
        initView()
        viewModel().areaData.observe(this, Observer {
            dataInsert(it)
        })
        viewModel().map.observe(this, Observer {
            mapView.mMapRectHeight = it.mapHeight
            mapView.mMapRectWidth = it.mapWidth
            mapView.mapDataList.clear()
            mapView.mapDataList.addAll(it.mapDataList)
            mapView.measure(mapView.measuredWidth,mapView.measuredHeight)
            mapView.postInvalidate()
        })
        viewModel().getChinaMapData("中国")
    }

    private fun viewModel(): MapViewModel {
        if(viewModel == null){
            viewModel = ViewModelProvider(this)[MapViewModel::class.java]
        }
        return viewModel as MapViewModel
    }

    override fun onChanged(t: Any?) {

    }

    private fun initView(){
        headView = LayoutInflater.from(context).inflate(R.layout.map,null) as LinearLayout
        mapView = headView.findViewById(R.id.map)
        tableTitleView = LayoutInflater.from(context).inflate(R.layout.province_table_title,null) as LinearLayout
        adapter = TableAdapter(ArrayList())
        adapter.addHeaderView(headView)
        adapter.addHeaderView(tableTitleView)
        binding.mapTable.layoutManager = LinearLayoutManager(context)
        binding.mapTable.addItemDecoration( DividerItemDecoration(getContext(),
            DividerItemDecoration.VERTICAL)
        )
        binding.mapTable.adapter = adapter

    }

    private fun dataInsert(data: ObservableArrayList<BaseEpidemicInfo>){
        Log.e("Fragment","table")
        adapter.setNewData(data)
    }
}