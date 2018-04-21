package com.crazzylab.rdwallpapers.model.data.db

import com.crazzylab.rdwallpapers.model.data.db.models.ImageModel
import com.crazzylab.rdwallpapers.model.system.SchedulersProvider
import io.realm.Realm
import io.realm.RealmObject
import io.reactivex.Observable
import io.realm.RealmResults
import javax.inject.Inject


/**
 * Created by Михаил on 24.08.2017.
 */
class DbService @Inject constructor(private val rxSchedulers: SchedulersProvider) {

    fun <T : RealmObject> save(`object`: T, clazz: Class<T>): Observable<RealmObject> {
        val realm = Realm.getDefaultInstance()

        val id: Long = try { (realm.where(clazz).max("id")!!.toInt() + 1).toLong() }
        catch (e: Exception) { 0L }

        (`object` as ImageModel).id = id

        return Observable.just(`object`)
                .subscribeOn(rxSchedulers.compute())
                .observeOn(rxSchedulers.ui())
                .flatMap { t ->
                    Observable.just(t)
                            .doOnSubscribe { realm.beginTransaction() }
                            .doOnComplete {
                                realm.commitTransaction()
                                realm.close()
                            }
                            .doOnNext { realm.copyToRealm(it) }
                }
    }

    fun <T : RealmObject> getAll(clazz: Class<T>): Observable<RealmResults<T>> {
        val realm = Realm.getDefaultInstance()

        return Observable.just(clazz)
                .subscribeOn(rxSchedulers.compute())
                .observeOn(rxSchedulers.ui())
                .flatMap { receivedClass ->
                    Observable.just(receivedClass)
                            .doOnSubscribe { realm.beginTransaction() }
                            .doOnComplete { realm.commitTransaction() }
                            .map { realm.where(it).findAll() }
                }
    }

    fun <T : RealmObject> delete(clazz: Class<T>, image: String?): Observable<Unit> {
        val realm = Realm.getDefaultInstance()

        return Observable.just(realm.where(clazz).equalTo("image", image).findAll())
                .subscribeOn(rxSchedulers.compute())
                .observeOn(rxSchedulers.ui())
                .flatMap { images ->
                    Observable.fromIterable(images)
                            .doOnSubscribe { realm.beginTransaction() }
                            .doOnComplete {
                                realm.commitTransaction()
                                realm.close()
                            }
                            .map { it.deleteFromRealm() }
                }
    }

    fun <T : RealmObject> deleteAll(clazz: Class<T>): Observable<Boolean> {
        val realm = Realm.getDefaultInstance()

        return Observable.just(clazz)
                .subscribeOn(rxSchedulers.compute())
                .observeOn(rxSchedulers.ui())
                .flatMap { receivedClass ->
                    Observable.just(receivedClass)
                            .doOnSubscribe { realm.beginTransaction() }
                            .doOnComplete {
                                realm.commitTransaction()
                                realm.close()
                            }
                            .map { realm.where(it).findAll().deleteAllFromRealm() }
                }
    }


}