package com.crazzylab.rdwallpapers.di.components

import com.crazzylab.rdwallpapers.di.modules.MainModule
import com.crazzylab.rdwallpapers.di.scopes.MainScope
import com.crazzylab.rdwallpapers.presentation.main.MainPresenter
import dagger.Component

/**
 * Created by Михаил on 30.12.2017.
 */
@MainScope
@Component(modules = [(MainModule::class)], dependencies = [(AppComponent::class)])
interface MainComponent {

    fun inject(mainPresenter: MainPresenter)

}