package com.crazzylab.rdwallpapers.ui.main

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.presentation.main.MainPresenter
import com.crazzylab.rdwallpapers.presentation.main.MainView
import com.crazzylab.rdwallpapers.ui.cropimage.CropImageFragment
import com.crazzylab.rdwallpapers.ui.favimages.FavImagesFragment
import com.crazzylab.rdwallpapers.ui.images.ImagesFragment
import com.crazzylab.rdwallpapers.ui.prefs.PrefsContainerFragment
import com.crazzylab.rdwallpapers.Constants
import com.crazzylab.rdwallpapers.extensions.getBoolExtra
import com.crazzylab.rdwallpapers.presentation.utils.clearTransparent
import com.crazzylab.rdwallpapers.presentation.utils.makeTransparent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.setModes(this)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        selectItem(intent.getBoolExtra(Constants.UPDATE_SETTINGS), savedInstanceState?.getInt(Constants.BOTTOM_NV_POSITION))
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.navigation_home -> {
                if (getFragmentByTag(Constants.HOME_FRAGMENT) !is ImagesFragment) {
                    openFragment(ImagesFragment().getInstance(presenter.isWhiteAppTheme()), Constants.HOME_FRAGMENT)
                }
                else (getFragmentByTag(Constants.HOME_FRAGMENT) as ImagesFragment).scrollToTop()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                if (getFragmentByTag(Constants.FAV_FRAGMENT) !is FavImagesFragment) {
                    openFragment(FavImagesFragment().getInstance(false, presenter.isWhiteAppTheme()), Constants.FAV_FRAGMENT)
                }
                else (getFragmentByTag(Constants.FAV_FRAGMENT) as FavImagesFragment).scrollToTop()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                if (getFragmentByTag(Constants.SETTINGS_FRAGMENT) !is PrefsContainerFragment) {
                    openFragment(PrefsContainerFragment().getInstance(presenter.isWhiteAppTheme()), Constants.SETTINGS_FRAGMENT)
                }
                else (getFragmentByTag(Constants.SETTINGS_FRAGMENT) as PrefsContainerFragment).scrollToTop()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun selectItem(condition: Boolean, position: Int?) {
        if(condition && position == null) navigation.selectedItemId = R.id.navigation_settings
        else navigation.selectedItemId = position ?: R.id.navigation_home
    }

    override fun openFragment(fragment: Fragment, tag: String){
        supportFragmentManager.beginTransaction().replace(R.id.frameContainer, fragment, tag).commit()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putInt(Constants.BOTTOM_NV_POSITION, navigation.selectedItemId)
    }

    override fun onBackPressed() = when {
            supportFragmentManager.backStackEntryCount == 0 -> super.onBackPressed()
            else -> {
                clearTransparent(this)
                with(supportFragmentManager) {
                    popBackStack()
                    fragments.forEach {
                        when (it){
                            is FavImagesFragment -> beginTransaction().replace(R.id.frameContainer,
                                    FavImagesFragment().getInstance(false, presenter.isWhiteAppTheme()),
                                    Constants.FAV_FRAGMENT).commit()
                            is CropImageFragment -> makeTransparent(this@MainActivity)
                        }
                    } } }
        }

    private fun getFragmentByTag(tag: String) = supportFragmentManager.findFragmentByTag(tag)

}