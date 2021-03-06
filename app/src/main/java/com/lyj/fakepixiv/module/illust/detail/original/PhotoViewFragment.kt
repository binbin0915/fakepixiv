package com.lyj.fakepixiv.module.illust.detail.original

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.gyf.immersionbar.ktx.immersionBar
import com.lyj.fakepixiv.R
import com.lyj.fakepixiv.app.base.BackFragment
import com.lyj.fakepixiv.app.data.model.response.Illust
import com.lyj.fakepixiv.app.data.source.remote.IllustRepository
import com.lyj.fakepixiv.databinding.FragmentPhotoViewBinding

/**
 * @author green sun
 *
 * @date 2019/11/11
 *
 * @desc
 */
class PhotoViewFragment : BackFragment<FragmentPhotoViewBinding, PhotoViewModel?>() {

    override var mViewModel: PhotoViewModel? = null

    private var position = 0
    private var key = -1
    lateinit var adapter: PhotoPagerAdapter

    companion object {
        private const val EXTRA_POSITION = "EXTRA_POSITION"
        private const val EXTRA_KEY = "EXTRA_KEY"
        fun newInstance(position: Int = 0, key: Int = -1): PhotoViewFragment {
            return PhotoViewFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_POSITION, position)
                    putInt(EXTRA_KEY, key)
                }
            }
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        arguments?.let {
            position = it.getInt(EXTRA_POSITION, 0)
            key = it.getInt(EXTRA_KEY, -1)
            val data = IllustRepository.instance[key].filter { illust -> illust.itemType == Illust.TYPE_META }
            with(mBinding) {
                adapter = PhotoPagerAdapter(data, position) { page ->
                    mBinding.vm = page.viewModel
                    mViewModel = page.viewModel
                }
                viewPager.adapter = adapter
                viewPager.setCurrentItem(position, false)
                viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {

                    }

                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                    }

                    override fun onPageSelected(position: Int) {
                        val page = adapter.pages[position]
                        mBinding.vm = page.viewModel
                        mViewModel = page.viewModel
                    }

                })
            }
        }

        mToolbar?.inflateMenu(R.menu.menu_photo)
        mToolbar?.setOnMenuItemClickListener {
            mViewModel?.save()
            true
        }
    }

    override fun initImmersionBar() {
        immersionBar {
            titleBarMarginTop(mBinding.toolbar)
            addViewSupportTransformColor(mBinding.toolbar)
            statusBarDarkFont(true)
            transparentStatusBar()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!diffOrientation) {
            if (key != -1) {
                IllustRepository.instance - key
            }
        }
    }

    override fun bindLayout(): Int = R.layout.fragment_photo_view
}