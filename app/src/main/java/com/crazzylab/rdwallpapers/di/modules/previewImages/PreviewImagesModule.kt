package com.crazzylab.rdwallpapers.di.modules.previewImages

import android.content.Context
import com.crazzylab.rdwallpapers.model.data.server.SaveImageApi
import com.crazzylab.rdwallpapers.model.data.db.DbService
import com.crazzylab.rdwallpapers.di.scopes.PreviewImagesScope
import com.crazzylab.rdwallpapers.model.data.storage.Prefs
import com.crazzylab.rdwallpapers.model.interactor.PreviewImagesInteractor
import com.crazzylab.rdwallpapers.model.repository.PreviewImagesRepository
import com.crazzylab.rdwallpapers.model.repository.ThemeRepository
import com.crazzylab.rdwallpapers.presentation.global.managers.permissions.PermissionsManager
import com.crazzylab.rdwallpapers.model.system.ResourceManager
import com.crazzylab.rdwallpapers.model.system.SchedulersProvider
import com.crazzylab.rdwallpapers.presentation.global.managers.RestErrorHandler
import dagger.Module
import dagger.Provides

/**
 * Created by Михаил on 22.08.2017.
 */
@Module
class PreviewImagesModule {

    @PreviewImagesScope
    @Provides
    fun providePreviewImagesRepository(saveImageApi: SaveImageApi, dbService: DbService) =
            PreviewImagesRepository(saveImageApi, dbService)

    @PreviewImagesScope
    @Provides
    fun providePrefs(context: Context) = Prefs(context)

    @PreviewImagesScope
    @Provides
    fun provideResourceManager(context: Context) = ResourceManager(context)

    @PreviewImagesScope
    @Provides
    fun provideRestErrorHandler(resourceManager: ResourceManager) = RestErrorHandler(resourceManager)

    @PreviewImagesScope
    @Provides
    fun provideThemeRepository(prefs: Prefs, resourceManager: ResourceManager) =
            ThemeRepository(prefs, resourceManager)

    @PreviewImagesScope
    @Provides
    fun providePreviewImagesInteractor(previewImagesRepository: PreviewImagesRepository,
                                       themeRepository: ThemeRepository,
                                       rxSchedulers: SchedulersProvider) =
            PreviewImagesInteractor(previewImagesRepository, themeRepository, rxSchedulers)

    @PreviewImagesScope
    @Provides
    fun providePermissionsManager() = PermissionsManager()

}