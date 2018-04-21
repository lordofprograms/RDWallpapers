package com.crazzylab.rdwallpapers.di.components

import com.crazzylab.rdwallpapers.di.modules.ImagesModule
import com.crazzylab.rdwallpapers.presentation.images.ImagesPresenter
import com.crazzylab.rdwallpapers.di.scopes.ImagesScope
import dagger.Component

/**
 * Created by Михаил on 13.08.2017.
 */
@ImagesScope
@Component(modules = [(ImagesModule::class)], dependencies = [(AppComponent::class)])
interface ImagesComponent {

    fun inject(presenter: ImagesPresenter)

}