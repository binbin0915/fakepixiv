package com.lyj.fakepixiv.module.common.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.ListPreloader
import androidx.databinding.library.baseAdapters.BR
import com.lyj.fakepixiv.R
import com.lyj.fakepixiv.app.application.ApplicationLike.Companion.context
import com.lyj.fakepixiv.app.adapter.BaseBindingViewHolder
import com.lyj.fakepixiv.app.adapter.PreloadBindingAdapter
import com.lyj.fakepixiv.app.adapter.UserSizeProvider
import com.lyj.fakepixiv.app.constant.IllustCategory
import com.lyj.fakepixiv.app.data.model.response.Illust
import com.lyj.fakepixiv.app.utils.dp2px
import com.lyj.fakepixiv.databinding.ItemUserPreviewBinding
import com.lyj.fakepixiv.module.common.UserItemViewModel
import com.lyj.fakepixiv.widget.CommonItemDecoration

/**
 * @author greensun
 *
 * @date 2019/8/2
 *
 * @desc
 */
class UserPreviewAdapter(data: MutableList<UserItemViewModel>) : PreloadBindingAdapter<UserItemViewModel, ItemUserPreviewBinding>(R.layout.item_user_preview, data, BR.vm) {

    override var sizeProvider: ListPreloader.PreloadSizeProvider<String> = UserSizeProvider()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<ItemUserPreviewBinding> {
        val vh = super.onCreateViewHolder(parent, viewType)
        val avatar = vh.getView<ImageView>(R.id.avatar)
        avatar?.let {
            (sizeProvider as UserSizeProvider).setAvatarView(it)
        }
        return vh
    }

    override fun convert(helper: BaseBindingViewHolder<ItemUserPreviewBinding>, item: UserItemViewModel) {
        super.convert(helper, item)
        helper.binding?.let {
            it.recyclerView.layoutManager.let { _ ->
                it.recyclerView.layoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
            }
            var list = item.data.illusts
            if (list.size < 3) {
                val novels = item.data.novels.take(3 - list.size)
                novels.forEach { value -> value.type = IllustCategory.NOVEL }
                list.addAll(novels)
            }
            var adapter = it.recyclerView.adapter
            if (adapter == null) {
                adapter = object : IllustAdapter(list) {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<ViewDataBinding> {
                        val vh = super.onCreateViewHolder(parent, viewType)
                        val image = vh.getView<ImageView>(R.id.image)
                        image?.let { iv ->
                            (this@UserPreviewAdapter.sizeProvider as UserSizeProvider).setIllistView(iv)
                        }
                        return vh
                    }
                }.apply {
                    addItemType(Illust.TYPE_ILLUST, R.layout.item_illust_small, BR.illust)
                    addItemType(Illust.TYPE_COMIC, R.layout.item_illust_small, BR.illust)
                    addItemType(Illust.TYPE_NOVEL, R.layout.item_novel_small, BR.illust)
                }

                adapter.bindToRecyclerView(it.recyclerView)

                it.recyclerView.addItemDecoration(CommonItemDecoration.Builder().dividerWidth(1.dp2px(), 0).draw(false).build())
            }else {
                (adapter as IllustAdapter).setNewData(list)
            }
        }
    }
}
