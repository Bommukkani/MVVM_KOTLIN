package com.example.myapplication.data.repository

import com.example.myapplication.data.api.ApiHelper


class MainRepository  constructor(private val apiHelper: ApiHelper) {

    fun getData() = apiHelper.getData()
    fun getEmployee() = apiHelper.getEmployee()

}