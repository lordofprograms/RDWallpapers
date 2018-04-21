package com.crazzylab.rdwallpapers.presentation.cropimage

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.support.v4.app.FragmentActivity
import com.arellomobile.mvp.InjectViewState
import com.crazzylab.rdwallpapers.RDWallpapersApp
import com.crazzylab.rdwallpapers.di.components.DaggerCropImageComponent
import com.crazzylab.rdwallpapers.di.modules.CropImageModule
import com.crazzylab.rdwallpapers.model.system.SchedulersProvider
import com.crazzylab.rdwallpapers.presentation.global.BasePresenter
import com.crazzylab.rdwallpapers.presentation.global.managers.RestErrorHandler
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by Михаил on 23.08.2017.
 */
@InjectViewState
class CropImagePresenter : BasePresenter<CropImageView>() {

    @Inject lateinit var rxSchedulers: SchedulersProvider
    @Inject lateinit var errorHandler: RestErrorHandler

    init {
        DaggerCropImageComponent.builder()
                .appComponent(RDWallpapersApp.graph)
                .cropImageModule(CropImageModule()).build().inject(this)
    }

    fun makeSbTransparent(activity: FragmentActivity): Disposable =
            Completable.fromObservable(transparent(activity))
                    .subscribe(
                            { activity.supportFragmentManager.popBackStack() },
                            { errorHandler.proceed(it) }
                    )

    fun setWallpaper(activity: FragmentActivity, croppedImage: Bitmap) =
            Observable.just(WallpaperManager.getInstance(activity))
                    .subscribeOn(rxSchedulers.io())
                    .observeOn(rxSchedulers.ui())
                    .subscribe(
                            { it.setBitmap(croppedImage) },
                            { errorHandler.proceed(it) },
                            { viewState.afterWallpapersSet() }
                    )
                    .connect()

}
