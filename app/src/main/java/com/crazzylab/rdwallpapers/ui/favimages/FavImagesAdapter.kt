package com.crazzylab.rdwallpapers.ui.favimages

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.model.data.db.models.ImageModel
import com.crazzylab.rdwallpapers.extensions.inflate
import com.crazzylab.rdwallpapers.extensions.loadImage

/**
 * Created by Михаил on 29.08.2017.
 */
class FavImagesAdapter: RecyclerView.Adapter<FavImagesAdapter.FavImageViewHolder>() {

     private val favListImages = ArrayList<ImageModel>()

    fun setFavList(favList: ArrayList<ImageModel>) {
        if(favListImages.isEmpty()) favListImages.addAll(favList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FavImageViewHolder? {
        val view = parent?.inflate(R.layout.image_item)
        return FavImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavImageViewHolder, position: Int) {
        val image = favListImages[position]
        holder.bind(image)
    }


    override fun getItemCount(): Int = when {
        favListImages.size > 0 -> favListImages.size
        else -> 0
    }

    class FavImageViewHolder(val view: View?) : RecyclerView.ViewHolder(view) {
        private val iv: ImageView? = view?.findViewById(R.id.imageWp)
        fun bind(item: ImageModel) = iv?.loadImage(view?.context, item.image)
    }

}