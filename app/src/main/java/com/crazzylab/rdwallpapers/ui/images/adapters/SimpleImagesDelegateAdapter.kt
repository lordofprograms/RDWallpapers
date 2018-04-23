package com.crazzylab.rdwallpapers.ui.images.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.entity.ImageItem
import com.crazzylab.rdwallpapers.ui.global.list.ViewType
import com.crazzylab.rdwallpapers.ui.global.list.ViewTypeDelegateAdapter
import com.crazzylab.rdwallpapers.extensions.inflate
import com.crazzylab.rdwallpapers.extensions.loadImage

/**
 * Created by Михаил on 21.09.2017.
 */
class SimpleImagesDelegateAdapter: ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ImageViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as ImageViewHolder
        holder.bind(item as ImageItem)
    }

    class ImageViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.image_item)) {

        private val iv: ImageView = itemView.findViewById(R.id.imageWp)

        fun bind(item: ImageItem) = iv.loadImage(itemView?.context, item.image)

    }

}