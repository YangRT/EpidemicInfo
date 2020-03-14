package com.yang.epidemicinfo.ui.protect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alguojian.mylibrary.StatusLayout

import com.yang.epidemicinfo.R
import com.yang.epidemicinfo.data.model.BaseStatusAdapter
import com.yang.epidemicinfo.data.model.PageStatus
import com.yang.epidemicinfo.data.model.ProtectiveKnowledgeInfo
import com.yang.epidemicinfo.databinding.FragmentListBinding



/**
 * @program: EpidemicInfo
 *
 * @description: 防护 ui
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 22:52
 **/

class ProtectFragment: Fragment(), Observer<Any> {

    private lateinit var adapter: ProtectAdapter
    private var viewModel : ProtectViewModel? = null
    private lateinit var binding: FragmentListBinding
    private lateinit var statusHelper: StatusLayout.StatusHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list,container,false)
        retainInstance = true
        val statusLayout = StatusLayout.setNewAdapter(BaseStatusAdapter())
        statusHelper = statusLayout.attachView(binding.root)
            .onRetryClick {
                viewModel().refresh()
            }
        return StatusLayout.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(viewModel())
        viewModel().status.observe(this,this)
        viewModel().protectData.observe(this, Observer { dataInsert(it) })
        init()
    }

    private fun viewModel(): ProtectViewModel {
        if(viewModel == null){
            viewModel = ViewModelProvider(this)[ProtectViewModel::class.java]
        }
        return viewModel as ProtectViewModel
    }

    private fun dataInsert(data: ObservableArrayList<ProtectiveKnowledgeInfo>){
        adapter.setNewData(data)
    }

    private fun isRefreshing():Boolean{
        return binding.mainPageRefreshLayout.isRefreshing
    }

    private fun init(){
        binding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.mainPageRefreshLayout.setOnRefreshListener {
            viewModel().refresh()
        }
        adapter = ProtectAdapter(R.layout.protect_item,ArrayList())
        adapter.setOnItemClickListener { adapter, view, position ->

        }
        binding.articleRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel().getProtectData()
    }

    private fun refreshCancel(){
        if (binding.mainPageRefreshLayout.isRefreshing){
            binding.mainPageRefreshLayout.isRefreshing = false
        }
    }

    override fun onChanged(t: Any?) {
        if(t is PageStatus){
            Log.e("BaseFragment", "change$t")
            when(t){
                PageStatus.LOADING -> {
                    statusHelper.showLoading()
                }
                PageStatus.SHOW_CONTENT -> {
                    statusHelper.showSuccess()
                    if(isRefreshing()){
                        Toast.makeText(context,"刷新成功！", Toast.LENGTH_SHORT).show()
                        refreshCancel()
                    }
                }
                PageStatus.EMPTY -> {
                    Log.e("BaseFragment","Empty")
                    statusHelper.showEmpty()
                }
                PageStatus.REFRESH_ERROR -> Toast.makeText(context,"刷新失败！", Toast.LENGTH_SHORT).show()
                PageStatus.REQUEST_ERROR -> Toast.makeText(context,"请求失败,请检查网络！", Toast.LENGTH_SHORT).show()
                PageStatus.NETWORK_ERROR -> {
                    statusHelper.showFailed()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusLayout.clearNewAdapter()
    }
}