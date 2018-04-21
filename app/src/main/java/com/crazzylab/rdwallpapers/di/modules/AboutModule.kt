package com.crazzylab.rdwallpapers.di.modules

import android.content.Context
import com.crazzylab.rdwallpapers.di.scopes.AboutScope
import com.crazzylab.rdwallpapers.model.interactor.AppInfoInteractor
import com.crazzylab.rdwallpapers.model.repository.AppInfoRepository
import com.crazzylab.rdwallpapers.model.system.ResourceManager
import dagger.Module
import dagger.Provides

/**
 * Created by Михаил on 10.02.2018.
 */
@Module
class AboutModule {

    @AboutScope
    @Provides
    fun provideResourceManager(context: Context) = ResourceManager(context)

    @AboutScope
    @Provides
    fun provideAppInfoRepository(resourceManager: ResourceManager) = AppInfoRepository(resourceManager)

    @AboutScope
    @Provides
    fun provideAppInfoInteractor(appInfoRepository: AppInfoRepository) = AppInfoInteractor(appInfoRepository)

}