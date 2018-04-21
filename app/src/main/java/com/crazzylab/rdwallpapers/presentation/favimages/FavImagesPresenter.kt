package com.crazzylab.rdwallpapers.presentation.favimages

import com.arellomobile.mvp.InjectViewState
import com.crazzylab.rdwallpapers.RDWallpapersApp
import com.crazzylab.rdwallpapers.model.data.db.models.ImageModel
import com.crazzylab.rdwallpapers.di.components.DaggerFavImagesComponent
import com.crazzylab.rdwallpapers.di.modules.FavImagesModule
import com.crazzylab.rdwallpapers.model.interactor.FavImagesInteractor
import com.crazzylab.rdwallpapers.presentation.global.managers.RestErrorHandler
import com.crazzylab.rdwallpapers.presentation.global.BasePresenter
import javax.inject.Inject

/**
 * Created by Михаил on 27.08.2017.
 */
@InjectViewState
class FavImagesPresenter : BasePresenter<FavImagesView>() {

    @Inject lateinit var favImagesInteractor: FavImagesInteractor
    @Inject lateinit var errorHandler: RestErrorHandler
    private val favList = ArrayList<ImageModel>()

    init {
        DaggerFavImagesComponent.builder()
                .appComponent(RDWallpapersApp.graph)
                .favImagesModule(FavImagesModule()).build().inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getFavImagesList()
    }

    private fun getFavImagesList() =
            favImagesInteractor.getAll().subscribe(
                    { setFavImagesList(favList, it) },
                    { errorHandler.proceed(it, { viewState.showErrorSnack(it) }) },
                    { viewState.showEmpty(favList) }
            ).connect()

    private fun setFavImagesList(emptyList: ArrayList<ImageModel>, dbList: List<ImageModel>) {
        emptyList.addAll(dbList)
        viewState.setList(emptyList)
    }

}