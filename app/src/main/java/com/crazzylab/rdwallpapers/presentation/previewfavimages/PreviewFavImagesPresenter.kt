package com.crazzylab.rdwallpapers.presentation.previewfavimages

import android.Manifest
import android.net.Uri
import android.os.Build
import android.support.v4.app.FragmentActivity
import com.arellomobile.mvp.InjectViewState
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.RDWallpapersApp
import com.crazzylab.rdwallpapers.model.data.db.models.ImageModel
import com.crazzylab.rdwallpapers.di.components.DaggerPreviewImagesComponent
import com.crazzylab.rdwallpapers.di.modules.previewImages.PreviewImagesModule
import com.crazzylab.rdwallpapers.extensions.toast
import com.crazzylab.rdwallpapers.model.interactor.PreviewImagesInteractor
import com.crazzylab.rdwallpapers.model.system.SchedulersProvider
import com.crazzylab.rdwallpapers.presentation.global.BasePresenter
import com.crazzylab.rdwallpapers.presentation.global.managers.RestErrorHandler
import com.crazzylab.rdwallpapers.presentation.global.managers.permissions.PermissionsManager
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by Михаил on 03.10.2017.
 */
@InjectViewState
class PreviewFavImagesPresenter : BasePresenter<PreviewFavImagesView>() {

    @Inject lateinit var previewFavImagesInteractor: PreviewImagesInteractor
    @Inject lateinit var rxSchedulers: SchedulersProvider
    @Inject lateinit var permissionsManager: PermissionsManager
    @Inject lateinit var errorHandler: RestErrorHandler
    private val listImages = ArrayList<ImageModel>()

    init {
        DaggerPreviewImagesComponent.builder()
                .appComponent(RDWallpapersApp.graph)
                .previewImagesModule(PreviewImagesModule()).build().inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getViewPagerList()
    }

    private fun getViewPagerList() = previewFavImagesInteractor.getAll()
            .filter { listImages.isEmpty() }
            .map { listImages.addAll(it) }
            .subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.ui())
            .subscribe { viewState.setViewPager(listImages) }
            .connect()

    private fun download(position: Int, id: String?) = previewFavImagesInteractor.download(id)
            .flatMap { previewFavImagesInteractor.saveFile(it, listImages[position].fileName) }
            .doOnSubscribe{ viewState.showDownloadingDialog() }
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

    private fun isWhiteAppTheme() = previewFavImagesInteractor.isWhiteAppTheme()

    fun makeSbNonTransparent(activity: FragmentActivity): Disposable =
            Completable.fromObservable(nonTransparent(activity))
                    .subscribe(
                            { activity.supportFragmentManager.popBackStack() },
                            { errorHandler.proceed(it) }
                    )

    fun makeSbNonTransparentWithSnack(activity: FragmentActivity): Disposable =
            Observable.just(nonTransparent(activity))
                    .subscribe(
                            { viewState.goToFavImages(isWhiteAppTheme()) },
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

    fun setFavListener(position: Int) {
        removeFav(listImages[position].image)
        viewState.setPositionAfterDeleting(listImages, position)
    }

    private fun removeFav(image: String?) {
        deleteFromDb(image)
        viewState.infoSnack(R.string.deleted_from_fav)
    }

    private fun deleteFromDb(image: String?) {
        previewFavImagesInteractor.delete(image).subscribe({}, { errorHandler.proceed(it) })
        viewState.removeView(listImages)
    }

}