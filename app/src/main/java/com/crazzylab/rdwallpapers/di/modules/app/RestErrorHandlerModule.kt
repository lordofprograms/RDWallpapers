package com.crazzylab.rdwallpapers.di.modules.app

import com.crazzylab.rdwallpapers.di.scopes.AppScope
import com.crazzylab.rdwallpapers.model.system.ResourceManager
import com.crazzylab.rdwallpapers.presentation.global.managers.RestErrorHandler
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * Created by Михаил on 10.12.2017.
 */
@Module
class RestErrorHandlerModule @Inject constructor(private val resourceManager: ResourceManager) {

    @AppScope
    @Provides
    fun provideRestErrorHandler() = RestErrorHandler(resourceManager)

}