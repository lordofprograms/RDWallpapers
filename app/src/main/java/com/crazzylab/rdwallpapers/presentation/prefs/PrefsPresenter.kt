package com.crazzylab.rdwallpapers.presentation.prefs

import android.preference.Preference
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.entity.ColorModel
import com.crazzylab.rdwallpapers.model.interactor.PrefsInteractor
import com.crazzylab.rdwallpapers.model.system.ResourceManager
import com.crazzylab.rdwallpapers.model.system.SchedulersProvider
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Михаил on 20.10.2017.
 */
class PrefsPresenter @Inject constructor(private val prefsInteractor: PrefsInteractor,
                                         private val resourceManager: ResourceManager,
                                         private val rxSchedulers: SchedulersProvider) {

    fun onPreferenceClick(view: PrefsView, preference: Preference) {
        with(resourceManager) {
            when (preference.key) {
            getString(R.string.push_notification_key) -> view.onSwitchClick()
            getString(R.string.about_key) -> view.goToAboutFragment()
                getString(R.string.share_key) -> shareWith(view)
                getString(R.string.night_mode_key) -> view.nightModeDialog()
                getString(R.string.theme_key) -> view.themeDialog()
                else -> {
                }
            }
        }
    }

    fun buildColorList(): ArrayList<ColorModel> {
        val colorList = ArrayList<ColorModel>()
        with(resourceManager) {
            colorList.add(ColorModel(getString(R.string.default_theme), R.color.colorPrimaryDark))
            colorList.add(ColorModel(getString(R.string.green_theme), R.color.colorPrimaryGreen))
            colorList.add(ColorModel(getString(R.string.pink_theme), R.color.colorPrimaryPink))
            colorList.add(ColorModel(getString(R.string.cyan_theme), R.color.colorPrimaryCyan))
            colorList.add(ColorModel(getString(R.string.purple_theme), R.color.colorPrimaryPurple))
            colorList.add(ColorModel(getString(R.string.orange_theme), R.color.colorPrimaryOrange))
            colorList.add(ColorModel(getString(R.string.blue_theme), R.color.colorPrimaryBlue))
            colorList.add(ColorModel(getString(R.string.grey_theme), R.color.colorPrimaryGrey))
        }
        return colorList
    }

    fun getNightMode() = prefsInteractor.getNightMode()

    fun setNightMode(position: Int) = prefsInteractor.setNightMode(position)

    fun isNightModeEnabled() = prefsInteractor.isNightModeEnabled()

    fun getTheme() = prefsInteractor.getTheme()

    fun setTheme(position: Int) = prefsInteractor.setTheme(position)

    fun isAppTheme() = prefsInteractor.isAppTheme()

    fun enableNotifications() = prefsInteractor.enableNotifications()

    fun disEnableNotifications() = prefsInteractor.disEnableNotifications()

    private fun shareWith(view: PrefsView) = Single.just(view.setShareIntent())
            .subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.ui())
            .subscribe()

}