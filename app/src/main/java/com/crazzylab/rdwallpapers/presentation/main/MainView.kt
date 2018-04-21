package com.crazzylab.rdwallpapers.presentation.main

import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpView

/**
 * Created by Михаил on 30.12.2017.
 */
interface MainView: MvpView {

    fun selectItem(condition: Boolean, position: Int?)
    fun openFragment(fragment: Fragment, tag: String)

}