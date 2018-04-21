package com.crazzylab.rdwallpapers.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.extensions.inflate
import com.crazzylab.rdwallpapers.extensions.setTitleBlack
import com.crazzylab.rdwallpapers.presentation.about.AboutPresenter
import com.crazzylab.rdwallpapers.presentation.about.AboutView
import kotlinx.android.synthetic.main.about_fragment.*
import android.content.Intent
import android.net.Uri
import android.support.annotation.StringRes
import android.support.v7.widget.Toolbar
import com.arellomobile.mvp.MvpAppCompatActivity
import com.crazzylab.rdwallpapers.Constants
import com.crazzylab.rdwallpapers.extensions.setBlackArrow


/**
 * Created by Михаил on 08.02.2018.
 */
class AboutFragment: MvpFragment(), AboutView {

    @InjectPresenter
    lateinit var presenter: AboutPresenter

    fun getInstance(isAppTheme: Boolean, isNightMode: Boolean): AboutFragment {
        val fragment = AboutFragment()
        val args = Bundle()
        args.putBoolean(Constants.WHITE_APP_THEME, isAppTheme)
        args.putBoolean(Constants.IS_NIGHT_MODE_ENABLED, isNightMode)
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            container?.inflate(R.layout.about_fragment)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbar()
        setLinksListeners()
    }

    override fun setToolbar() {
        with(aboutToolbar as Toolbar){
            title = getString(R.string.about)
            setTitleBlack(arguments)
            with(activity as MvpAppCompatActivity) {
                setSupportActionBar(aboutToolbar as Toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                setBlackArrow(arguments)
            }
            setNavigationOnClickListener { activity.fragmentManager.popBackStack() }
        }
    }

    override fun setVersionText(version: String) {
        tvVersion.text = getString(R.string.version) + ": " + version
    }

    override fun setDevelopersText(developersText: String) {
        tvDevelopers.text = developersText
    }

    override fun setGoalText(goal: String) {
        tvGoal.text = goal
    }

    override fun sendFeedbackEmailIntent(@StringRes toId: Int, @StringRes subjectId: Int) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(toId)))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(subjectId))
        activity.startActivity(intent)
    }

    override fun openUrlIntent(@StringRes urlId: Int){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(urlId)))
        activity.startActivity(intent)
    }


    override fun setLinksListeners() {
        with(presenter) {
            tvRate.setOnClickListener{ openUrl(R.string.rate_url).subscribe() }
            tvFeedback.setOnClickListener { sendFeedback(R.string.support_email, R.string.email_subject).subscribe() }
            tvSource.setOnClickListener { openUrl(R.string.source_url).subscribe() }
        }
    }
}