package com.crazzylab.rdwallpapers.ui.previewfavimages

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.*
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.crazzylab.rdwallpapers.Constants
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.model.data.db.models.ImageModel
import com.crazzylab.rdwallpapers.extensions.toast
import com.crazzylab.rdwallpapers.presentation.previewfavimages.PreviewFavImagesPresenter
import com.crazzylab.rdwallpapers.presentation.previewfavimages.PreviewFavImagesView
import com.crazzylab.rdwallpapers.ui.cropimage.CropImageFragment
import com.crazzylab.rdwallpapers.ui.global.BaseFragment
import com.crazzylab.rdwallpapers.presentation.utils.*
import com.crazzylab.rdwallpapers.ui.favimages.FavImagesFragment
import kotlinx.android.synthetic.main.image_preview.*
import java.io.File

/**
 * Created by Михаил on 03.10.2017.
 */
class PreviewFavImagesFragment : BaseFragment(), PreviewFavImagesView {

    @InjectPresenter
    lateinit var presenter: PreviewFavImagesPresenter
    private lateinit var menu: Menu
    private var downloadingDialog: MaterialDialog? = null
    val adapter = PreviewFavPagerAdapter()

    fun getInstance(position: Int): PreviewFavImagesFragment {
        val fragment = PreviewFavImagesFragment()
        val bundle = Bundle()
        bundle.putInt(Constants.FAV_POSITION, position)
        fragment.arguments = bundle
        return fragment
    }

    override val layoutRes: Int = R.layout.image_preview

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setStatusBarTransparent()
        setToolbar()
        setFabIcons()
        updateOnPageChanged()
        setListeners()
    }

    override fun setViewPager(listImages: ArrayList<ImageModel>) {
        if (adapter.count == 0) adapter.setList(listImages)
        viewPagerSlider.adapter = adapter
        arguments?.let { viewPagerSlider.currentItem = it.getInt(Constants.FAV_POSITION) }
    }

    override fun updateOnPageChanged() {
        viewPagerSlider.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                arguments?.putInt(Constants.FAV_POSITION, position)
            }
        })
    }

    override fun setToolbar() {
        val toolbar = previewToolbar as Toolbar
        toolbar.apply {
            bringToFront()
            title = getString(R.string.gallery)
            setBackgroundColor(Color.TRANSPARENT)
            setTitleTextColor(Color.WHITE)
            setNavigationIcon(R.drawable.ic_arrow_back)
        }
        with(activity as MvpAppCompatActivity) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun setStatusBarTransparent() {
        activity?.let { previewToolbar.setPadding(0, (getSbHeight(it) / 2), 0, 0) }
    }

    override fun setFabIcons() {
        fabSet.setImageResource(R.drawable.ic_done)
        fabCrop.setImageResource(R.drawable.ic_crop)
        fabShare.setImageResource(R.drawable.ic_share)
        fabDownload.setImageResource(R.drawable.ic_download)
    }

    override fun showDownloadingDialog() {
        downloadingDialog = context?.let {
            MaterialDialog.Builder(it)
                .title(R.string.downloading_file)
                .content(R.string.please_wait)
                .backgroundColorRes(R.color.colorPrimary)
                .progress(true, 0)
                .show()
        }
    }

    override fun hideDownloadingDialog() {
        if (downloadingDialog != null && downloadingDialog!!.isShowing) downloadingDialog?.dismiss()
    }

    override fun setWallpaperIntent(uri: Uri) {
        val intent = Intent(Intent.ACTION_ATTACH_DATA)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.setDataAndType(uri, "image/*")
        intent.putExtra("mimeType", "image/*")
        startActivity(Intent.createChooser(intent, getString(R.string.set_as)))
    }

    override fun setShareIntent(uri: Uri) {
        val share = Intent(Intent.ACTION_SEND)
        share.putExtra(Intent.EXTRA_STREAM, uri)
        share.type = "image/*"
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(share, getString(R.string.share_with)))
    }

    override fun setSettingsIntent() {
        val appSettingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:${activity?.packageName}"))
        startActivityForResult(appSettingsIntent, Constants.PERMISSION_CODE)
    }

    override fun infoSnack(resId: Int) = showSnack(getString(resId))

    override fun showErrorSnack(error: String) {
        showSnack(error)
        hideDownloadingDialog()
    }

    override fun goToFavImages(isWhiteAppTheme: Boolean){
        activity?.supportFragmentManager?.let {
            with(it) {
                popBackStack()
                beginTransaction().replace(R.id.frameContainer,
                        FavImagesFragment().getInstance(true, isWhiteAppTheme)).commit()
            }
        }
    }

    override fun setListeners() {
        with(presenter) {
            fabSet.setOnClickListener { activity?.let { setAsWallpaper(it, viewPagerSlider.currentItem)} }
            fabCrop.setOnClickListener { activity?.let {cropFile(it, viewPagerSlider.currentItem) } }
            fabShare.setOnClickListener { activity?.let { shareFile(it, viewPagerSlider.currentItem) } }
            fabDownload.setOnClickListener { activity?.let {downloadFile(it, viewPagerSlider.currentItem)} }
        }
    }

    override fun setMenuFavIcon(title: Int, icon: Int) {
        menu.findItem(R.id.action_with_favorites).setTitle(title).setIcon(icon)
    }

    override fun removeView(listImages: ArrayList<ImageModel>) {
        listImages.removeAt(viewPagerSlider.currentItem)
        adapter.removeView(viewPagerSlider, viewPagerSlider.currentItem)
    }

    override fun setPositionAfterDeleting(listImages: ArrayList<ImageModel>, position: Int) {
        when {
            position - 1 == -1 && listImages.isEmpty() -> activity?.let { presenter.makeSbNonTransparentWithSnack(it) }
            position == adapter.count -> viewPagerSlider.currentItem = position - 1
            else -> viewPagerSlider.currentItem = position + 1
        }
    }

    override fun showNoStoragePermissionSnackbar() {
        showSnackWithListener(getString(R.string.storage_permission_not_granted),
                getString(R.string.title_settings), { presenter.openAppSettings()
            context?.toast(R.string.storage_grant_action)
        })
    }

    override fun requestPermissionWithRationale() {
        activity?.let { activity ->
            when {
                ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    showSnackWithListener(getString(R.string.storage_rationale),
                            getString(R.string.grant), { presenter.requestPerms(activity) })
                }
                else -> presenter.requestPerms(activity)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        activity?.let { presenter.checkRequestCode(it, requestCode) }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        activity?.let { presenter.checkPermissionResult(it, requestCode, grantResults) }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun goToCropImage(file: File) {
        activity?.let { activity ->
            with(activity) {
                clearTransparent(this)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, CropImageFragment().getInstance(file.path))
                        .addToBackStack(null)
                        .commit()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.preview_menu, menu)
        this.menu = menu
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_with_favorites).setIcon(R.drawable.ic_favorite_white)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> activity?.let { presenter.makeSbNonTransparent(it) }
            R.id.action_with_favorites -> presenter.setFavListener(viewPagerSlider.currentItem)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        activity?.let { makeTransparent(it) }
    }

    override fun onPause() {
        super.onPause()
        hideDownloadingDialog()
    }

}