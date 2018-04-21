package com.crazzylab.rdwallpapers.model.interactor

import com.crazzylab.rdwallpapers.Constants
import com.crazzylab.rdwallpapers.model.repository.NightModeRepository
import com.crazzylab.rdwallpapers.model.repository.ThemeRepository
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject

/**
 * Created by Михаил on 25.01.2018.
 */
class PrefsInteractor @Inject constructor(private val nightModeRepository: NightModeRepository,
                                          private val themeRepository: ThemeRepository) {

    fun getTheme() = themeRepository.getTheme()

    fun setTheme(position: Int) = themeRepository.setTheme(position)

    fun isAppTheme() = themeRepository.isWhiteAppTheme()

    fun getNightMode() = nightModeRepository.getMode()

    fun setNightMode(position: Int) = nightModeRepository.setMode(position)

    fun isNightModeEnabled() = nightModeRepository.isNightModeEnabled()

    fun enableNotifications() = FirebaseMessaging.getInstance().subscribeToTopic(Constants.FCM_TOPIC)

    fun disEnableNotifications() = FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.FCM_TOPIC)

}