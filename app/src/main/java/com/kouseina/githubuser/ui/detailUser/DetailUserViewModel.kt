package com.kouseina.githubuser.ui.detailUser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kouseina.githubuser.data.response.DetailUserResponse
import com.kouseina.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
    private val _detaillUser = MutableLiveData<DetailUserResponse>()
    val detailUser : LiveData<DetailUserResponse> = _detaillUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchDetailUser(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().detailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detaillUser.value = response.body()
                } else {
                    Log.e(DetailUserFragment.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailUserFragment.TAG, "onFailure: ${t.message}")
            }

        })
    }
}