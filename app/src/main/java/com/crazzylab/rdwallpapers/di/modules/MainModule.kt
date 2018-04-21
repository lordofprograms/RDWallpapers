package com.crazzylab.rdwallpapers.di.modules

import android.content.Context
import com.crazzylab.rdwallpapers.di.scopes.MainScope
import com.crazzylab.rdwallpapers.model.repository.NightModeRepository
import com.crazzylab.rdwallpapers.model.data.storage.Prefs
import com.crazzylab.rdwallpapers.model.interactor.MainInteractor
import com.crazzylab.rdwallpapers.model.repository.ThemeRepository
import com.crazzylab.rdwallpapers.model.system.ResourceManager
import dagger.Module
import dagger.Provides

/**
 * Created by Михаил on 30.12.2017.
 */
@Module
class MainModule {

    @MainScope
    @Provides
    fun provideResourceManager(context: Context) = ResourceManager(context)

    @MainScope
    @Provides
    fun providePrefs(context: Context) = Prefs(context)

    @MainScope
    @Provides
    fun provideNightModeManager(prefs: Prefs) = NightModeRepository(prefs)

    @MainScope
    @Provides
    fun provideThemeRepository(prefs: Prefs, resourceManager: ResourceManager) =
            ThemeRepository(prefs, resourceManager)

    @MainScope
    @Provides
    fun provideMainInteractor(nightModeRepository: NightModeRepository,
                              themeRepository: ThemeRepository) =
            MainInteractor(nightModeRepository, themeRepository)

}