package com.crazzylab.rdwallpapers.presentation.favimages

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.crazzylab.rdwallpapers.model.data.db.models.ImageModel

/**
 * Created by Михаил on 27.08.2017.
 */
interface FavImagesView: MvpView {

    fun initView()
    fun updateList()
    fun showEmpty(list: ArrayList<ImageModel>)
    @StateStrategyType(OneExecutionStateStrategy::class) fun showErrorSnack(error: String)
    fun setList(list: ArrayList<ImageModel>)
    fun itemClicks()
    fun scrollToTop()
    fun goToPreviewImage(position: Int)

}