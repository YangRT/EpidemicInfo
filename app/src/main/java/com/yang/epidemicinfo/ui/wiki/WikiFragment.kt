package com.yang.epidemicinfo.ui.wiki

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alguojian.mylibrary.StatusLayout
import com.yang.epidemicinfo.MyApplication.Companion.context
import com.yang.epidemicinfo.R
import com.yang.epidemicinfo.data.model.BaseStatusAdapter
import com.yang.epidemicinfo.data.model.NewsResult
import com.yang.epidemicinfo.data.model.PageStatus
import com.yang.epidemicinfo.data.model.WikiItem
import com.yang.epidemicinfo.databinding.FragmentListBinding
import com.yang.epidemicinfo.ui.news.NewsListAdapter
import com.yang.epidemicinfo.ui.news.NewsViewModel


/**
 * @program: EpidemicInfo
 *
 * @description: 维基知识 ui
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 22:25
 **/

class WikiFragment: Fragment(), Observer<Any> {

    private lateinit var adapter: WikiListAdapter
    private var viewModel : WikiViewModel? = null
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
        viewModel().wikiData.observe(this, Observer { dataInsert(it) })
        init()
    }

    private fun viewModel(): WikiViewModel {
        if(viewModel == null){
            viewModel = ViewModelProvider(this)[WikiViewModel::class.java]
        }
        return viewModel as WikiViewModel
    }

    private fun dataInsert(data: ObservableArrayList<WikiItem>){
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
        adapter = WikiListAdapter(ArrayList())
        adapter.setOnItemClickListener { adapter, view, position ->

        }
        binding.articleRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel().getWikiData()
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
                        Toast.makeText(context,"刷新成功！", Toast.LENGTH_SHORT).show();
                        refreshCancel()
                    }
                }
                PageStatus.EMPTY -> {
                    Log.e("BaseFragment","Empty")
                    statusHelper.showEmpty()
                }
                PageStatus.REFRESH_ERROR -> Toast.makeText(context,"刷新失败！", Toast.LENGTH_SHORT).show()
                PageStatus.REQUEST_ERROR -> Toast.makeText(context,"请求失败,请检查网络！", Toast.LENGTH_SHORT).show();
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