package com.kouseina.githubuser.data.retrofit

import com.kouseina.githubuser.data.response.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUser(
        @Query("q") q: String,
    ) : Call<SearchUserResponse>
}