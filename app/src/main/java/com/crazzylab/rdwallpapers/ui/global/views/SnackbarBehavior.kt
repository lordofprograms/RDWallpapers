package com.crazzylab.rdwallpapers.ui.global.views

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.view.View
import com.github.clans.fab.FloatingActionMenu

/**
 * Created by Михаил on 22.08.2017.
 */

class SnackbarBehavior(context: Context?, attrs: AttributeSet?) :
        CoordinatorLayout.Behavior<FloatingActionMenu>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: FloatingActionMenu?, dependency: View?): Boolean =
            dependency is Snackbar.SnackbarLayout

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: FloatingActionMenu, dependency: View): Boolean {
        val translationY = Math.min(0f, dependency.translationY.minus(dependency.height))
        child.translationY = translationY
        return true
    }
}