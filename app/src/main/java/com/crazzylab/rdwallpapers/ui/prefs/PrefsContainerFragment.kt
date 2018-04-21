package com.crazzylab.rdwallpapers.ui.prefs

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.crazzylab.rdwallpapers.Constants
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.extensions.setTitleBlack
import com.crazzylab.rdwallpapers.ui.global.BaseFragment
import kotlinx.android.synthetic.main.prefs_container_fragment.*

/**
 * Created by Михаил on 19.10.2017.
 */
class PrefsContainerFragment : BaseFragment() {

    override val layoutRes: Int = R.layout.prefs_container_fragment

    fun getInstance(isAppTheme: Boolean): PrefsContainerFragment {
        val fragment = PrefsContainerFragment()
        val args = Bundle()
        args.putBoolean(Constants.WHITE_APP_THEME, isAppTheme)
        fragment.arguments = args
        return fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        activity?.fragmentManager?.beginTransaction()?.replace(R.id.frameContainer, PrefsFragment())?.commit()
    }

    fun scrollToTop() {
        if(settingsContainer != null) settingsContainer.scrollTo(0,0)
    }

    private fun setToolbar() {
        with((settingsToolbar as Toolbar)) {
            title = getString(R.string.title_settings)
            setTitleBlack(arguments)
        }
    }

}