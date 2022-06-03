package com.example.register.mvvm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newtrainerapp.retrofit.models.BaseNetworkResult
import com.example.newtrainerapp.retrofit.models.request.LogInRequest
import com.example.newtrainerapp.retrofit.models.request.SignUpRequest
import com.example.newtrainerapp.retrofit.models.response.LogInResponse
import com.example.newtrainerapp.retrofit.models.response.SignUpResponse
import com.example.newtrainerapp.retrofit.models.response.TrainerResponse
import com.example.register.repository.Repository

class MyViewModel : ViewModel() {
    private val repository = Repository()
    private val _trainerListViewModel = MutableLiveData<List<TrainerResponse>?>()
    val trainerListViewModel: MutableLiveData<List<TrainerResponse>?> get() = _trainerListViewModel

    private val _loadingViewModel = MutableLiveData<Boolean?>()
    val loadingViewModel: MutableLiveData<Boolean?> get() = _loadingViewModel

    private val _errorViewModel = MutableLiveData<String?>()
    val errorViewModel: MutableLiveData<String?> get() = _errorViewModel

    fun getAllTrainer() {
        repository.getAllTrainer().observeForever {list->
            val listTrainer = ArrayList<TrainerResponse>()
            when (list) {
                is BaseNetworkResult.Success -> {
                    _trainerListViewModel.value = listTrainer
                }
                is BaseNetworkResult.Error -> {
                    _errorViewModel.value = list.message
                }
                is BaseNetworkResult.Loading -> {
                    _loadingViewModel.value = list.isLoading
                }
            }
        }
    }

    private val _logIn = MutableLiveData<LogInResponse>()
    val logIn: LiveData<LogInResponse> get() = _logIn

    fun logIn(logInRequest: LogInRequest, context: Context){
        repository.logIn(logInRequest,context).observeForever {
            when(it){
                is BaseNetworkResult.Success -> {
                    _logIn.value = it.data!!
                }
                is BaseNetworkResult.Error -> {
                    _errorViewModel.value = it.message
                }
                is BaseNetworkResult.Loading -> {
                    _loadingViewModel.value = it.isLoading
                }
            }
        }
    }

    private val _signUp = MutableLiveData<SignUpResponse>()
    val signUp: LiveData<SignUpResponse> get() = _signUp

    fun signUp(signUpRequest: SignUpRequest, logInRequest: LogInRequest, context: Context) {
        repository.singUp(signUpRequest,context,logInRequest).observeForever {
            when(it){
                is BaseNetworkResult.Success -> {
                    _signUp.value = it.data!!
                }
                is BaseNetworkResult.Error -> {
                    _errorViewModel.value = it.message
                }
                is BaseNetworkResult.Loading -> {
                    _loadingViewModel.value = it.isLoading
                }
            }
        }
    }

}