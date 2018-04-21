package com.crazzylab.rdwallpapers.presentation.previewfavimages

import android.net.Uri
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.crazzylab.rdwallpapers.model.data.db.models.ImageModel
import java.io.File

/**
 * Created by Михаил on 03.10.2017.
 */
interface PreviewFavImagesView: MvpView {

    fun setViewPager(listImages: ArrayList<ImageModel>)
    fun updateOnPageChanged()
    fun setStatusBarTransparent()
    fun setFabIcons()
    fun setToolbar()
    fun goToFavImages(isWhiteAppTheme: Boolean)
    fun setListeners()
    fun removeView(listImages: ArrayList<ImageModel>)
    fun setPositionAfterDeleting(listImages: ArrayList<ImageModel>, position: Int)
    fun setMenuFavIcon(title: Int, icon: Int)
    fun showErrorSnack(error: String)
    fun showDownloadingDialog()
    fun hideDownloadingDialog()
    fun setSettingsIntent()
    fun showNoStoragePermissionSnackbar()
    fun requestPermissionWithRationale()

    @StateStrategyType(OneExecutionStateStrategy::class) fun infoSnack(resId: Int)
    @StateStrategyType(SkipStrategy::class) fun setWallpaperIntent(uri: Uri)
    @StateStrategyType(SkipStrategy::class) fun setShareIntent(uri: Uri)
    @StateStrategyType(SkipStrategy::class)fun goToCropImage(file: File)

}