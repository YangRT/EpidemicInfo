package com.yang.epidemicinfo.ui.rumor

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yang.epidemicinfo.R
import com.yang.epidemicinfo.data.model.NewsResult
import com.yang.epidemicinfo.data.model.RumorsResult


/**
 * @program: EpidemicInfo
 *
 * @description: 谣言列表 适配器
 *
 * @author: YangRT
 *
 * @create: 2020-03-12 16:58
 **/

class RumorListAdapter (layoutId:Int, data:MutableList<RumorsResult>):
    BaseQuickAdapter<RumorsResult, BaseViewHolder>(layoutId,data),LoadMoreModule {

    override fun convert(helper: BaseViewHolder, item: RumorsResult?) {
        item?.let {
            helper.setText(R.id.rumor_title,it.title)
            helper.setText(R.id.rumor_main,it.mainSummary)
            helper.setText(R.id.rumor_body,it.body)
        }
    }


}