package com.crazzylab.rdwallpapers.di.modules.previewImages

import com.crazzylab.rdwallpapers.model.data.server.SaveImageApi
import com.crazzylab.rdwallpapers.di.scopes.PreviewImagesScope
import com.crazzylab.rdwallpapers.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Михаил on 22.08.2017.
 */
@Module
class SaveImagesApiServiceModule {

    @PreviewImagesScope
    @Provides
    fun provideSaveImageApiService(client: OkHttpClient,
                                   moshiConverterFactory: MoshiConverterFactory,
                                   rxAdapter: RxJava2CallAdapterFactory): SaveImageApi {
        val retrofit = Retrofit.Builder().client(client)
                .baseUrl(Constants.IMAGES_BASE_URL).addConverterFactory(moshiConverterFactory)
                .addCallAdapterFactory(rxAdapter)
                .build()
        return retrofit.create(SaveImageApi::class.java)
    }

}