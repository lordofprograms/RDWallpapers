package com.crazzylab.rdwallpapers.presentation.global

import android.support.v4.app.FragmentActivity
import android.util.Log
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.crazzylab.rdwallpapers.presentation.utils.clearTransparent
import com.crazzylab.rdwallpapers.presentation.utils.makeTransparent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Михаил on 18.12.2017.
 */
open class BasePresenter<V : MvpView> : MvpPresenter<V>() {

    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        Log.d("Presenter", "onDestroy in BasePresenter")
        compositeDisposable.dispose()
    }

    protected fun Disposable.connect() {
        compositeDisposable.add(this)
    }

    protected fun nonTransparent(activity: FragmentActivity): Observable<Unit> =
            Observable.just(clearTransparent(activity))

    protected fun transparent(activity: FragmentActivity): Observable<Unit> =
            Observable.just(makeTransparent(activity))

}