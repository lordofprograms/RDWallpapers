package com.crazzylab.rdwallpapers.model.system

import android.content.Context
import android.content.res.Resources

/**
 * Created by Михаил on 10.12.2017.
 */
class ResourceManager(private val context: Context) {

    fun getString(id: Int): String = context.getString(id)

    fun getStringArray(resources: Resources, id: Int): Array<out String> = resources.getStringArray(id)

}