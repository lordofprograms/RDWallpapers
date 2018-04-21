package com.crazzylab.rdwallpapers.di.modules.app

import com.crazzylab.rdwallpapers.model.data.server.ImageApi
import com.crazzylab.rdwallpapers.di.scopes.AppScope
import com.crazzylab.rdwallpapers.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Михаил on 12.08.2017.
 */
@Module
class ImagesApiServiceModule {

    @AppScope
    @Provides
    fun provideApiService(client: OkHttpClient,
                          moshiConverterFactory: MoshiConverterFactory,
                          rxAdapter: RxJava2CallAdapterFactory): ImageApi {
        val retrofit = Retrofit.Builder().client(client)
                .baseUrl(Constants.BASE_URL).addConverterFactory(moshiConverterFactory)
                .addCallAdapterFactory(rxAdapter).build()

        return retrofit.create(ImageApi::class.java)
    }

}