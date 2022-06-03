package com.example.register.ui

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.register.shared.Shared
import com.example.register.databinding.FragmentTrainerBinding
import com.example.register.mvvm.MyViewModel
import com.example.register.adapter.TrainerAdapter

class TrainerFragment: BaseFragment<FragmentTrainerBinding>(FragmentTrainerBinding::inflate) {
    private val shared by lazy {
        Shared(requireContext())
    }
    private lateinit var viewModel:MyViewModel
    private val adapter by lazy {
        TrainerAdapter()
    }

    override fun onViewCreated() {
        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter

        viewModel.trainerListViewModel.observe(requireActivity()) {
            adapter.getData(it!!)
        }

        viewModel.getAllTrainer()

        binding.swipe.setOnRefreshListener {
            viewModel.getAllTrainer()
        }

        viewModel.loadingViewModel.observe(requireActivity()) {
            binding.swipe.isRefreshing = it!!
        }

        viewModel.errorViewModel.observe(requireActivity()) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}