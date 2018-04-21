package com.crazzylab.rdwallpapers.ui.images.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.ui.global.list.ViewType
import com.crazzylab.rdwallpapers.ui.global.list.ViewTypeDelegateAdapter
import com.crazzylab.rdwallpapers.extensions.inflate

/**
 * Created by Михаил on 21.09.2017.
 */
class LoadingDelegateAdapter: ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = LoadingViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType){}

    class LoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.image_item_loading))


}