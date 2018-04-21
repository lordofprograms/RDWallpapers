package com.crazzylab.rdwallpapers.model.system

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * Created by Михаил on 09.08.2017.
 */
class AppSchedulers : SchedulersProvider {

    val backgroundExecutor: ExecutorService = Executors.newCachedThreadPool()
    val BACKGROUND_SCHEDULERS: Scheduler = Schedulers.from(backgroundExecutor)
    val internetExecutor: ExecutorService = Executors.newCachedThreadPool()
    val INTERNET_SCHEDULERS: Scheduler = Schedulers.from(internetExecutor)

    override fun runOnBackground(): Scheduler = BACKGROUND_SCHEDULERS

    override fun io(): Scheduler = Schedulers.io()

    override fun compute(): Scheduler = Schedulers.computation()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    override fun internet(): Scheduler = INTERNET_SCHEDULERS

}