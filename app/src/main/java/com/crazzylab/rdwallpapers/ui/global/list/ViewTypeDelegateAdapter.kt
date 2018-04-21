package com.crazzylab.rdwallpapers.ui.global.list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by Михаил on 21.09.2017.
 */
interface ViewTypeDelegateAdapter {

    fun onCreateViewHolder(parent:ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)

}