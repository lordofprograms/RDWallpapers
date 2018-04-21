package com.crazzylab.rdwallpapers.presentation.images

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.crazzylab.rdwallpapers.entity.ImageItem

/**
 * Created by Михаил on 13.08.2017.
 */
interface ImagesView: MvpView {

    fun showRefreshProgress(show: Boolean)
    fun showEmptyProgress(show: Boolean)
    fun showPageProgress(show: Boolean)
    fun showEmptyView(show: Boolean)
    fun showEmptyError(show: Boolean, message: String?)
    fun showImages(show: Boolean, images: List<ImageItem>)

    fun scrollToTop()
    fun initRecycler()
    fun infoSnack(resId: Int)
    fun showContent(connection: Boolean)
    fun showConnectionSnack(connection: Boolean)
    fun showReloading()
    fun itemClicks()
    fun goToPreviewImage(position: Int)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

}