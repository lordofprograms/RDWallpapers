package com.crazzylab.rdwallpapers.di.components

import com.crazzylab.rdwallpapers.di.scopes.FavImagesScope
import com.crazzylab.rdwallpapers.presentation.favimages.FavImagesPresenter
import com.crazzylab.rdwallpapers.di.modules.FavImagesModule
import dagger.Component

/**
 * Created by Михаил on 27.08.2017.
 */
@FavImagesScope
@Component(modules = [(FavImagesModule::class)], dependencies = [(AppComponent::class)])
interface FavImagesComponent {
    fun inject(fragment: FavImagesPresenter)
}