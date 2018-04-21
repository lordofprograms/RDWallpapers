package com.crazzylab.rdwallpapers.di.components

import android.content.Context
import com.crazzylab.rdwallpapers.model.data.server.ImageApi
import com.crazzylab.rdwallpapers.di.modules.app.*
import com.crazzylab.rdwallpapers.di.scopes.AppScope
import com.crazzylab.rdwallpapers.model.system.SchedulersProvider
import dagger.Component

/**
 * Created by Михаил on 12.08.2017.
 */
@AppScope
@Component(modules = [(NetworkModule::class), (AppContextModule::class), (ResourceManagerModule::class),
(RestErrorHandlerModule::class), (RealmDbModule::class), (RxModule::class), (ImagesApiServiceModule::class)])
interface AppComponent {

    fun getAppContext(): Context

    fun rxSchedulers(): SchedulersProvider

    fun apiService(): ImageApi

}