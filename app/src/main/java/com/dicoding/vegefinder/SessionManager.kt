package com.dicoding.vegefinder

import android.content.Context
import android.content.SharedPreferences
import com.dicoding.vegefinder.data.model.User
import com.dicoding.vegefinder.data.model.Vegetable
import com.dicoding.vegefinder.data.model.VegetableType

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
    private var tinyDB: TinyDB = TinyDB(context)

    companion object {
        const val KEY_TOKEN = "token"
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
        var userToken : String? = null
    }

    fun saveUser(user: User){
        tinyDB.putObject("user", User::class.java)
    }

    fun getUser(): User{
        return tinyDB.getObject("user", User::class.java)
    }

    fun saveVegetableList(vegetableList: ArrayList<Vegetable>){
        tinyDB.putListVegetable("vegetableList",  vegetableList)
    }

    fun getVegetableList(): ArrayList<Vegetable>? {
        return tinyDB.getVegetableList("vegetableList")
    }

    fun saveVegetableTypeList(vegetableTypeList: ArrayList<VegetableType>){
        tinyDB.putListTypeVegetable("vegetableTypeList", vegetableTypeList)
    }

    fun getVegetableTypeList(): ArrayList<VegetableType>{
        return tinyDB.getVegetableTypeList("vegetableTypeList")
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
        tinyDB.remove("user")
        tinyDB.remove("vegetableList")
        tinyDB.remove("vegetableTypeList")
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}
