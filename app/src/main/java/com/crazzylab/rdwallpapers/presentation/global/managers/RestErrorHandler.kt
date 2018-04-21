package com.crazzylab.rdwallpapers.presentation.global.managers

import android.util.Log
import com.crazzylab.rdwallpapers.model.system.ResourceManager
import com.crazzylab.rdwallpapers.presentation.utils.handleThrowable
import com.crazzylab.rdwallpapers.extensions.userMessage


/**
 * Created by Михаил on 10.12.2017.
 */
class RestErrorHandler(private val resourceManager: ResourceManager) {

    fun proceed(error: Throwable, messageListener: (String) -> Unit = {}){
        Log.d("Error", "Something went wrong!")
        handleThrowable(error)
        messageListener(error.userMessage(resourceManager))
    }

}