package com.crazzylab.rdwallpapers.di.modules

import android.content.Context
import com.crazzylab.rdwallpapers.model.data.db.DbService
import com.crazzylab.rdwallpapers.di.scopes.FavImagesScope
import com.crazzylab.rdwallpapers.di.scopes.ImagesScope
import com.crazzylab.rdwallpapers.model.interactor.FavImagesInteractor
import com.crazzylab.rdwallpapers.model.repository.FavImagesRepository
import com.crazzylab.rdwallpapers.model.system.ResourceManager
import com.crazzylab.rdwallpapers.presentation.global.managers.RestErrorHandler
import dagger.Module
import dagger.Provides

/**
 * Created by Михаил on 27.08.2017.
 */
@Module
class FavImagesModule {

    @FavImagesScope
    @Provides
    fun provideFavImagesRepository(dbService: DbService) = FavImagesRepository(dbService)

    @FavImagesScope
    @ImagesScope
    fun provideFavImagesInteractor(favImagesRepository: FavImagesRepository) = FavImagesInteractor(favImagesRepository)

}