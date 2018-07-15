package com.crazzylab.rdwallpapers.di.modules.app

import com.crazzylab.rdwallpapers.di.scopes.AppScope
import com.crazzylab.rdwallpapers.model.data.db.DbService
import com.crazzylab.rdwallpapers.model.system.SchedulersProvider
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * Created by Михаил on 26.01.2018.
 */
@Module
class DbServiceModule {

    @AppScope
    @Provides
    fun provideDbService(rxSchedulers: SchedulersProvider) = DbService(rxSchedulers)

}