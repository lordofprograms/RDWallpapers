package com.crazzylab.rdwallpapers.di.components

import com.crazzylab.rdwallpapers.di.modules.previewImages.PreviewImagesModule
import com.crazzylab.rdwallpapers.di.modules.previewImages.PreviewImagesNetworkModule
import com.crazzylab.rdwallpapers.di.scopes.PreviewImagesScope
import com.crazzylab.rdwallpapers.di.modules.previewImages.SaveImagesApiServiceModule
import com.crazzylab.rdwallpapers.presentation.previewfavimages.PreviewFavImagesPresenter
import com.crazzylab.rdwallpapers.presentation.previewimages.PreviewImagesPresenter
import dagger.Component

/**
 * Created by Михаил on 22.08.2017.
 */
@PreviewImagesScope
@Component(modules = [(PreviewImagesModule::class), (SaveImagesApiServiceModule::class), (PreviewImagesNetworkModule::class)], dependencies = [(AppComponent::class)])
interface PreviewImagesComponent {

    fun inject(presenter: PreviewImagesPresenter)
    fun inject(presenter: PreviewFavImagesPresenter)

}