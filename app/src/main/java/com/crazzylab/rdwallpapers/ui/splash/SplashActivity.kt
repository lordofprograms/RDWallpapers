package com.crazzylab.rdwallpapers.ui.splash

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.presentation.splash.SplashPresenter
import com.crazzylab.rdwallpapers.presentation.splash.SplashView
import com.crazzylab.rdwallpapers.ui.main.MainActivity

class SplashActivity : MvpAppCompatActivity(), SplashView {

    @InjectPresenter
    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        goToMain()
    }

    override fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
