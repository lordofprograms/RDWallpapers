package com.crazzylab.rdwallpapers.di.components

import com.crazzylab.rdwallpapers.di.modules.AboutModule
import com.crazzylab.rdwallpapers.di.scopes.AboutScope
import com.crazzylab.rdwallpapers.presentation.about.AboutPresenter
import dagger.Component

/**
 * Created by Михаил on 10.02.2018.
 */
@AboutScope
@Component(modules = [AboutModule::class], dependencies = [(AppComponent::class)])
interface AboutComponent {

    fun inject(aboutPresenter: AboutPresenter)

}