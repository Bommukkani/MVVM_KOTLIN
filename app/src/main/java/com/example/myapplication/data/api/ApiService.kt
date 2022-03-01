package com.example.myapplication.data.api

import com.example.myapplication.data.model.Data
import com.example.myapplication.data.model.User
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    fun getUsers(): Single<List<User>>

    @GET("challenge-services/")
    fun getData() : Call<Data>

    @GET("challenge-employees/")
    fun getEmployee() : Call<Data>


}