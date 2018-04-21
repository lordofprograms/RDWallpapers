package com.crazzylab.rdwallpapers.ui.images.adapters

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.crazzylab.rdwallpapers.entity.ImageItem
import com.crazzylab.rdwallpapers.ui.global.list.ViewType
import com.crazzylab.rdwallpapers.ui.global.list.ViewTypeDelegateAdapter
import com.crazzylab.rdwallpapers.Constants
import java.util.ArrayList

/**
 * Created by Михаил on 21.09.2017.
 */
class ImagesPagingAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val items = ArrayList<ViewType>()
    private val loadingItem = object : ViewType {
        override fun getViewType(): Int = Constants.LOADING
    }

    init {
        delegateAdapters.put(Constants.IMAGES, SimpleImagesDelegateAdapter())
        delegateAdapters.put(Constants.LOADING, LoadingDelegateAdapter())
        items.add(loadingItem)
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            delegateAdapters.get(viewType).onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])
    }

    override fun getItemViewType(position: Int): Int = this.items[position].getViewType()

    fun setData(images: List<ImageItem>) {
        val progress = isProgress()

        items.clear()
        items.addAll(images)
        if (progress) items.add(loadingItem)

        notifyDataSetChanged()
    }

    fun showProgress(isVisible: Boolean) {
        val currentProgress = isProgress()

        if (isVisible && !currentProgress) items.add(loadingItem)
        else if (!isVisible && currentProgress) items.remove(items.last())

        notifyDataSetChanged()
    }

    private fun isProgress() = items.isNotEmpty() && items.last() !is ImageItem

    fun getImages(): List<ImageItem> = items.filter { it.getViewType() == Constants.IMAGES }.map { it as ImageItem }

}