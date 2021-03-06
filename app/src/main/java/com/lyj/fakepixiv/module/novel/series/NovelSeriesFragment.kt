package com.lyj.fakepixiv.module.novel.series

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.immersionbar.ImmersionBar
import androidx.databinding.library.baseAdapters.BR
import com.lyj.fakepixiv.R
import com.lyj.fakepixiv.app.base.BackFragment
import com.lyj.fakepixiv.app.data.model.response.Illust
import com.lyj.fakepixiv.app.utils.attachLoadMore
import com.lyj.fakepixiv.app.utils.Router
import com.lyj.fakepixiv.app.utils.bindState
import com.lyj.fakepixiv.databinding.FragmentNovelSeriesBinding
import com.lyj.fakepixiv.module.common.adapter.IllustAdapter

/**
 * @author greensun
 *
 * @date 2019/3/20
 *
 * @desc 漫画系列
 */
class NovelSeriesFragment : BackFragment<FragmentNovelSeriesBinding, NovelSeriesViewModel>() {

    override var mViewModel: NovelSeriesViewModel = NovelSeriesViewModel()

    private var seriesId: String = ""

    companion object {
        private const val EXTRA_SERIES_ID = "EXTRA_SERIES_ID"
        fun newInstance(seriesId: String): NovelSeriesFragment {
            return NovelSeriesFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SERIES_ID, seriesId)
                }
            }
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        arguments?.let {
            seriesId = it.getString(EXTRA_SERIES_ID, "")
            mViewModel.seriesId = seriesId
        }
        mToolbar?.inflateMenu(R.menu.menu_comic_series)

        with(mBinding) {
            val adapter = IllustAdapter(mViewModel.data, true).apply {
                usePreload = false
                addItemType(Illust.TYPE_NOVEL, R.layout.item_series_novel, BR.data)
                addItemType(Illust.TYPE_ILLUST, R.layout.item_series_novel, BR.data)
            }
            adapter.setOnItemClickListener { _, _, position ->
                Router.goNovelDetail(position, adapter.data)
            }
            val header = NovelSeriesHeader(mActivity, mViewModel)
            adapter.addHeaderView(header.rootView)
            adapter.bindState(mViewModel.loadState) {
                mViewModel.load()
            }
            recyclerView.attachLoadMore(mViewModel.loadMoreState) {
                mViewModel.loadMore()
            }
            val layoutManager = LinearLayoutManager(mActivity)
            recyclerView.layoutManager = layoutManager
            adapter.bindToRecyclerView(recyclerView)
            recyclerView.addItemDecoration(DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL))
        }
        mViewModel.load()
    }


    override fun initImmersionBar() {
        ImmersionBar.with(this)
                .titleBarMarginTop(mBinding.toolbar)
                .statusBarColor(R.color.transparent)
                .statusBarColorTransform(R.color.black)
                .statusBarAlpha(0.25f)
                .init()
    }


    override fun bindLayout(): Int = R.layout.fragment_novel_series
}