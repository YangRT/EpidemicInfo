package com.yang.epidemicinfo.ui.wiki

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yang.epidemicinfo.R
import com.yang.epidemicinfo.data.model.BaseEpidemicInfo
import com.yang.epidemicinfo.data.model.WikiItem


/**
 * @program: EpidemicInfo
 *
 * @description: wiki 知识 适配器
 *
 * @author: YangRT
 *
 * @create: 2020-03-14 21:56
 **/

class WikiListAdapter(data:MutableList<WikiItem>):
    BaseMultiItemQuickAdapter<WikiItem, BaseViewHolder>(data) {

    init {
            addItemType(WikiItem.TITLE, R.layout.wiki_item)
            addItemType(WikiItem.NORMAL,R.layout.wiki_item_pic)

    }

    override fun convert(helper: BaseViewHolder, item: WikiItem?) {
        item?.let {
            helper.setText(R.id.wiki_title,it.title)
            helper.setText(R.id.wiki_description,it.description)
            if (helper.itemViewType == WikiItem.NORMAL){
                Glide.with(context).load(it.imgUrl).into(helper.itemView.findViewById(R.id.wiki_pic))
            }
        }

    }
}