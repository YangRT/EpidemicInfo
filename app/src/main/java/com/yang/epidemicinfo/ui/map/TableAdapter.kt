package com.yang.epidemicinfo.ui.map

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yang.epidemicinfo.R
import com.yang.epidemicinfo.data.model.BaseEpidemicInfo


/**
 * @program: EpidemicInfo
 *
 * @description: 表格适配器
 *
 * @author: YangRT
 *
 * @create: 2020-03-10 16:15
 **/

class TableAdapter(data:MutableList<BaseEpidemicInfo>):
    BaseMultiItemQuickAdapter<BaseEpidemicInfo, BaseViewHolder>(data) {


    init {
        addItemType(BaseEpidemicInfo.PROVINCE, R.layout.province_tabe_item)
        addItemType(BaseEpidemicInfo.OTHER,R.layout.table_item)
    }

    override fun convert(helper: BaseViewHolder, item: BaseEpidemicInfo?) {
        item?.let {
            helper.setText(R.id.table_area,it.area)
            helper.setText(R.id.table_all_count,"${it.confirmedCount}")
            helper.setText(R.id.table_cured_count,"${it.curedCount}")
            helper.setText(R.id.table_current_count,"${it.currentConfirmedCount}")
            helper.setText(R.id.table_died_count,"${it.deadCount}")
            it
        }
    }
}