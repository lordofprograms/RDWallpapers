package com.crazzylab.rdwallpapers

import android.app.Application
import com.crazzylab.rdwallpapers.di.components.AppComponent
import com.crazzylab.rdwallpapers.di.modules.app.AppContextModule
import com.crazzylab.rdwallpapers.di.components.DaggerAppComponent
import com.crazzylab.rdwallpapers.model.data.db.migration.RealmImageMigration
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

/**
 * Created by Михаил on 09.08.2017.
 */
class RDWallpapersApp : Application() {

    companion object {
        lateinit var INSTANCE: RDWallpapersApp
        lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initializeLogger()
        initAppComponent()
        initRealmConfig()
    }

    private fun initAppComponent(){
        graph = DaggerAppComponent.builder().appContextModule(AppContextModule(this)).build()
    }

    private fun initRealmConfig(){
        Realm.init(this)
         val config = RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(RealmImageMigration())
                .build()
        Realm.setDefaultConfiguration(config)
    }

    private fun initializeLogger(){
        when{
            BuildConfig.DEBUG -> Timber.plant(Timber.DebugTree())
            else -> {
                Timber.plant(object : Timber.Tree() {
                    override fun log(priority: Int, tag: String, message: String, t: Throwable) {
                        //TODO  decide what to log in release version
                    }
                })
            }
        }
    }

}