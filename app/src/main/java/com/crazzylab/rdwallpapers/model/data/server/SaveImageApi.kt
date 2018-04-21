package com.crazzylab.rdwallpapers.model.data.server

import retrofit2.http.Url
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import io.reactivex.Observable

/**
 * Created by Михаил on 21.08.2017.
 */
interface SaveImageApi {

    @GET
    fun download(@Url link: String?): Observable<Response<ResponseBody>>

}