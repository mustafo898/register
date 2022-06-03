package com.example.register.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.newtrainerapp.retrofit.ApiClient
import com.example.newtrainerapp.retrofit.ApiInterface
import com.example.newtrainerapp.retrofit.models.BaseNetworkResult
import com.example.newtrainerapp.retrofit.models.request.LogInRequest
import com.example.newtrainerapp.retrofit.models.request.SignUpRequest
import com.example.newtrainerapp.retrofit.models.response.LogInResponse
import com.example.newtrainerapp.retrofit.models.response.SignUpResponse
import com.example.newtrainerapp.retrofit.models.response.TrainerResponse
import com.example.register.controller.extention
import com.example.register.shared.Shared
import com.example.register.ui.TrainerFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    private var apiInterface: ApiInterface? = ApiClient.retrofit!!.create(ApiInterface::class.java)

    fun getAllTrainer(): MutableLiveData<BaseNetworkResult<List<TrainerResponse>>> {
        val listViewModel = MutableLiveData<BaseNetworkResult<List<TrainerResponse>>>()

        listViewModel.value = BaseNetworkResult.Loading(true)
        apiInterface?.getTrainersList()?.enqueue(object : Callback<List<TrainerResponse>> {
            override fun onResponse(
                call: Call<List<TrainerResponse>>,
                response: Response<List<TrainerResponse>>
            ) {
                listViewModel.value = BaseNetworkResult.Loading(false)
                if (response.code() == 200) {
                    response.body()?.let {
                        listViewModel.value = BaseNetworkResult.Success(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<TrainerResponse>>, t: Throwable) {
                listViewModel.value = BaseNetworkResult.Loading(false)
                listViewModel.value = BaseNetworkResult.Error("No internet connection")
            }
        })
        return listViewModel
    }

    fun logIn(logInRequest: LogInRequest,context: Context): MutableLiveData<BaseNetworkResult<LogInResponse>> {
        val liveData = MutableLiveData<BaseNetworkResult<LogInResponse>>()
        val sharedPref = Shared(context)
        apiInterface?.logIn(logInRequest)
            ?.enqueue(object : Callback<LogInResponse> {
                override fun onResponse(
                    call: Call<LogInResponse>,
                    response: Response<LogInResponse>
                ) {
                    if (response.code() == 200) {
                        response.body()?.let {
                            sharedPref.setToken(it.accessToken)
                            Log.d("TTTT", "language: ${sharedPref.getToken()}")
                            liveData.value = BaseNetworkResult.Success(it)
                        }
                    }
                }

                override fun onFailure(call: Call<LogInResponse>, t: Throwable) {

                }
            })
        return liveData
    }

    fun singUp(signUpRequest: SignUpRequest, context: Context, logInRequest: LogInRequest): MutableLiveData<BaseNetworkResult<SignUpResponse>> {
        val liveData = MutableLiveData<BaseNetworkResult<SignUpResponse>>()
        val sharedPref = Shared(context)

        apiInterface?.signUp(signUpRequest)?.enqueue(object : Callback<SignUpResponse>{
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        liveData.value = BaseNetworkResult.Success(it)
                        logIn(logInRequest,context)
                    }
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {

            }
        })
        return liveData
    }
}