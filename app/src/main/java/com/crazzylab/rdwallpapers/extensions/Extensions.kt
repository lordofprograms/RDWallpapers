package com.crazzylab.rdwallpapers.extensions

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.crazzylab.rdwallpapers.Constants
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.model.system.ResourceManager
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Михаил on 26.12.2017.
 */

fun Throwable.userMessage(resourceManager: ResourceManager): String = when (this) {
    is HttpException -> when (this.code()) {
        304 -> resourceManager.getString(R.string.not_modified_error)
        400 -> resourceManager.getString(R.string.bad_request_error)
        403 -> resourceManager.getString(R.string.forbidden_error)
        404 -> resourceManager.getString(R.string.not_found_error)
        405 -> resourceManager.getString(R.string.method_not_allowed_error)
        409 -> resourceManager.getString(R.string.conflict_error)
        422 -> resourceManager.getString(R.string.unprocessable_error)
        500 -> resourceManager.getString(R.string.server_error)
        else -> resourceManager.getString(R.string.unknown_error)
    }
    is IOException -> resourceManager.getString(R.string.network_error)
    else -> resourceManager.getString(R.string.unknown_error)
}

fun ViewGroup.inflate(resId: Int): View = LayoutInflater.from(context).inflate(resId, this, false)

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun Intent.getBoolExtra(name: String, defaultValue: Boolean = false) = this.getBooleanExtra(name, defaultValue)


fun Toolbar.setTitleBlack(arguments: Bundle?){
    arguments?.let {
        if(it.getBoolean(Constants.WHITE_APP_THEME)){
            this@setTitleBlack.setTitleTextColor(resources.getColor(R.color.contentColor))
        }}
}

fun MvpAppCompatActivity.setBlackArrow(arguments: Bundle?){
    arguments?.let {
        if(it.getBoolean(Constants.WHITE_APP_THEME) && !it.getBoolean(Constants.IS_NIGHT_MODE_ENABLED)) {
            val upArrow = resources.getDrawable(R.drawable.ic_arrow_back)
            upArrow.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP)
            supportActionBar?.setHomeAsUpIndicator(upArrow)
        }}
}

fun RecyclerView.setSidePadding(sidePadding: Int){
    val marginLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    marginLayoutParams.setMargins(sidePadding, 0, sidePadding, 0)
    this.layoutParams = marginLayoutParams
}

fun ImageView.loadImage(context: Context?, image: String?) {
    Glide.with(context).load(image)
            .thumbnail(0.5f)
            .crossFade()
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .into(this)
}

fun Context.toast(resId: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, getString(resId), length).show()
}


// Inline function to create Parcel Creator
inline fun <reified T : Parcelable> createParcel(crossinline createFromParcel: (Parcel) -> T?): Parcelable.Creator<T> =
        object : Parcelable.Creator<T> {
            override fun createFromParcel(source: Parcel): T? = createFromParcel(source)
            override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)
        }
