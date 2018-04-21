package com.crazzylab.rdwallpapers.model.repository

import com.crazzylab.rdwallpapers.model.data.db.models.ImageModel
import com.crazzylab.rdwallpapers.model.data.db.DbService
import javax.inject.Inject

/**
 * Created by Михаил on 21.01.2018.
 */
class FavImagesRepository @Inject constructor(private val dbService: DbService) {

    fun getAll() = dbService.getAll(ImageModel::class.java)

}