package com.lyj.fakepixiv.module.pixivision

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import com.lyj.fakepixiv.R
import com.lyj.fakepixiv.app.adapter.BaseBindingAdapter
import com.lyj.fakepixiv.app.data.model.response.SpotlightArticle
import com.lyj.fakepixiv.app.utils.Router

/**
 * @author green sun
 *
 * @date 2019/10/14
 *
 * @desc
 */
class PixivisionAdapter(@LayoutRes layoutId: Int, data: MutableList<SpotlightArticle>, itemBindId: Int) : BaseBindingAdapter<SpotlightArticle, ViewDataBinding>(layoutId, data, itemBindId) {
    init {
        setOnItemClickListener { adapter, view, position ->
            val item = data[position]
            Router.getTopFragment()?.start(PixivisionFragment.newInstance(item.article_url))
        }
    }
}