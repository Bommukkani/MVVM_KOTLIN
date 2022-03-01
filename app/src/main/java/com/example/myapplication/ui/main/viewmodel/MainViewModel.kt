package com.example.myapplication.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.Data
import com.example.myapplication.data.repository.MainRepository
import com.example.myapplication.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {

    val movieList = MutableLiveData<Data>()
    val errorMessage = MutableLiveData<String>()


    fun getAllData() {

        val response = repository.getData()
        response.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                movieList.postValue(response.body())
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getEmployeeData() {

        val response = repository.getEmployee()
        response.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                movieList.postValue(response.body())
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}