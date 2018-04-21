package com.crazzylab.rdwallpapers.di.modules.app

import android.content.Context
import com.crazzylab.rdwallpapers.di.scopes.AppScope
import com.crazzylab.rdwallpapers.model.system.ResourceManager
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * Created by Михаил on 10.12.2017.
 */
@Module
class ResourceManagerModule @Inject constructor(private val context: Context) {

    @AppScope
    @Provides
    fun provideResourceManager() = ResourceManager(context)

}