package com.crazzylab.rdwallpapers.model.repository

import android.content.res.Configuration
import android.support.v7.app.AppCompatDelegate
import com.crazzylab.rdwallpapers.model.data.storage.Prefs
import com.crazzylab.rdwallpapers.Constants
import javax.inject.Inject

/**
 * Created by Михаил on 24.10.2017.
 */
class NightModeRepository @Inject constructor(private val prefs: Prefs) {

    fun getMode(): Int = prefs.getInt(Constants.NIGHT_MODE_KEY)

    fun isNightModeEnabled(): Boolean = (prefs.context.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

    fun setMode(position: Int) = prefs.put(Constants.NIGHT_MODE_KEY, modeList(position))

    fun installMode() = modeList(getMode())

    private fun modeList(position: Int): Int {
        when (position) {
            0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        return position
    }

}