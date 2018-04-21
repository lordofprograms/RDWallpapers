package com.crazzylab.rdwallpapers.presentation.about

import android.support.annotation.StringRes
import com.arellomobile.mvp.MvpView

/**
 * Created by Михаил on 10.02.2018.
 */
interface AboutView: MvpView {

    fun setToolbar()
    fun setVersionText(version: String)
    fun setDevelopersText(developersText: String)
    fun setGoalText(goal: String)
    fun sendFeedbackEmailIntent(@StringRes toId: Int, @StringRes subjectId: Int)
    fun openUrlIntent(@StringRes urlId: Int)
    fun setLinksListeners()

}