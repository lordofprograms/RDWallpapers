package com.crazzylab.rdwallpapers.ui.global

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.crazzylab.rdwallpapers.extensions.inflate
import com.crazzylab.rdwallpapers.presentation.utils.snackbar

/**
 * Created by Михаил on 26.12.2017.
 */
abstract class BaseFragment: MvpAppCompatFragment() {

    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            container?.inflate(layoutRes)

    protected fun showSnack(message: String){
        view?.let { snackbar(it, message) }
    }

    protected fun showSnackWithListener(message: String, listenerText: String, listener: (View) -> Unit){
        view?.let{ snackbar(it, message, listenerText, listener, Snackbar.LENGTH_LONG) }
    }

}