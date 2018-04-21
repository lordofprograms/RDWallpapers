package com.crazzylab.rdwallpapers.model.interactor

import com.crazzylab.rdwallpapers.model.repository.FavImagesRepository
import javax.inject.Inject

/**
 * Created by Михаил on 21.01.2018.
 */
class FavImagesInteractor @Inject constructor(private val favImagesRepository: FavImagesRepository) {

    fun getAll() = favImagesRepository.getAll()

}