package com.crazzylab.rdwallpapers.model.interactor

import com.crazzylab.rdwallpapers.entity.Images
import com.crazzylab.rdwallpapers.model.repository.ImagesRepository
import com.crazzylab.rdwallpapers.model.system.SchedulersProvider
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Михаил on 21.01.2018.
 */
class ImagesInteractor @Inject constructor(private val imagesRepository: ImagesRepository,
                                           private val rxSchedulers: SchedulersProvider) {

    fun getImages(redirectedUrl: String): Observable<Images> = imagesRepository.getImages(redirectedUrl)
            .subscribeOn(rxSchedulers.internet())
            .observeOn(rxSchedulers.ui())

}