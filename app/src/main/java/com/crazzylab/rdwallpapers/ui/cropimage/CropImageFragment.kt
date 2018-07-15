package com.crazzylab.rdwallpapers.ui.cropimage

import android.graphics.BitmapFactory
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.presentation.cropimage.CropImagePresenter
import com.crazzylab.rdwallpapers.presentation.cropimage.CropImageView
import com.crazzylab.rdwallpapers.ui.global.BaseFragment
import com.crazzylab.rdwallpapers.Constants
import kotlinx.android.synthetic.main.crop_image_fragment.*

/**
 * Created by Михаил on 23.08.2017.
 */
class CropImageFragment : BaseFragment(), CropImageView {

    @InjectPresenter
    lateinit var presenter: CropImagePresenter

    fun getInstance(filePath: String): CropImageFragment {
        val fragment = CropImageFragment()
        val args = Bundle()
        args.putString(Constants.FILE_PATH, filePath)
        fragment.arguments = args
        return fragment
    }

    override val layoutRes: Int = R.layout.crop_image_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cropImage.setImageBitmap(BitmapFactory.decodeFile(arguments?.getString(Constants.FILE_PATH)))
        btnSet.setOnClickListener { activity?.let {
                if (cropImage.croppedImage != null) presenter.setWallpaper(it, cropImage.croppedImage)
                else afterWallpapersSet(R.string.unknown_error)
            }
        }
    }

    override fun afterWallpapersSet(resId: Int) {
        showSnack(getString(resId))
        activity?.let { presenter.makeSbTransparent(it) }
    }

}