package com.crazzylab.rdwallpapers.di.modules

import android.content.Context
import com.crazzylab.rdwallpapers.model.data.server.ImageApi
import com.crazzylab.rdwallpapers.di.scopes.ImagesScope
import com.crazzylab.rdwallpapers.model.interactor.ImagesInteractor
import com.crazzylab.rdwallpapers.model.repository.ImagesRepository
import com.crazzylab.rdwallpapers.model.system.ResourceManager
import com.crazzylab.rdwallpapers.model.system.SchedulersProvider
import com.crazzylab.rdwallpapers.presentation.global.managers.RestErrorHandler
import com.crazzylab.rdwallpapers.presentation.global.managers.NetworkManager
import dagger.Module
import dagger.Provides

/**
 * Created by Михаил on 13.08.2017.
 */
@Module
class ImagesModule {

    @ImagesScope
    @Provides
    fun provideImagesRepository(api: ImageApi, errorHandler: RestErrorHandler) =
            ImagesRepository(api, errorHandler)

    @ImagesScope
    @Provides
    fun provideImagesInteractor(imagesRepository: ImagesRepository,
                                rxSchedulers: SchedulersProvider) =
            ImagesInteractor(imagesRepository, rxSchedulers)

    @ImagesScope
    @Provides
    fun provideNetworkChecker(context: Context) = NetworkManager(context)

}