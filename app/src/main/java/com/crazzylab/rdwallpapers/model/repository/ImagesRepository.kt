package com.crazzylab.rdwallpapers.model.repository

import android.util.Log
import com.crazzylab.rdwallpapers.entity.Images
import com.crazzylab.rdwallpapers.model.data.server.ImageApi
import com.crazzylab.rdwallpapers.presentation.global.managers.RestErrorHandler
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Михаил on 21.01.2018.
 */
class ImagesRepository @Inject constructor(private val api: ImageApi,
                                           private val errorHandler: RestErrorHandler)  {

    fun getImages(redirectedUrl: String): Observable<Images> =
            Observable.just(api.getImages(redirectedUrl))
                    .map { it.execute() }
                    .doOnError { errorHandler.proceed(it) }
                    .filter { it.isSuccessful }
                    .map { it.body()!! }
                    .onErrorReturn { Images("", emptyList()) }
                    .map { Images(it.redirectedUrl, it.elements) }

}