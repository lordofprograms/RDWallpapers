package com.crazzylab.rdwallpapers.di.modules.previewImages

import com.crazzylab.rdwallpapers.di.scopes.PreviewImagesScope
import com.crazzylab.rdwallpapers.model.system.AppSchedulers
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Михаил on 22.08.2017.
 */
@Module
class PreviewImagesNetworkModule {

    @PreviewImagesScope
    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient().newBuilder().build()

    @PreviewImagesScope
    @Provides
    fun provideRxAdapter(): RxJava2CallAdapterFactory =
            RxJava2CallAdapterFactory.createWithScheduler(AppSchedulers().INTERNET_SCHEDULERS)


    @PreviewImagesScope
    @Provides
    fun provideMoshiClient(): MoshiConverterFactory = MoshiConverterFactory.create()

}