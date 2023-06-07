package com.dicoding.vegefinder

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)

    companion object {
        const val KEY_TOKEN = "token"
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
        var userToken : String? = null
    }

    fun saveAuthToken(token: String?) {
        val editor = prefs.edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply()

        userToken = token
    }

    fun getUserToken(): String? {
        userToken = prefs.getString(KEY_TOKEN, null)
        return userToken
    }

    fun setLogin(isLoggedIn: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}
