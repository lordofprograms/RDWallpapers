package com.crazzylab.rdwallpapers.model.repository

import android.util.Log
import com.crazzylab.rdwallpapers.entity.Images
import com.crazzylab.rdwallpapers.model.data.server.ImageApi
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Михаил on 21.01.2018.
 */
class ImagesRepository @Inject constructor(private val api: ImageApi)  {

    // try to write logs for finding time of work to solve this bug
    fun getImages(redirectedUrl: String/*, currentPage: Int*/): Observable<Images> {
        return Observable.create {
            val callResponse = api.getImages(redirectedUrl/*, currentPage*/)
            Log.d("Retrofit", "Before making response executed")
            val response = callResponse.execute()

            when (response.isSuccessful) {
                true -> {
                    Log.d("Retrofit", "Response is successful")
                    val item = response.body()!!
                    it.onNext(Images(item.redirectedUrl, item.elements))
                    it.onComplete()
                }
                else -> {
                    Log.d("Retrofit", "Error on response")
                    it.onError(Throwable(response.message()))
                }
            }
        }
    }



}