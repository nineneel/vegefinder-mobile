package com.bangkit.vegefinder.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.bangkit.vegefinder.data.model.Avatar
import com.bangkit.vegefinder.data.model.User
import com.bangkit.vegefinder.data.model.Vegetable
import com.bangkit.vegefinder.data.model.VegetableType
import com.bangkit.vegefinder.helper.TinyDB

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
    private var tinyDB: TinyDB =
        TinyDB(context)

    companion object {
        const val KEY_TOKEN = "token"
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
        var userToken : String? = null
    }

    fun saveAvatarList(avatars: ArrayList<Avatar>){
        tinyDB.putListAvatar("avatarList", avatars)
    }

    fun getAvatarList(): ArrayList<Avatar>{
        return tinyDB.getAvatarList("avatarList")
    }

    fun saveUser(user: User){
        tinyDB.putUser("currentUser", user)
    }

    fun getUser(): User? {
        return tinyDB.getUser("currentUser")
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
        tinyDB.remove("currentUser")
        tinyDB.remove("vegetableList")
        tinyDB.remove("vegetableTypeList")
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    fun removeByKey(key: String){
        tinyDB.remove(key)
    }
}
