package com.yang.epidemicinfo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.yang.epidemicinfo.data.network.EpidemicNetwork
import com.yang.epidemicinfo.databinding.ActivityMainBinding
import com.yang.epidemicinfo.mapview.Dom2xml
import com.yang.epidemicinfo.mapview.MapView
import com.yang.epidemicinfo.ui.map.MapFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        window.statusBarColor = Color.TRANSPARENT
        fragment = MapFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment).commit()


    }
}
