package com.crazzylab.rdwallpapers.model.repository

import android.os.Environment
import com.crazzylab.rdwallpapers.model.data.db.models.ImageModel
import com.crazzylab.rdwallpapers.entity.ImageItem
import com.crazzylab.rdwallpapers.model.data.db.DbService
import com.crazzylab.rdwallpapers.model.data.server.SaveImageApi
import io.reactivex.Observable
import io.realm.RealmObject
import okhttp3.ResponseBody
import okio.Okio
import retrofit2.Response
import java.io.File
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Михаил on 21.01.2018.
 */
class PreviewImagesRepository @Inject constructor(private val saveApi: SaveImageApi,
                                                  private val dbService: DbService) {

    fun download(link: String?): Observable<Response<ResponseBody>> = saveApi.download(link)

    fun saveFile(response: Response<ResponseBody>, fileName: String?): Observable<File> {
        return Observable.create { subscriber ->
            try {
                val directory = File(Environment.getExternalStorageDirectory(), File.separator + "RDWallpapers")
                if (!directory.exists()) directory.mkdirs()
                createImageFile(directory, fileName).subscribe { file ->
                    with(Okio.buffer(Okio.sink(file))){
                        response.body()?.let { writeAll(it.source()) }
                        close()
                    }
                    subscriber.onNext(file)
                    subscriber.onComplete()
                }
            }
            catch (e: IOException) {
                e.printStackTrace()
                subscriber.onError(e)
            }
        }
    }

    private fun createImageFile(storageDir: File, fileName: String?): Observable<File> =
            Observable.just(File(storageDir, "$fileName.jpg"))

    fun getAll() = dbService.getAll(ImageModel::class.java)

    fun saveToDb(listImages: List<ImageItem>, position: Int): Observable<RealmObject> {
        val model = ImageModel()
        model.image = listImages[position].image
        model.name = listImages[position].name
        model.fileName = listImages[position].fileName
        return dbService.save(model, ImageModel::class.java)
    }

    fun deleteFromDb(image: String?) = dbService.delete(ImageModel::class.java, image)

}