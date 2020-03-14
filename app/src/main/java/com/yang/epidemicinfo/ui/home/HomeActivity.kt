package com.yang.epidemicinfo.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.yang.epidemicinfo.R
import com.yang.epidemicinfo.databinding.ActivityHomeBinding
import com.yang.epidemicinfo.ui.map.MapFragment
import com.yang.epidemicinfo.ui.news.NewsFragment
import com.yang.epidemicinfo.ui.rumor.RumorFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding
    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        binding.toolbarTitle.text = "疫情地图"
        binding.toolbar.setTitleTextAppearance(this,R.style.Toolbar_TitleText_low)
        fragment = RumorFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment, fragment).commit()
    }
}
