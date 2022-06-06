package com.example.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.register.controller.extention
import com.example.register.databinding.ActivityMainBinding
import com.example.register.ui.RegisterFragment
import com.example.register.ui.SplashFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        extention.init(R.id.controller,supportFragmentManager)
        extention.controller?.startMainFragment(SplashFragment())
    }
}