package com.crazzylab.rdwallpapers.entity

import android.os.Parcel
import android.os.Parcelable
import com.crazzylab.rdwallpapers.ui.global.list.ViewType
import com.crazzylab.rdwallpapers.Constants
import com.crazzylab.rdwallpapers.extensions.createParcel

/**
 * Created by Михаил on 21.09.2017.
 */

data class Images(
        val redirectedUrl: String,
        val elements: List<ImageItem>) : Parcelable {

    companion object {
        @JvmField
        @Suppress("unused")
        val CREATOR = createParcel { Images(it) }
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.createTypedArrayList(ImageItem.CREATOR))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(redirectedUrl)
        parcel.writeTypedList(elements)
    }

    override fun describeContents(): Int = 0

}

/*data class Images(
        val after: String?,
        val before: String?,
        val elements: List<ImageItem>) : Parcelable {

    companion object {
        @JvmField
        @Suppress("unused")
        val CREATOR = createParcel { Images(it) }
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(ImageItem.CREATOR))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(after)
        parcel.writeString(before)
        parcel.writeTypedList(elements)
    }

    override fun describeContents(): Int = 0

}*/

data class ImageItem(
        val image: String,
        val name: String,
        val fileName: String) : Parcelable, ViewType {

    companion object {
        @JvmField
        @Suppress("unused")
        val CREATOR = createParcel { ImageItem(it) }
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeString(fileName)
    }

    override fun describeContents(): Int = 0

    override fun getViewType() = Constants.IMAGES

}
