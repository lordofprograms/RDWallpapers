package com.crazzylab.rdwallpapers.model.interactor

import android.support.v7.app.AppCompatActivity
import com.crazzylab.rdwallpapers.model.repository.NightModeRepository
import com.crazzylab.rdwallpapers.model.repository.ThemeRepository
import javax.inject.Inject

/**
 * Created by Михаил on 25.01.2018.
 */
class MainInteractor @Inject constructor(private val nightModeRepository: NightModeRepository,
                                         private val themeRepository: ThemeRepository) {

    fun installNightMode() = nightModeRepository.installMode()

    fun installTheme(activity: AppCompatActivity) = themeRepository.installTheme(activity)

    fun isWhiteAppTheme() = themeRepository.isWhiteAppTheme()
}