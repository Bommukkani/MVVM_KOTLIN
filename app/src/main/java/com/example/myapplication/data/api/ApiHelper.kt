package com.example.myapplication.data.api

import com.example.myapplication.data.api.ApiService

class ApiHelper(private val apiService: ApiService) {

    fun getUsers() = apiService.getUsers()
    fun getData() = apiService.getData()
    fun getEmployee() = apiService.getEmployee()


}