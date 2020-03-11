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
import androidx.recyclerview.widget.LinearLayoutManager
import com.yang.epidemicinfo.R
import com.yang.epidemicinfo.data.model.BaseEpidemicInfo
import com.yang.epidemicinfo.databinding.FragmentMapBinding


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
            activity?.runOnUiThread {
                Log.e("MapFragment",Thread.currentThread().name)
                binding.map.mMapRectHeight = it.mapHeight
                        binding.map.mMapRectWidth = it.mapWidth
                binding.map.mapDataList.clear()
                binding.map.mapDataList.addAll(it.mapDataList)
                binding.map.measure(binding.map.measuredWidth,binding.map.measuredHeight)
                binding.map.invalidate()
                viewModel().getChinaData()
            }

        })

    }

    override fun onResume() {
        super.onResume()
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
//        tableTitleView = LayoutInflater.from(context).inflate(R.layout.province_table_title,null) as LinearLayout
//        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,70)
//        tableTitleView.layoutParams = lp
        adapter = TableAdapter(ArrayList())
       // adapter.addHeaderView(tableTitleView)
        binding.mapTable.layoutManager = LinearLayoutManager(context)
        binding.mapTable.adapter = adapter

    }

    private fun dataInsert(data: ObservableArrayList<BaseEpidemicInfo>){
        Log.e("Fragment","table")
        adapter.setNewData(data)
    }
}