package com.example.register.ui

import android.util.Log
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.register.R
import com.example.register.shared.Shared
import com.example.register.databinding.FragmentTrainerBinding
import com.example.register.mvvm.MyViewModel
import com.example.register.adapter.TrainerAdapter
import com.example.register.controller.extention

class TrainerFragment: BaseFragment<FragmentTrainerBinding>(FragmentTrainerBinding::inflate) {
    private lateinit var viewModel: MyViewModel

    private val sharedPref by lazy {
        Shared(requireContext())
    }

    private val adapter by lazy {
        TrainerAdapter()
    }

    override fun onViewCreated() {
        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter

        viewModel.trainerListViewModel.observe(requireActivity()) {
            adapter.getData(it!!)
            it.forEach {f->
                Log.d("ddd", "onViewCreated: $f")
            }
        }

        viewModel.loadingViewModel.observe(requireActivity()) {
            binding.swipe.isRefreshing = it!!
        }

        viewModel.errorViewModel.observe(requireActivity()) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        binding.swipe.setOnRefreshListener {
            viewModel.getAllTrainer()
        }

        viewModel.getAllTrainer()

        binding.logOut.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(),binding.logOut)
            popupMenu.inflate(R.menu.pop_up_menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.log->{
                        extention.controller?.startMainFragment(RegisterFragment())
                        sharedPref.setToken("")
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }
            popupMenu.show()
        }
    }
}