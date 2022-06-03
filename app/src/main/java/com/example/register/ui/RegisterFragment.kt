package com.example.register.ui

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.newtrainerapp.retrofit.models.request.LogInRequest
import com.example.newtrainerapp.retrofit.models.request.SignUpRequest
import com.example.register.controller.extention
import com.example.register.shared.Shared
import com.example.register.databinding.FragmentRegisterBinding
import com.example.register.mvvm.MyViewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val shared by lazy {
        Shared(requireContext())
    }
    private lateinit var viewModel:MyViewModel
    override fun onViewCreated() {
        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]
        binding.register.setOnClickListener {
            val username = binding.nickName.text.toString()
            val email = binding.emailAddress.text.toString()
            val name = binding.name.text.toString()
            val password = binding.security.text.toString()
            val role = listOf<String>("ROLE_USER")
            if (username.trim().isNotEmpty() && email.trim().isNotEmpty() && name.trim().isNotEmpty() && password.trim().isNotEmpty()){
                viewModel.signUp(SignUpRequest(name,username,email,role,password), LogInRequest(username,password),requireContext())
                extention.controller?.replaceFragment(TrainerFragment())
            }else{
                Toast.makeText(requireContext(), "Please fill fields", Toast.LENGTH_SHORT).show()
            }
        }
        binding.logIn.setOnClickListener {
            extention.controller?.replaceFragment(SignUpFragment())
        }
    }
}