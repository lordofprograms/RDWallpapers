package com.crazzylab.rdwallpapers.di.components

import android.content.Context
import com.crazzylab.rdwallpapers.model.data.server.ImageApi
import com.crazzylab.rdwallpapers.di.modules.app.*
import com.crazzylab.rdwallpapers.di.scopes.AppScope
import com.crazzylab.rdwallpapers.model.data.db.DbService
import com.crazzylab.rdwallpapers.model.data.storage.Prefs
import com.crazzylab.rdwallpapers.model.system.ResourceManager
import com.crazzylab.rdwallpapers.model.system.SchedulersProvider
import com.crazzylab.rdwallpapers.presentation.global.managers.RestErrorHandler
import dagger.Component

/**
 * Created by Михаил on 12.08.2017.
 */
@AppScope
@Component(modules = [(NetworkModule::class), (AppContextModule::class), (ResourceManagerModule::class),
    (PrefsAppModule::class), (DbServiceModule:: class), (RestErrorHandlerModule::class), (RealmDbModule::class),
    (RxModule::class), (ImagesApiServiceModule::class)])
interface AppComponent {

    fun getAppContext(): Context

    fun getRxSchedulers(): SchedulersProvider

    fun getApiService(): ImageApi

    fun getResourceManager(): ResourceManager

    fun getDbService(): DbService

    fun getPrefs(): Prefs

    fun getRestErrorHandler(): RestErrorHandler

}