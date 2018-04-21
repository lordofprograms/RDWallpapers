package com.crazzylab.rdwallpapers.ui.favimages

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.model.data.db.models.ImageModel
import com.crazzylab.rdwallpapers.presentation.favimages.FavImagesPresenter
import com.crazzylab.rdwallpapers.presentation.favimages.FavImagesView
import com.crazzylab.rdwallpapers.ui.global.BaseFragment
import com.crazzylab.rdwallpapers.ui.previewfavimages.PreviewFavImagesFragment
import com.crazzylab.rdwallpapers.ui.global.views.ItemClickSupport
import com.crazzylab.rdwallpapers.Constants
import com.crazzylab.rdwallpapers.presentation.utils.scrollTop
import com.crazzylab.rdwallpapers.extensions.setTitleBlack
import com.crazzylab.rdwallpapers.extensions.visible
import io.realm.Realm
import kotlinx.android.synthetic.main.fav_images_fragment.*

/**
 * Created by Михаил on 27.08.2017.
 */
class FavImagesFragment : BaseFragment(), FavImagesView {

    @InjectPresenter
    lateinit var presenter: FavImagesPresenter
    private val adapter = FavImagesAdapter()

    fun getInstance(flag: Boolean, isAppTheme: Boolean): FavImagesFragment {
        val fragment = FavImagesFragment()
        val args = Bundle()
        args.putBoolean(Constants.FAV_FLAG_ALL_DELETED, flag)
        args.putBoolean(Constants.WHITE_APP_THEME, isAppTheme)
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Realm.init(context)
        super.onCreate(savedInstanceState)
    }

    override val layoutRes: Int = R.layout.fav_images_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        updateList()
        itemClicks()
    }

    override fun scrollToTop() = scrollTop(rvImagesFav, favImagesAppBar)

    override fun initView() {
        favImagesToolbar.title = getString(R.string.title_favorites)
        favImagesToolbar.setTitleBlack(arguments)
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> rvImagesFav.layoutManager = GridLayoutManager(context, 4)
            Configuration.ORIENTATION_PORTRAIT -> rvImagesFav.layoutManager = GridLayoutManager(context, 2)
            else -> rvImagesFav.layoutManager = GridLayoutManager(context, 2)
        }
        rvImagesFav.itemAnimator = DefaultItemAnimator()
        rvImagesFav.adapter = adapter
    }

    override fun showEmpty(list: ArrayList<ImageModel>) {
        val isEmpty = list.isEmpty()
        rvImagesFav.visible(!isEmpty)
        emptyText.visible(isEmpty)
    }

    override fun showErrorSnack(error: String) {
        showSnack(error)
    }

    override fun setList(list: ArrayList<ImageModel>) = adapter.setFavList(list)

    override fun updateList() {
        adapter.notifyDataSetChanged()
        arguments?.let { arguments ->
            if(arguments.getBoolean(Constants.FAV_FLAG_ALL_DELETED)) {
                showSnack(getString(R.string.all_items_deleted))
            }
        }
    }

    override fun itemClicks() {
        with(ItemClickSupport.addTo(rvImagesFav)) {
            setOnItemClickListener { _, position, _ -> goToPreviewImage(position) }
        }
    }

    override fun goToPreviewImage(position: Int) {
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container,
                PreviewFavImagesFragment().getInstance(position))?.addToBackStack(null)?.commit()
    }

}