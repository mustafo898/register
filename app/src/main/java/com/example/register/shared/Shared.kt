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


}