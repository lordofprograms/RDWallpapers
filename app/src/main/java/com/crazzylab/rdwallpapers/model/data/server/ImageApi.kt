package com.crazzylab.rdwallpapers.model.data.server

import com.crazzylab.rdwallpapers.entity.Images
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by Михаил on 12.08.2017.
 */
interface ImageApi {

    @GET
    fun getImages(@Url redirectedUrl: String/*, @Path("currentPage") currentPage: Int*/): Call<Images>

}