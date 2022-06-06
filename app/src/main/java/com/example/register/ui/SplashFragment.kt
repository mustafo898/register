package com.example.register.ui

import android.os.Handler
import android.view.View
import com.example.register.shared.Shared
import com.example.register.controller.extention
import com.example.register.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private var i = 0
    private val handler = Handler()
    private val shared by lazy {
        Shared(requireContext())
    }

    override fun onViewCreated() {
        progress()
        startTimer()
    }

    private fun startTimer() {
        Handler().postDelayed({
            if (shared.getToken() != "") {
                extention.controller?.startMainFragment(TrainerFragment())
            } else {
                extention.controller?.startMainFragment(RegisterFragment())
            }
        }, 3000)
    }

    // progress barni ishlab turishi uchun
    private fun progress() {
        i = binding.progressBar.progress
        Thread {
            while (i < 100) {
                i += 1
                handler.post {
                    binding.progressBar.progress = i
                }
                try {
                    Thread.sleep(50)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            binding.progressBar.visibility = View.INVISIBLE
        }.start()
    }
}