package com.yang.epidemicinfo.ui.news

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
import com.yang.epidemicinfo.R
import com.yang.epidemicinfo.data.model.BaseStatusAdapter
import com.yang.epidemicinfo.data.model.NewsResult
import com.yang.epidemicinfo.data.model.PageStatus
import com.yang.epidemicinfo.databinding.FragmentListBinding


/**
 * @program: EpidemicInfo
 *
 * @description: 新闻 ui
 *
 * @author: YangRT
 *
 * @create: 2020-03-12 16:56
 **/

class NewsFragment:Fragment(),Observer<Any>{

    private lateinit var adapter:NewsListAdapter
    private var viewModel : NewsViewModel? = null
    private lateinit var binding:FragmentListBinding
    private lateinit var statusHelper: StatusLayout.StatusHelper
    private lateinit var headView: FrameLayout


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
        viewModel().data.observe(this, Observer { dataInsert(it) })
        init()
    }

    private fun viewModel():NewsViewModel{
        if(viewModel == null){
            viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        }
        return viewModel as NewsViewModel
    }

    private fun dataInsert(data:ObservableArrayList<NewsResult>){
        adapter.setNewData(data)
    }

    private fun isRefreshing():Boolean{
        return binding.mainPageRefreshLayout.isRefreshing
    }

    private fun init(){
        headView = LayoutInflater.from(context).inflate(R.layout.news_title,null) as FrameLayout
        val lp = FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,80)
        lp.topMargin = 15
        headView.layoutParams = lp
        binding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.mainPageRefreshLayout.setOnRefreshListener {
            viewModel().refresh()
        }
        adapter = NewsListAdapter(R.layout.news_item,ArrayList())
        adapter.loadMoreModule?.setOnLoadMoreListener {
            viewModel().loadNextPage()
        }
        adapter.setOnItemClickListener { adapter, view, position ->

        }
        adapter.addHeaderView(headView)
        adapter.loadMoreModule?.isEnableLoadMoreIfNotFullPage = false
        binding.articleRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel().getCacheData()
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
                    loadMoreFinished()
                }
                PageStatus.EMPTY -> {
                    Log.e("BaseFragment","Empty")
                    statusHelper.showEmpty()
                }
                PageStatus.NO_MORE_DATA -> {loadMoreEmpty()}
                PageStatus.LOAD_MORE_FAILED -> loadMoreFailed()
                PageStatus.REFRESH_ERROR -> Toast.makeText(context,"刷新失败！", Toast.LENGTH_SHORT).show()
                PageStatus.REQUEST_ERROR -> Toast.makeText(context,"请求失败,请检查网络！", Toast.LENGTH_SHORT).show();
                PageStatus.NETWORK_ERROR -> {
                    statusHelper.showFailed()
                }
            }
        }
    }

    private fun loadMoreFailed(){
        if(adapter.loadMoreModule?.isLoading!!){
            adapter.loadMoreModule?.loadMoreFail()
        }
    }

    private fun loadMoreFinished(){
        if(adapter.loadMoreModule?.isLoading!!){
            adapter.loadMoreModule?.loadMoreComplete()
        }
    }

    private fun loadMoreEmpty(){
        if(adapter.loadMoreModule?.isLoading!!){
            adapter.loadMoreModule?.loadMoreEnd()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusLayout.clearNewAdapter()
    }
}