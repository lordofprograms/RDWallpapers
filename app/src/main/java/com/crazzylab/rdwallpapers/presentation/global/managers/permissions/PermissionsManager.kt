package com.crazzylab.rdwallpapers.presentation.global.managers.permissions

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.Constants
import com.crazzylab.rdwallpapers.extensions.toast

/**
 * Created by Михаил on 29.09.2017.
 */
class PermissionsManager {

    fun requestPerms(activity: Activity){
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            activity.requestPermissions(permissions, Constants.PERMISSION_CODE)
    }

    @SuppressLint("UseCheckPermission")
    fun hasPermissions(activity: Activity): Boolean{
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return permissions
                .map { activity.checkCallingOrSelfPermission(it) }
                .none { it != PackageManager.PERMISSION_GRANTED }
    }

    fun checkRequestCode(activity: Activity, requestCode: Int){
        when(requestCode == Constants.PERMISSION_CODE && hasPermissions(activity) ) {
            true -> activity.toast(R.string.storage_permission_granted)
            false -> activity.toast(R.string.storage_permission_denied)
        }
    }

    fun getPermissionResult(requestCode: Int, grantResults: IntArray): Boolean{
        var allowed = true

        when (requestCode) {
            Constants.PERMISSION_CODE -> grantResults.forEach { allowed = allowed && (it == PackageManager.PERMISSION_GRANTED) }
            else -> allowed = false
        }
        return allowed
    }

}