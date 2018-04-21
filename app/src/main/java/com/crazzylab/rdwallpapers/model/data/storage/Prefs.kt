package com.crazzylab.rdwallpapers.model.data.storage

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Михаил on 21.10.2017.
 */
class Prefs(val context: Context) {

    private val PREF_NAME = "default_preference"
    private val mPref: SharedPreferences
    private var mEditor: SharedPreferences.Editor? = null
    private var mBulkUpdate = false

    init{
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun put(key: String, `val`: String) {
        doEdit()
        mEditor?.putString(key, `val`)
        doCommit()
    }

    fun put(key: String, `val`: Int) {
        doEdit()
        mEditor?.putInt(key, `val`)
        doCommit()
    }

    fun put(key: String, `val`: Boolean) {
        doEdit()
        mEditor?.putBoolean(key, `val`)
        doCommit()
    }

    fun put(key: String, `val`: Float) {
        doEdit()
        mEditor?.putFloat(key, `val`)
        doCommit()
    }

    fun put(key: String, `val`: Double) {
        doEdit()
        mEditor?.putString(key, `val`.toString())
        doCommit()
    }

    fun put(key: String, `val`: Long) {
        doEdit()
        mEditor?.putLong(key, `val`)
        doCommit()
    }

    fun getString(key: String, defaultValue: String? = null): String? = mPref.getString(key, defaultValue)

    fun getInt(key: String, defaultValue: Int = 0): Int = mPref.getInt(key, defaultValue)

    fun getLong(key: String, defaultValue: Long = 0): Long = mPref.getLong(key, defaultValue)

    fun getFloat(key: String, defaultValue: Float = 0f): Float = mPref.getFloat(key, defaultValue)

    fun getDouble(key: String): Double = getDouble(key, 0.0)

    private fun getDouble(key: String, defaultValue: Double): Double {
        return try { (mPref.getString(key, defaultValue.toString())).toDouble() }
        catch (nfe: NumberFormatException) { defaultValue }
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean = mPref.getBoolean(key, defaultValue)

    fun remove(vararg keys: String) {
        doEdit()
        keys.forEach { mEditor?.remove(it)  }
        doCommit()
    }

    fun clear() {
        doEdit()
        mEditor?.clear()
        doCommit()
    }

    fun edit() {
        mBulkUpdate = true
        mEditor = mPref.edit()
    }

    private fun doEdit() {
        if (!mBulkUpdate && mEditor == null) {
            mEditor = mPref.edit()
        }
    }

    private fun doCommit() {
        if (!mBulkUpdate && mEditor != null) {
            mEditor?.commit()
            mEditor = null
        }
    }

}