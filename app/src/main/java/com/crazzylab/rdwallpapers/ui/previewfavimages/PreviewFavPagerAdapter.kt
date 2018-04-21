package com.crazzylab.rdwallpapers.ui.previewfavimages

import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.model.data.db.models.ImageModel
import com.crazzylab.rdwallpapers.extensions.inflate
import com.crazzylab.rdwallpapers.extensions.loadImage


/**
 * Created by Михаил on 03.10.2017.
 */
class PreviewFavPagerAdapter : PagerAdapter() {

    private val imagesList =  ArrayList<ImageModel>()

    fun setList(favList: ArrayList<ImageModel>){
        imagesList.addAll(favList)
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val view = container.inflate(R.layout.image_slider_item)
        val imageView = view.findViewById(R.id.imagePreview) as ImageView
        val image = imagesList[position]
        imageView.loadImage(container.context, image.image)

        container.addView(view)
        return view
    }

    fun removeView(pager: ViewPager, position: Int) {
        pager.adapter = null
        imagesList.removeAt(position)
        notifyDataSetChanged()
        pager.adapter = this
    }

    override fun getItemPosition(`object`: Any): Int = when (imagesList.indexOf(`object`)) {
        -1 -> PagerAdapter.POSITION_NONE
        else -> imagesList.indexOf(`object`)
    }

    override fun getCount(): Int = imagesList.size

    override fun isViewFromObject(view: View, obj: Any): Boolean = view === obj as View

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}