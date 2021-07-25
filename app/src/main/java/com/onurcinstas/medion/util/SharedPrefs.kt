package com.onurcinstas.medion.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPrefs {

    companion object {
        private const val IS_LOGGED_IN = "is_logged_in"
        private const val USERNAME = "username"

        private var sharedPreferences: SharedPreferences? = null

        @Volatile private var instance: SharedPrefs? = null

        private val lock = Any()

        operator fun invoke(context: Context): SharedPrefs = instance ?: synchronized(lock) {
            instance ?: makeSharedPrefs(context).also {
                instance = it
            }
        }

        private fun makeSharedPrefs(context: Context): SharedPrefs {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPrefs()
        }

    }

    fun setLoggedIn(boolean: Boolean){
        sharedPreferences?.edit()?.putBoolean(IS_LOGGED_IN, boolean)?.apply()
    }
    fun getLoggedIn() = sharedPreferences?.getBoolean(IS_LOGGED_IN, false)

    fun setUsername(username: String){
        sharedPreferences?.edit()?.putString(USERNAME, username)?.apply()
    }
    fun getUsername() = sharedPreferences?.getString(USERNAME, "")

    fun logout(){
        sharedPreferences?.edit()?.clear()?.apply()
    }
}