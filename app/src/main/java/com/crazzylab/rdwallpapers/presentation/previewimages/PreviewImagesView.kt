package com.crazzylab.rdwallpapers.presentation.previewimages

import android.net.Uri
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.crazzylab.rdwallpapers.entity.ImageItem
import java.io.File

/**
 * Created by Михаил on 20.08.2017.
 */
interface PreviewImagesView: MvpView {

    fun setList(emptyList: ArrayList<ImageItem>)
    fun setViewPager(listImages: ArrayList<ImageItem>)
    fun updateOnPageChanged()
    fun setStatusBarTransparent()
    fun setFabIcons()
    fun setToolbar()
    fun setListeners()
    fun showErrorSnack(error: String)
    fun showDownloadingDialog()
    fun hideDownloadingDialog()
    fun setSettingsIntent()
    fun showNoStoragePermissionSnackbar()
    fun requestPermissionWithRationale()

    @StateStrategyType(OneExecutionStateStrategy::class) fun infoSnack(resId: Int)
    @StateStrategyType(SkipStrategy::class) fun setMenuFavIcon(title: Int, icon: Int)
    @StateStrategyType(SkipStrategy::class) fun setWallpaperIntent(uri: Uri)
    @StateStrategyType(SkipStrategy::class) fun setShareIntent(uri: Uri)
    @StateStrategyType(SkipStrategy::class) fun goToCropImage(file: File)

}