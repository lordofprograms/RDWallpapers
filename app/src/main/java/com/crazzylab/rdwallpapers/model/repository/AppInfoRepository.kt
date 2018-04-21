package com.crazzylab.rdwallpapers.model.repository

import com.crazzylab.rdwallpapers.BuildConfig
import com.crazzylab.rdwallpapers.model.system.ResourceManager
import javax.inject.Inject

/**
 * Created by Михаил on 10.02.2018.
 */
class AppInfoRepository @Inject constructor(private val resourceManager: ResourceManager) {

    fun getAppVersion() = BuildConfig.VERSION_NAME

}