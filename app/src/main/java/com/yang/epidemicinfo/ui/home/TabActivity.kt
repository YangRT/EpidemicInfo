package com.yang.epidemicinfo.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide.init
import com.yang.epidemicinfo.R
import com.yang.epidemicinfo.databinding.ActivityTabBinding
import com.yang.epidemicinfo.ui.map.MapFragment
import com.yang.epidemicinfo.ui.news.NewsFragment
import com.yang.epidemicinfo.ui.rumor.RumorFragment

class TabActivity : AppCompatActivity() {

    private lateinit var adapter: TabViewPagerAdapter
    private var fragmentList:ArrayList<Fragment> = ArrayList()
    private var titleList:ArrayList<String> = ArrayList()
    private lateinit var binding:ActivityTabBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tab)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        binding.toolbarTitle.text = "疫情信息"
        binding.toolbar.setTitleTextAppearance(this,R.style.Toolbar_TitleText_low)
        initData()
        init()
    }

    private fun initData(){
        val mapFragment = MapFragment()
        val newsFragment = NewsFragment()
        val rumorFragment = RumorFragment()
        fragmentList.add(mapFragment)
        fragmentList.add(newsFragment)
        fragmentList.add(rumorFragment)

        titleList.add("疫情地图")
        titleList.add("实时播报")
        titleList.add("谣言")
    }

    private fun init(){
        adapter = TabViewPagerAdapter(supportFragmentManager,fragmentList,titleList)
        binding.tabLayout.setupWithViewPager(binding.tabViewPager)
        binding.tabViewPager.adapter = adapter
    }
}
