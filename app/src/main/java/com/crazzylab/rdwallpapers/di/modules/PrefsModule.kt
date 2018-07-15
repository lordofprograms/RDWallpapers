package com.crazzylab.rdwallpapers.di.modules

import android.content.Context
import com.crazzylab.rdwallpapers.di.scopes.PrefsScope
import com.crazzylab.rdwallpapers.model.repository.NightModeRepository
import com.crazzylab.rdwallpapers.model.system.ResourceManager
import com.crazzylab.rdwallpapers.model.data.storage.Prefs
import com.crazzylab.rdwallpapers.model.interactor.PrefsInteractor
import com.crazzylab.rdwallpapers.model.repository.ThemeRepository
import com.crazzylab.rdwallpapers.model.system.SchedulersProvider
import com.crazzylab.rdwallpapers.presentation.prefs.PrefsPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by Михаил on 21.10.2017.
 */
@Module
class PrefsModule {

    @PrefsScope
    @Provides
    fun provideNightModeRepository(prefs: Prefs) = NightModeRepository(prefs)

    @PrefsScope
    @Provides
    fun provideThemeRepository(prefs: Prefs, resourceManager: ResourceManager) =
            ThemeRepository(prefs, resourceManager)

    @PrefsScope
    @Provides
    fun provideNightModeInteractor(nightModeRepository: NightModeRepository,
                                   themeRepository: ThemeRepository) =
            PrefsInteractor(nightModeRepository, themeRepository)

    @PrefsScope
    @Provides
    fun providePresenter(prefsInteractor: PrefsInteractor,
                         resourceManager: ResourceManager,
                         rxSchedulers: SchedulersProvider) =
            PrefsPresenter(prefsInteractor, resourceManager, rxSchedulers)

}