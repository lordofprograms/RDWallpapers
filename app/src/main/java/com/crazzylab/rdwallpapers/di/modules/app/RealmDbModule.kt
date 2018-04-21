package com.crazzylab.rdwallpapers.di.modules.app

import com.crazzylab.rdwallpapers.di.scopes.AppScope
import dagger.Module
import dagger.Provides
import io.realm.Realm

/**
 * Created by Михаил on 29.08.2017.
 */
@Module
class RealmDbModule {

    @AppScope
    @Provides
    fun provideRealm(): Realm = Realm.getDefaultInstance()

}