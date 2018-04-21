package com.crazzylab.rdwallpapers.di.modules.app

import android.content.Context
import com.crazzylab.rdwallpapers.di.scopes.AppScope
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * Created by Михаил on 12.08.2017.
 */
@Module
class AppContextModule @Inject constructor(private val context: Context) {

    @AppScope
    @Provides
    fun provideAppContext(): Context = context

}