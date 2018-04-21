package com.crazzylab.rdwallpapers.di.components

import com.crazzylab.rdwallpapers.di.modules.PrefsModule
import com.crazzylab.rdwallpapers.di.scopes.PrefsScope
import com.crazzylab.rdwallpapers.ui.prefs.PrefsFragment
import dagger.Component

/**
 * Created by Михаил on 22.10.2017.
 */
@PrefsScope
@Component(modules = [(PrefsModule::class)], dependencies = [(AppComponent::class)])
interface PrefsComponent {

    fun inject(fragment: PrefsFragment)

}