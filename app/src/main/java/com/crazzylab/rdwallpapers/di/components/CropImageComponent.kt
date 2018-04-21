package com.crazzylab.rdwallpapers.di.components

import com.crazzylab.rdwallpapers.di.modules.CropImageModule
import com.crazzylab.rdwallpapers.di.scopes.CropImageScope
import com.crazzylab.rdwallpapers.presentation.cropimage.CropImagePresenter
import dagger.Component

/**
 * Created by Михаил on 22.02.2018.
 */
@CropImageScope
@Component(modules = [CropImageModule::class], dependencies = [AppComponent::class])
interface CropImageComponent {

    fun inject(cropImagePresenter: CropImagePresenter)

}