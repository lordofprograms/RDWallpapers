package com.crazzylab.rdwallpapers.model.system

import io.reactivex.Scheduler

/**
 * Created by Михаил on 09.08.2017.
 */
interface SchedulersProvider {

    fun runOnBackground(): Scheduler
    fun io(): Scheduler
    fun compute(): Scheduler
    fun ui(): Scheduler
    fun internet(): Scheduler

}