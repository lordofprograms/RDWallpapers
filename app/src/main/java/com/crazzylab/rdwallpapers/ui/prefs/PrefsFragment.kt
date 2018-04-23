package com.crazzylab.rdwallpapers.ui.prefs

import android.preference.PreferenceFragment
import android.os.Bundle
import android.util.TypedValue
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.presentation.prefs.PrefsView
import android.content.Intent
import android.preference.Preference
import android.preference.PreferenceManager
import android.preference.SwitchPreference
import com.crazzylab.rdwallpapers.presentation.prefs.PrefsPresenter
import javax.inject.Inject
import com.afollestad.materialdialogs.MaterialDialog
import com.crazzylab.rdwallpapers.RDWallpapersApp
import com.crazzylab.rdwallpapers.ui.main.MainActivity
import com.crazzylab.rdwallpapers.Constants
import com.crazzylab.rdwallpapers.di.components.DaggerPrefsComponent
import com.crazzylab.rdwallpapers.di.modules.PrefsModule
import android.support.v7.widget.GridLayoutManager
import com.crazzylab.rdwallpapers.ui.global.views.ItemClickSupport
import com.crazzylab.rdwallpapers.extensions.setSidePadding
import com.crazzylab.rdwallpapers.presentation.utils.getSbHeight
import com.crazzylab.rdwallpapers.ui.about.AboutFragment


/**
 * Created by Михаил on 18.10.2017.
 */
class PrefsFragment : PreferenceFragment(), PrefsView, Preference.OnPreferenceClickListener {

    @Inject lateinit var presenter: PrefsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerPrefsComponent.builder()
                .appComponent(RDWallpapersApp.graph)
                .prefsModule(PrefsModule()).build().inject(this)
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
        setSwitchState()
        setPadding()
        nightModeSummary()
        themeSummary()
    }

    override fun onPreferenceClick(preference: Preference): Boolean {
        presenter.onPreferenceClick(this, preference)
        return true
    }

    override fun onSwitchClick() {
        val switch = findPreference(getString(R.string.push_notification_key)) as SwitchPreference
        presenter.setNotificationState(switch.isEnabled)
    }

    override fun setSwitchState() {
        val switch = findPreference(getString(R.string.push_notification_key)) as SwitchPreference
        switch.isChecked = PreferenceManager.getDefaultSharedPreferences(activity)
                .getBoolean(getString(R.string.push_notification_key), false)
    }

    override fun setListeners() {
        val prefsArray = arrayOf(findPreference(getString(R.string.push_notification_key)),
                findPreference(getString(R.string.about_key)), findPreference(getString(R.string.share_key)),
                findPreference(getString(R.string.night_mode_key)), findPreference(getString(R.string.theme_key)))
        prefsArray.forEach { it.onPreferenceClickListener = this }
    }


    override fun setPadding() {
        val orientationMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, resources.displayMetrics).toInt()
        val topMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                resources.getDimension(R.dimen.activity_margin) + 30, resources.displayMetrics).toInt()
        view.setPadding(orientationMargin, topMargin, orientationMargin, orientationMargin)
    }

    override fun setShareIntent() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject/Title")
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text))
        startActivity(Intent.createChooser(intent, getString(R.string.share_with)))
    }

    override fun goToAboutFragment() {
        activity?.fragmentManager?.beginTransaction()?.replace(R.id.container,
                AboutFragment().getInstance(presenter.isAppTheme(), presenter.isNightModeEnabled()))
                ?.addToBackStack(null)?.commit()
    }

    override fun nightModeSummary() {
        val nightPref = findPreference(getString(R.string.night_mode_key))
        val allNightModes = resources.getStringArray(R.array.night_mode_array)
        nightPref.summary = allNightModes[presenter.getNightMode()]
    }

    override fun nightModeDialog() {
        MaterialDialog.Builder(activity)
                .title(R.string.night_mode)
                .items(R.array.night_mode_array)
                .backgroundColorRes(R.color.dialog_background)
                .itemsCallbackSingleChoice(presenter.getNightMode(), { _, _, position, _ ->
                    presenter.setNightMode(position)
                    refreshNightModeValue()
                    true
                })
                .show()
    }

    override fun themeSummary() {
        val themePref = findPreference(getString(R.string.theme_key))
        val allThemes = resources.getStringArray(R.array.theme_array)
        themePref.summary = allThemes[presenter.getTheme()]
    }

    override fun setDialogChooserListener(dialog: MaterialDialog) {
        with(ItemClickSupport.addTo(dialog.recyclerView)) {
            setOnItemClickListener { _, position, _ ->
                run {
                    presenter.setTheme(position)
                    dialog.dismiss()
                    refreshNightModeValue()
                }
            }
        }
    }

    override fun setRecyclerViewPadding(dialog: MaterialDialog) {
        dialog.setOnShowListener {
            dialog.recyclerView.setSidePadding(getSbHeight(activity) / 2)
        }
    }

    override fun themeDialog() {
        val dialog = MaterialDialog.Builder(activity)
                .title(R.string.theme)
                .adapter(ColorsAdapter(presenter.buildColorList(), presenter.getTheme()),
                        GridLayoutManager(activity, 4))
                .backgroundColorRes(R.color.dialog_background)
                .negativeText(R.string.cancel)
                .onNegative { dialog, _ -> dialog.dismiss() }
                .build()
        setRecyclerViewPadding(dialog)
        setDialogChooserListener(dialog)
        dialog.show()
    }

    override fun refreshNightModeValue() {
        val intent = Intent(activity, MainActivity::class.java)
        intent.putExtra(Constants.UPDATE_SETTINGS, true)
        activity.startActivity(intent)
        activity.finish()
    }

}