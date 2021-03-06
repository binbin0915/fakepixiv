package com.lyj.fakepixiv.module.login

import androidx.databinding.ObservableList
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.GridLayoutManager
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.util.ViewPreloadSizeProvider
import androidx.databinding.library.baseAdapters.BR
import com.lyj.fakepixiv.GlideApp
import com.lyj.fakepixiv.R
import com.lyj.fakepixiv.app.adapter.BaseBindingAdapter
import com.lyj.fakepixiv.app.adapter.BaseBindingViewHolder
import com.lyj.fakepixiv.app.adapter.PreloadBindingAdapter
import com.lyj.fakepixiv.app.data.model.response.Illust
import com.lyj.fakepixiv.databinding.ItemHomeIllustBinding
import com.lyj.fakepixiv.databinding.ItemWallpaperBinding

/**
 * @author greensun
 *
 * @date 2019/3/24
 *
 * @desc 登录页图片墙adapter
 */
class WallpaperAdapter(data: ObservableList<Illust>, val readyCallback: (() -> Unit)) : PreloadBindingAdapter<Illust, ItemWallpaperBinding>(R.layout.item_wallpaper, data, BR.illust) {
    var start = -1
    var end = -1
    private val map = mutableMapOf<Int, Boolean>()

    override fun convert(helper: BaseBindingViewHolder<ItemWallpaperBinding>, item: Illust) {

        helper.binding?.let {
            var request = GlideApp.with(mContext)
                    .load(item.image_urls.square_medium)
            //if ((start == -1) or (helper.adapterPosition <= end)) {
                    request = request.addListener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            val layoutManager = recyclerView.layoutManager as GridLayoutManager
                            if (start == -1) {
                                // 计算可见item数
                                start = layoutManager.findFirstVisibleItemPosition()
                                end = layoutManager.findLastVisibleItemPosition()
                                for (i in start..end) {
                                    map[i] = false
                                }
                            }
                            map[helper.adapterPosition] = true
                            // 可见item图片全部加载完成
                            if (map.values.reduce { a, b -> a and b }) {
                                readyCallback()
                            }
                            return false
                        }

                    })
                //}

            request.into(helper.binding.img)
        }
    }
}