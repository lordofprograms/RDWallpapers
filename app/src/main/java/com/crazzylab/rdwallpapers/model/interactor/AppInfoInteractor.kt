package com.crazzylab.rdwallpapers.model.interactor

import com.crazzylab.rdwallpapers.model.repository.AppInfoRepository
import javax.inject.Inject

/**
 * Created by Михаил on 10.02.2018.
 */
class AppInfoInteractor @Inject constructor(private val appInfoRepository: AppInfoRepository) {

    fun getAppVersion() = appInfoRepository.getAppVersion()

}