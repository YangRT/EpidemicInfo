package com.yang.epidemicinfo.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


/**
 * @program: WanAndroid
 *
 * @description: tablayout与 viewPager 适配器
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 16:16
 **/

class TabViewPagerAdapter(fm: FragmentManager,val item:List<Fragment>,private val titleList:List<String>): FragmentStatePagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return item[position]
    }

    override fun getCount(): Int {
        return item.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }
}