package com.crazzylab.rdwallpapers.di.modules

import android.content.Context
import com.crazzylab.rdwallpapers.di.scopes.CropImageScope
import com.crazzylab.rdwallpapers.model.system.ResourceManager
import com.crazzylab.rdwallpapers.presentation.global.managers.RestErrorHandler
import dagger.Module
import dagger.Provides

/**
 * Created by Михаил on 22.02.2018.
 */
@Module
class CropImageModule {

    @CropImageScope
    @Provides
    fun provideResourceManager(context: Context) = ResourceManager(context)

    @CropImageScope
    @Provides
    fun provideRestErrorHandler(resourceManager: ResourceManager) = RestErrorHandler(resourceManager)


}