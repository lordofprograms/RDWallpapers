package com.crazzylab.rdwallpapers.di.modules.app

import android.content.Context
import com.crazzylab.rdwallpapers.di.scopes.AppScope
import com.crazzylab.rdwallpapers.model.data.storage.Prefs
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class PrefsAppModule {

    @AppScope
    @Provides
    fun providePrefs(context: Context) = Prefs(context)

}