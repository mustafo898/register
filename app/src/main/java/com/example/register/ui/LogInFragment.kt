package com.example.register.ui

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.newtrainerapp.retrofit.models.request.LogInRequest
import com.example.register.shared.Shared
import com.example.register.controller.extention
import com.example.register.databinding.FragmentSignBinding
import com.example.register.mvvm.MyViewModel

class LogInFragment : BaseFragment<FragmentSignBinding>(FragmentSignBinding::inflate) {
    private val shared by lazy {
        Shared(requireContext())
    }

    private lateinit var viewModel: MyViewModel

    override fun onViewCreated() {
        shared.setToken("")
        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]

        binding.name.setText(shared.getUserName())
        binding.security.setText(shared.getPassword())

        binding.register.setOnClickListener {
            val username = binding.name.text.toString()
            val password = binding.security.text.toString()

            if (username.trim().isNotEmpty() && password.trim().isNotEmpty()) {
                val request = LogInRequest(username, password)
                viewModel.logIn(request,requireContext())

                extention.controller?.replaceFragment(TrainerFragment())

            } else {
                Toast.makeText(requireContext(), "Please fill fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.logIn.setOnClickListener {
            extention.controller?.replaceFragment(RegisterFragment())
        }
    }
}