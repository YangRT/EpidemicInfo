package com.yang.epidemicinfo.ui.news

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yang.epidemicinfo.R
import com.yang.epidemicinfo.data.model.NewsResult
import com.yang.epidemicinfo.util.longToDate
import kotlinx.android.synthetic.main.news_item.view.*


/**
 * @program: EpidemicInfo
 *
 * @description: 新闻列表 适配器
 *
 * @author: YangRT
 *
 * @create: 2020-03-12 16:57
 **/

class NewsListAdapter(layoutId:Int, data:MutableList<NewsResult>):BaseQuickAdapter<NewsResult,BaseViewHolder>(layoutId,data),LoadMoreModule {

    override fun convert(helper: BaseViewHolder, item: NewsResult?) {
        item?.let {
            helper.setText(R.id.news_title,it.title)
            helper.setText(R.id.news_summary,it.summary)
            helper.setText(R.id.news_come,"信息来源:${it.infoSource}")
            helper.setText(R.id.news_time, longToDate(it.pubDate))
        }

    }
}