package com.crazzylab.rdwallpapers.ui.previewimages

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.entity.ImageItem
import com.crazzylab.rdwallpapers.extensions.inflate
import com.crazzylab.rdwallpapers.extensions.loadImage

/**
 * Created by Михаил on 14.08.2017.
 */
class PreviewPagerAdapter : PagerAdapter() {

    val imagesList =  ArrayList<ImageItem>()

    fun setList(imgList: ArrayList<ImageItem>){
        imagesList.addAll(imgList)
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val view: View = container.inflate(R.layout.image_slider_item)
        val imageView: ImageView = view.findViewById(R.id.imagePreview)
        val image = imagesList[position]
        imageView.loadImage(container.context, image.image)

        container.addView(view)
        return view
    }

    override fun getCount(): Int = imagesList.size

    override fun isViewFromObject(view: View, obj: Any): Boolean = view === obj as View


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}
