package com.yang.epidemicinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.yang.epidemicinfo.databinding.ActivityMainBinding
import com.yang.epidemicinfo.ui.map.MapFragment
import com.yang.epidemicinfo.ui.news.NewsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        fragment = NewsFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment).commit()

    }
}
