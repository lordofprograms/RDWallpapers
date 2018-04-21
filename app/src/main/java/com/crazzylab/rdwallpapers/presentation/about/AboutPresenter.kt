package com.crazzylab.rdwallpapers.presentation.about

import android.support.annotation.StringRes
import com.arellomobile.mvp.InjectViewState
import com.crazzylab.rdwallpapers.RDWallpapersApp
import com.crazzylab.rdwallpapers.di.components.DaggerAboutComponent
import com.crazzylab.rdwallpapers.di.modules.AboutModule
import com.crazzylab.rdwallpapers.model.interactor.AppInfoInteractor
import com.crazzylab.rdwallpapers.model.system.SchedulersProvider
import com.crazzylab.rdwallpapers.presentation.global.BasePresenter
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Михаил on 10.02.2018.
 */
@InjectViewState
class AboutPresenter: BasePresenter<AboutView>() {

    @Inject lateinit var appInfoInteractor: AppInfoInteractor
    @Inject lateinit var rxSchedulers: SchedulersProvider

    init {
        DaggerAboutComponent.builder()
                .appComponent(RDWallpapersApp.graph)
                .aboutModule(AboutModule()).build().inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setVersionText(appInfoInteractor.getAppVersion())
    }


    fun openUrl(@StringRes urlId: Int): Observable<Unit> = Observable.just(viewState.openUrlIntent(urlId))
            .subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.ui())

    fun sendFeedback(@StringRes toId: Int, @StringRes subjectId: Int): Observable<Unit> =
            Observable.just(viewState.sendFeedbackEmailIntent(toId, subjectId))
                    .subscribeOn(rxSchedulers.io())
                    .observeOn(rxSchedulers.ui())

}