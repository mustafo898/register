package com.example.register.shared

import android.content.Context
import android.content.SharedPreferences
import java.util.ArrayList

class Shared(context:Context) {

    private var preferences: SharedPreferences =
        context.getSharedPreferences("APP_PREFS_NAME", Context.MODE_PRIVATE)

    private lateinit var editor: SharedPreferences.Editor

    fun setToken(token: String) {
        editor = preferences.edit()
        editor.putString("TOKEN", token)
        editor.apply()
    }

    fun getToken() = preferences.getString("TOKEN", "")

    fun setUserName(username: String) {
        editor = preferences.edit()
        editor.putString("UserName", username)
        editor.apply()
    }

    fun getUserName() = preferences.getString("UserName", "")

    fun setPassword(password: String) {
        editor = preferences.edit()
        editor.putString("password", password)
        editor.apply()
    }

    fun getPassword() = preferences.getString("password", "")

}