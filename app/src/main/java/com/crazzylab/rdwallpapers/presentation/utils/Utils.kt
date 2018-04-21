package com.crazzylab.rdwallpapers.presentation.utils

import android.app.Activity
import android.graphics.Rect
import android.os.Build
import android.support.design.widget.AppBarLayout
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import timber.log.Timber


/**
 * Created by Михаил on 09.08.2017.
 */

fun handleThrowable(throwable: Throwable) {
    Timber.e(throwable, throwable.toString())
}

fun snackbar(view: View, text: String, actionText: String = "",
             listener: (View) -> Unit = {}, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, text, length).setAction(actionText, listener).show()
}

fun scrollTop(rv: RecyclerView, appBar: AppBarLayout) {
    rv.smoothScrollToPosition(0)
    appBar.setExpanded(true, true)
}

fun getStatusBarHeight(activity: Activity): Int {
    val rectangle = Rect()
    val window = activity.window
    window.decorView.getWindowVisibleDisplayFrame(rectangle)
    val statusBarHeight = rectangle.top
    val contentView: View = window.findViewById(Window.ID_ANDROID_CONTENT)
    val contentViewTop = contentView.top
    return contentViewTop - statusBarHeight
}

fun getSbHeight(activity: Activity): Int {
    var paddingTop = getStatusBarHeight(activity)
    val tv = TypedValue()
    activity.theme.resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true)
    paddingTop += TypedValue.complexToDimensionPixelSize(tv.data, activity.resources.displayMetrics)
    return paddingTop
}

fun makeTransparent(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
}

fun clearTransparent(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
}
