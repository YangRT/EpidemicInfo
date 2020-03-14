package com.yang.epidemicinfo.ui.protect

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yang.epidemicinfo.R
import com.yang.epidemicinfo.data.model.NewsResult
import com.yang.epidemicinfo.data.model.ProtectiveKnowledgeInfo
import com.yang.epidemicinfo.util.longToDate
import kotlinx.android.synthetic.main.protect_item.view.*


/**
 * @program: EpidemicInfo
 *
 * @description: 防护 适配器
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 22:52
 **/

class ProtectAdapter(layoutId:Int, data:MutableList<ProtectiveKnowledgeInfo>):
    BaseQuickAdapter<ProtectiveKnowledgeInfo, BaseViewHolder>(layoutId,data) {
    override fun convert(helper: BaseViewHolder, item: ProtectiveKnowledgeInfo?) {
        item?.let{
            helper.setText(R.id.protect_title,it.title)
            helper.setText(R.id.protect_time, longToDate(it.createTime))
            Glide.with(context).load(it.imgUrl).into(helper.itemView.findViewById(R.id.protect_image))

        }
    }
}