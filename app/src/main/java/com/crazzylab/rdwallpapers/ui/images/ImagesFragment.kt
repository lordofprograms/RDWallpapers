package com.crazzylab.rdwallpapers.ui.images

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.arellomobile.mvp.presenter.InjectPresenter
import com.crazzylab.rdwallpapers.Constants
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.ui.global.list.InfiniteScrollListener
import com.crazzylab.rdwallpapers.entity.ImageItem
import com.crazzylab.rdwallpapers.presentation.images.ImagesPresenter
import com.crazzylab.rdwallpapers.presentation.images.ImagesView
import com.crazzylab.rdwallpapers.ui.images.adapters.ImagesPagingAdapter
import com.crazzylab.rdwallpapers.ui.global.BaseFragment
import com.crazzylab.rdwallpapers.ui.previewimages.PreviewImagesFragment
import com.crazzylab.rdwallpapers.ui.global.views.ItemClickSupport
import com.crazzylab.rdwallpapers.presentation.utils.scrollTop
import com.crazzylab.rdwallpapers.extensions.visible
import com.crazzylab.rdwallpapers.extensions.setTitleBlack
import kotlinx.android.synthetic.main.home_images_fragment.*
import java.util.*

/**
 * Created by Михаил on 10.08.2017.
 */
class ImagesFragment : BaseFragment(), ImagesView {

    @InjectPresenter
    lateinit var presenter: ImagesPresenter
    private val adapter = ImagesPagingAdapter()

    override val layoutRes: Int = R.layout.home_images_fragment

    fun getInstance(isAppTheme: Boolean): ImagesFragment {
        val fragment = ImagesFragment()
        val args = Bundle()
        args.putBoolean(Constants.WHITE_APP_THEME, isAppTheme)
        fragment.arguments = args
        return fragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imagesToolbar.title = getString(R.string.title_home)
        imagesToolbar.setTitleBlack(arguments)
        initRecycler()
        itemClicks()
        refreshLayout.setOnRefreshListener {
            Log.d("Paginator","RefreshListener in the fragment")
            presenter.refreshImages()
        }
    }

    override fun scrollToTop() = scrollTop(rvImagesFav, imagesAppBar)

    override fun initRecycler(){
        rvImagesFav.apply {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            val gridLayout = setGridLayoutManager(resources.configuration.orientation)
            layoutManager = gridLayout
            addOnScrollListener(InfiniteScrollListener({ presenter.loadNextImagesPage() }, gridLayout))
            adapter = this@ImagesFragment.adapter
        }
    }

    override fun itemClicks() {
        with(ItemClickSupport.addTo(rvImagesFav)) {
            setOnItemClickListener { _, position, _ -> goToPreviewImage(position) }
        }
    }

    override fun showRefreshProgress(show: Boolean) {
        refreshLayout.postDelayed({ refreshLayout?.isRefreshing = show }, 1000)
    }

    override fun showReloading(){
        refreshLayout?.isRefreshing = true
        refreshLayout.postDelayed({ refreshLayout?.isRefreshing = false }, 1000)
    }

    override fun showEmptyProgress(show: Boolean) {
        //trick for disable and hide swipeToRefresh on fullscreen progress
        /*refreshLayout.visible(!show)
        refreshLayout.post { refreshLayout.isRefreshing = false }*/
    }

    override fun showPageProgress(show: Boolean) {
        rvImagesFav.post { adapter.showProgress(show) }
    }

    override fun showEmptyView(show: Boolean) {
        showMessage(getString(R.string.empty_response))
        // todo show image
    }

    override fun showEmptyError(show: Boolean, message: String?) {
        if (show && message != null) showMessage(message)
    }

    override fun showImages(show: Boolean, images: List<ImageItem>) {
        rvImagesFav.visible(show)
        rvImagesFav.post { adapter.setData(images) }
    }

    override fun infoSnack(resId: Int) = showMessage(getString(resId))

    override fun showContent(connection: Boolean) {
        offlineLayout.visible(!connection)
        rvImagesFav.visible(connection)
    }

    override fun showMessage(message: String) = showSnack(message)

    override fun showConnectionSnack(connection: Boolean) = when (connection) {
        true -> showSnackWithListener(getString(R.string.connection_established),
                getString(R.string.go_to_online_mode), { presenter.goToOnline() })
        else -> infoSnack(R.string.no_connection)
    }

    override fun goToPreviewImage(position: Int) {
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container,
                PreviewImagesFragment().getInstance(adapter.getImages() as ArrayList<ImageItem>,
                        position))?.addToBackStack(null)?.commit()
    }

    override fun onResume() {
        super.onResume()
        presenter.registerReceiver()
    }

    override fun onPause() {
        super.onPause()
        presenter.unregisterReceiver()
    }

    private fun setGridLayoutManager(orientation: Int): GridLayoutManager {
        var gridLayout = GridLayoutManager(context, 2)
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayout = GridLayoutManager(context, 4)
        }
        gridLayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = adapter.getItemViewType(position)
        }
        return gridLayout
    }

}
