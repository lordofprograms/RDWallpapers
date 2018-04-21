package com.crazzylab.rdwallpapers.model.repository

import android.support.v7.app.AppCompatActivity
import com.crazzylab.rdwallpapers.model.data.storage.Prefs
import com.crazzylab.rdwallpapers.model.system.ResourceManager
import javax.inject.Inject
import com.crazzylab.rdwallpapers.R

/**
 * Created by Михаил on 29.01.2018.
 */
class ThemeRepository @Inject constructor(private val prefs: Prefs,
                                          private val resourceManager: ResourceManager) {

    fun getTheme(): Int = prefs.getInt(resourceManager.getString(R.string.theme_key), 0)

    fun setTheme(position: Int) = prefs.put(resourceManager.getString(R.string.theme_key), position)

    fun isWhiteAppTheme() = getTheme() == 0

    fun installTheme(activity: AppCompatActivity) = when (getTheme()) {
        0 -> activity.setTheme(R.style.AppTheme)
        1 -> activity.setTheme(R.style.GreenTheme)
        2 -> activity.setTheme(R.style.PinkTheme)
        3 -> activity.setTheme(R.style.CyanTheme)
        4 -> activity.setTheme(R.style.PurpleTheme)
        5 -> activity.setTheme(R.style.OrangeTheme)
        6 -> activity.setTheme(R.style.BlueTheme)
        7 -> activity.setTheme(R.style.GreyTheme)
        else -> activity.setTheme(R.style.AppTheme)
    }

}