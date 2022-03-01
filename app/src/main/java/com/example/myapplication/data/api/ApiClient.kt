package com.example.myapplication.data.api

import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit: Retrofit? = null


    val client: Retrofit?
        get() {
            if (retrofit == null) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                val httpClient = OkHttpClient.Builder()
                httpClient.addInterceptor(logging)
                retrofit = Retrofit.Builder()
                    .baseUrl("https://mockapi-123.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            }
            return retrofit
        }
}