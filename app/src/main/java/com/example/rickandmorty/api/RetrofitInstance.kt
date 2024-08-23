package com.example.rickandmorty.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private const val BASE_URL  =  "https://rickandmortyapi.com/api/"

    private fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiInterface(): ApiEndpoints {
        return getInstance().create(ApiEndpoints::class.java)
    }
}