package com.crazzylab.rdwallpapers.presentation.prefs

import com.afollestad.materialdialogs.MaterialDialog

/**
 * Created by Михаил on 20.10.2017.
 */
interface PrefsView {

    fun setPadding()
    fun setShareIntent()
    fun setListeners()
    fun goToAboutFragment()
    fun nightModeSummary()
    fun nightModeDialog()
    fun themeSummary()
    fun setSwitchState()
    fun onSwitchClick()
    fun setDialogChooserListener(dialog: MaterialDialog)
    fun setRecyclerViewPadding(dialog: MaterialDialog)
    fun themeDialog()
    fun refreshNightModeValue()

}