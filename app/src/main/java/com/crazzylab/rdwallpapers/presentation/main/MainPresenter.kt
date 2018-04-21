package com.crazzylab.rdwallpapers.presentation.main

import android.support.v7.app.AppCompatActivity
import com.arellomobile.mvp.MvpPresenter
import com.crazzylab.rdwallpapers.RDWallpapersApp
import com.crazzylab.rdwallpapers.di.components.DaggerMainComponent
import com.crazzylab.rdwallpapers.di.modules.MainModule
import com.crazzylab.rdwallpapers.model.interactor.MainInteractor
import javax.inject.Inject

/**
 * Created by Михаил on 30.12.2017.
 */
class MainPresenter: MvpPresenter<MainView>() {

    @Inject lateinit var mainInteractor: MainInteractor

    init {
        DaggerMainComponent.builder()
                .appComponent(RDWallpapersApp.graph)
                .mainModule(MainModule()).build().inject(this)
    }

    fun setModes(activity: AppCompatActivity){
        installNightMode()
        installTheme(activity)
    }

    fun isWhiteAppTheme() = mainInteractor.isWhiteAppTheme()

    private fun installNightMode() = mainInteractor.installNightMode()

    private fun installTheme(activity: AppCompatActivity) = mainInteractor.installTheme(activity)

    override fun onDestroy() {
        super.onDestroy()
    }
}