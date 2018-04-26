package com.crazzylab.rdwallpapers.presentation.images

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.crazzylab.rdwallpapers.RDWallpapersApp
import com.crazzylab.rdwallpapers.di.components.DaggerImagesComponent
import com.crazzylab.rdwallpapers.di.modules.ImagesModule
import com.crazzylab.rdwallpapers.presentation.global.managers.Paginator
import com.crazzylab.rdwallpapers.presentation.global.managers.RestErrorHandler
import com.crazzylab.rdwallpapers.entity.ImageItem
import com.crazzylab.rdwallpapers.model.interactor.ImagesInteractor
import com.crazzylab.rdwallpapers.Constants
import com.crazzylab.rdwallpapers.model.system.SchedulersProvider
import com.crazzylab.rdwallpapers.presentation.global.BasePresenter
import com.crazzylab.rdwallpapers.presentation.global.managers.NetworkManager
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by Михаил on 13.08.2017.
 */
@InjectViewState
class ImagesPresenter : BasePresenter<ImagesView>() {

    @Inject lateinit var imagesInteractor: ImagesInteractor
    @Inject lateinit var rxSchedulers: SchedulersProvider
    @Inject lateinit var errorHandler: RestErrorHandler
    @Inject lateinit var networkManager: NetworkManager
    private lateinit var broadcastReceiver: BroadcastReceiver
    private val publishSubject = PublishSubject.create<Boolean>()
    private var redirectedUrl: String = Constants.REDIRECTED_URL

    init {
        DaggerImagesComponent.builder()
                .appComponent(RDWallpapersApp.graph)
                .imagesModule(ImagesModule()).build().inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        showContent()
        checkConnection()
        refreshImages()
    }

    private val paginator = Paginator({ requestImages(redirectedUrl) },
            object : Paginator.ViewController<ImageItem> {

                override fun showEmptyProgress(show: Boolean) {
                    viewState.showEmptyProgress(show)
                }

                override fun showEmptyError(show: Boolean, error: Throwable?) {
                    if (error != null) {
                        errorHandler.proceed(error, { viewState.showEmptyError(show, it) })
                    } else viewState.showEmptyError(show, null)
                }

                override fun showErrorMessage(error: Throwable) {
                    if(redirectedUrl != "") errorHandler.proceed(error, { viewState.showMessage(it) })
                }

                override fun showEmptyView(show: Boolean) {
                    viewState.showEmptyView(show)
                }

                override fun showData(show: Boolean, data: List<ImageItem>) {
                    viewState.showImages(show, data)
                }

                override fun showRefreshProgress(show: Boolean) {
                    viewState.showRefreshProgress(show)
                }

                override fun showPageProgress(show: Boolean) {
                    viewState.showPageProgress(show)
                }
            }
    )

    fun refreshImages() {
        if(redirectedUrl == Constants.REDIRECTED_URL) paginator.refresh()
        else viewState.showRefreshProgress(false)
    }

    fun loadNextImagesPage() {
        if(redirectedUrl != "") paginator.loadNewPage()
        else viewState.showPageProgress(false)
    }

    private fun requestImages(redirectedUrl: String): Observable<List<ImageItem>> =
            imagesInteractor.getImages(redirectedUrl)
                    .doOnNext { this.redirectedUrl = it.redirectedUrl }
                    .map { it.elements }

    fun goToOnline(){
        viewState.showReloading()
        viewState.showContent(true)
    }

    fun registerReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                publishSubject.onNext(networkManager.isInternetOn())
            }
        }
        networkManager.registerReceiver(broadcastReceiver)
    }

    fun unregisterReceiver() = networkManager.unregisterReceiver(broadcastReceiver)

    private fun showContent() = networkManager.isInternetOnObservable()
            .subscribe(
                    { viewState.showContent(it) },
                    { errorHandler.proceed(it, { viewState.showMessage(it) }) }
            )

    private fun checkConnection() {
        publishSubject.startWith(networkManager.isInternetOn())
                .distinctUntilChanged()
                .skip(1)
                .subscribeOn(rxSchedulers.internet())
                .observeOn(rxSchedulers.ui())
                .subscribe { viewState.showConnectionSnack(it) }
                .connect()
    }

    override fun onDestroy() {
        super.onDestroy()
        paginator.release()
    }

}