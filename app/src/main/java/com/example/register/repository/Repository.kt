package com.example.register.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newtrainerapp.retrofit.ApiClient
import com.example.newtrainerapp.retrofit.ApiInterface
import com.example.newtrainerapp.retrofit.models.BaseNetworkResult
import com.example.newtrainerapp.retrofit.models.request.LogInRequest
import com.example.newtrainerapp.retrofit.models.request.SignUpRequest
import com.example.newtrainerapp.retrofit.models.response.LogInResponse
import com.example.newtrainerapp.retrofit.models.response.SignUpResponse
import com.example.newtrainerapp.retrofit.models.response.TrainerResponse
import com.example.register.shared.Shared
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    private var apiInterface: ApiInterface? = ApiClient.retrofit!!.create(ApiInterface::class.java)

    fun getAllTrainer(): LiveData<BaseNetworkResult<List<TrainerResponse>>> {
        val listViewModel = MutableLiveData<BaseNetworkResult<List<TrainerResponse>>>()

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
                listViewModel.value = BaseNetworkResult.Error("${t.message}")
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
                            liveData.value = BaseNetworkResult.Error("success")
                        }
                    }
                }

                override fun onFailure(call: Call<LogInResponse>, t: Throwable) {
                    liveData.value = BaseNetworkResult.Error("${t.message}")
                }
            })
        return liveData
    }

    fun singUp(signUpRequest: SignUpRequest): MutableLiveData<BaseNetworkResult<String>> {
        val liveData = MutableLiveData<BaseNetworkResult<String>>()

        apiInterface?.signUp(signUpRequest)?.enqueue(object : Callback<String>{
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        liveData.value = BaseNetworkResult.Success(it)
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                liveData.value = BaseNetworkResult.Error(t.message)
            }
        })
        return liveData
    }
}