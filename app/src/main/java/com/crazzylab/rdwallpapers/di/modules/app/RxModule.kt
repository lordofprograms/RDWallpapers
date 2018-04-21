package com.crazzylab.rdwallpapers.di.modules.app

import com.crazzylab.rdwallpapers.di.scopes.AppScope
import com.crazzylab.rdwallpapers.model.system.AppSchedulers
import com.crazzylab.rdwallpapers.model.system.SchedulersProvider
import dagger.Module
import dagger.Provides

/**
 * Created by Михаил on 12.08.2017.
 */
@Module
class RxModule {

    @AppScope
    @Provides
    fun provideRxSchedulers(): SchedulersProvider = AppSchedulers()

}