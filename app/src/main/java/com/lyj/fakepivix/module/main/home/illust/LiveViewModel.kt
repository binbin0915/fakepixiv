package com.lyj.fakepivix.module.main.home.illust

import android.databinding.ObservableArrayList
import com.lyj.fakepivix.app.base.BaseViewModel
import com.lyj.fakepivix.app.base.IModel
import com.lyj.fakepivix.app.data.model.response.Live
import com.lyj.fakepivix.app.data.source.remote.LiveRepository
import com.lyj.fakepivix.app.network.LoadState
import io.reactivex.rxkotlin.subscribeBy

/**
 * @author greensun
 *
 * @date 2019/4/15
 *
 * @desc
 */
class LiveViewModel : BaseViewModel<IModel?>() {
    override val mModel: IModel? = null

    val data = ObservableArrayList<Live>()

    var loadState: LoadState = LoadState.Idle

    fun load() {
        val disposable = LiveRepository.instance
                .loadRecommend()
                .doOnSubscribe{ loadState = LoadState.Loading }
                .subscribeBy(onNext = {
                    loadState = LoadState.Succeed
                    data.clear()
                    data.addAll(it)
                }, onError = {
                    loadState = LoadState.Failed(it)
                })
        addDisposable(disposable)
    }
}