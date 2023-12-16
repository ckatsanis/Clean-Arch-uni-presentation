package com.learningwithmanos.uniexercise

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
object AppPreferences {
    private var sharedPreferences: SharedPreferences? = null
    fun setup(context: Context) {
        sharedPreferences = context.getSharedPreferences(".marvelPrefs", MODE_PRIVATE)
    }
    var apikey: String?
        get() = Key.APIKEY.getString()
        set(value) = Key.APIKEY.setString(value)
    var privatekey: String?
        get() = Key.PRIVATE_KEY.getString()
        set(value) = Key.PRIVATE_KEY.setString(value)
    private enum class Key {
        APIKEY, PRIVATE_KEY;
        fun getString(): String? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getString(name, "") else null
        fun setString(value: String?) = value?.let { sharedPreferences!!.edit { putString(name, value) } } ?: remove()
        fun remove() = sharedPreferences!!.edit { remove(name) }
    }
}