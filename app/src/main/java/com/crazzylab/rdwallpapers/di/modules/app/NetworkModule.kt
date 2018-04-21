package com.crazzylab.rdwallpapers.di.modules.app

import com.crazzylab.rdwallpapers.di.scopes.AppScope
import com.crazzylab.rdwallpapers.model.system.AppSchedulers
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Михаил on 12.08.2017.
 */
@Module
class NetworkModule {

    @AppScope
    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient().newBuilder().build()

    @AppScope
    @Provides
     fun provideRxAdapter(): RxJava2CallAdapterFactory =
            RxJava2CallAdapterFactory.createWithScheduler(AppSchedulers().INTERNET_SCHEDULERS)


    @AppScope
    @Provides
     fun provideMoshiClient(): MoshiConverterFactory = MoshiConverterFactory.create()

}