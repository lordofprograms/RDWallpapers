package com.crazzylab.rdwallpapers.model.interactor

import com.crazzylab.rdwallpapers.entity.ImageItem
import com.crazzylab.rdwallpapers.model.repository.PreviewImagesRepository
import com.crazzylab.rdwallpapers.model.repository.ThemeRepository
import com.crazzylab.rdwallpapers.model.system.SchedulersProvider
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response

import java.io.File
import javax.inject.Inject

/**
 * Created by Михаил on 21.01.2018.
 */
class PreviewImagesInteractor @Inject constructor(private val previewImagesRepository: PreviewImagesRepository,
                                                  private val themeRepository: ThemeRepository,
                                                  private val rxSchedulers: SchedulersProvider) {

    fun download(link: String?): Observable<Response<ResponseBody>> = previewImagesRepository.download(link)
            .subscribeOn(rxSchedulers.internet())
            .observeOn(rxSchedulers.ui())

    fun saveFile(response: Response<ResponseBody>, fileName: String?): Observable<File> =
            previewImagesRepository.saveFile(response, fileName)
                    .subscribeOn(rxSchedulers.io())
                    .observeOn(rxSchedulers.ui())

    fun getAll() = previewImagesRepository.getAll()

    fun isWhiteAppTheme() = themeRepository.isWhiteAppTheme()

    fun save(listImages: ArrayList<ImageItem>, position: Int) = previewImagesRepository.saveToDb(listImages, position)

    fun delete(image: String?) = previewImagesRepository.deleteFromDb(image)

}