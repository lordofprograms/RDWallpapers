package com.crazzylab.rdwallpapers.presentation.previewimages

import android.Manifest
import android.net.Uri
import android.os.Build
import android.support.v4.app.FragmentActivity
import com.arellomobile.mvp.InjectViewState
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.RDWallpapersApp
import com.crazzylab.rdwallpapers.di.components.DaggerPreviewImagesComponent
import com.crazzylab.rdwallpapers.di.modules.previewImages.PreviewImagesModule
import com.crazzylab.rdwallpapers.entity.ImageItem
import com.crazzylab.rdwallpapers.extensions.toast
import com.crazzylab.rdwallpapers.model.interactor.PreviewImagesInteractor
import com.crazzylab.rdwallpapers.model.system.SchedulersProvider
import com.crazzylab.rdwallpapers.presentation.global.BasePresenter
import com.crazzylab.rdwallpapers.presentation.global.managers.RestErrorHandler
import com.crazzylab.rdwallpapers.presentation.global.managers.permissions.PermissionsManager
import com.crazzylab.rdwallpapers.presentation.utils.clearTransparent
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by Михаил on 20.08.2017.
 */
@InjectViewState
class PreviewImagesPresenter : BasePresenter<PreviewImagesView>() {

    @Inject lateinit var previewImagesInteractor: PreviewImagesInteractor
    @Inject lateinit var rxSchedulers: SchedulersProvider
    @Inject lateinit var permissionsManager: PermissionsManager
    @Inject lateinit var errorHandler: RestErrorHandler
    private val listImages = ArrayList<ImageItem>()

    init {
        DaggerPreviewImagesComponent.builder()
                .appComponent(RDWallpapersApp.graph)
                .previewImagesModule(PreviewImagesModule()).build().inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setViewPagerList()
    }

    private fun setViewPagerList() = Completable.fromAction { viewState.setList(listImages) }
            .subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.ui())
            .subscribe({ viewState.setViewPager(listImages) })
            .connect()

    private fun download(position: Int, id: String?) = previewImagesInteractor.download(id)
            .flatMap { previewImagesInteractor.saveFile(it, listImages[position].fileName) }
            .doOnSubscribe { viewState.showDownloadingDialog() }
            .doAfterTerminate { viewState.hideDownloadingDialog() }

    fun setAsWallpaper(activity: FragmentActivity, position: Int) {
        when {
            permissionsManager.hasPermissions(activity) -> {
                download(position, listImages[position].name)
                        .subscribe(
                                { viewState.setWallpaperIntent(Uri.fromFile(it)) },
                                { errorHandler.proceed(it, { viewState.showErrorSnack(it) }) }
                        )
            }
            else -> viewState.requestPermissionWithRationale()
        }
    }

    fun cropFile(activity: FragmentActivity, position: Int) {
        when {
            permissionsManager.hasPermissions(activity) -> {
                download(position, listImages[position].name)
                        .subscribe(
                                { viewState.goToCropImage(it) },
                                { errorHandler.proceed(it, { viewState.showErrorSnack(it) }) }
                        )
            }
            else -> viewState.requestPermissionWithRationale()
        }
    }

    fun shareFile(activity: FragmentActivity, position: Int) {
        when {
            permissionsManager.hasPermissions(activity) -> {
                download(position, listImages[position].name)
                        .subscribe(
                                { viewState.setShareIntent(Uri.fromFile(it)) },
                                { errorHandler.proceed(it, { viewState.showErrorSnack(it) }) }
                        )
            }
            else -> viewState.requestPermissionWithRationale()
        }
    }

    fun downloadFile(activity: FragmentActivity, position: Int) {
        when {
            permissionsManager.hasPermissions(activity) -> {
                Completable.fromObservable(download(position, listImages[position].name))
                        .subscribe({ viewState.infoSnack(R.string.image_downloaded) },
                                { errorHandler.proceed(it, { viewState.showErrorSnack(it) }) })
            }
            else -> viewState.requestPermissionWithRationale()
        }
    }

    fun makeSbNonTransparent(activity: FragmentActivity): Disposable =
            Completable.fromObservable(Observable.just(clearTransparent(activity)))
                    .subscribe(
                            { activity.supportFragmentManager.popBackStack() },
                            { errorHandler.proceed(it) }
                    )

    fun checkPermissionResult(activity: FragmentActivity, requestCode: Int, grantResults: IntArray) {
        when (permissionsManager.getPermissionResult(requestCode, grantResults)) {
            true -> activity.toast(R.string.storage_permission_granted)
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        activity.toast(R.string.storage_permission_denied)
                    else viewState.showNoStoragePermissionSnackbar()
                }
            }
        }
    }

    fun openAppSettings() = viewState.setSettingsIntent()

    fun requestPerms(activity: FragmentActivity) = permissionsManager.requestPerms(activity)

    fun checkRequestCode(activity: FragmentActivity, requestCode: Int) =
            permissionsManager.checkRequestCode(activity, requestCode)

    private fun addToFav(position: Int) {
        previewImagesInteractor.save(listImages, position).subscribe()
        viewState.infoSnack(R.string.added_to_fav)
        viewState.setMenuFavIcon(R.string.delete_from_favorites, R.drawable.ic_favorite_white)
    }

    private fun removeFav(image: String?) {
        previewImagesInteractor.delete(image).subscribe()
        viewState.infoSnack(R.string.deleted_from_fav)
        viewState.setMenuFavIcon(R.string.add_to_favorites, R.drawable.ic_favorite_border)
    }

    fun setFavListener(position: Int) {
        previewImagesInteractor.getAll().subscribe { favImages ->
            if (favImages.isNotEmpty()) {
                favImages.filter { listImages[position].image == it.image }
                        .map { removeFav(it.image) }
            }
        }
        addToFav(position)
    }

    fun setFavIcons(position: Int) {
        previewImagesInteractor.getAll().subscribe { favImages ->
            when (favImages.isNotEmpty()) {
                true -> favImages.filter { listImages[position].image == it.image }
                        .map { viewState.setMenuFavIcon(R.string.delete_from_favorites, R.drawable.ic_favorite_white) }
                else -> viewState.setMenuFavIcon(R.string.add_to_favorites, R.drawable.ic_favorite_border)
            }
        }
    }

}