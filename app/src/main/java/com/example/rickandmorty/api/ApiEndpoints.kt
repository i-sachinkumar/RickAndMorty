package com.example.rickandmorty.api

import com.example.rickandmorty.model.Character
import com.example.rickandmorty.model.CharacterList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndpoints {

    @GET("character")
    fun getAllCharacters(@Query("page") pageNum : Int) : Call<CharacterList>

    @GET("character/{id}")
    fun getCharacter(@Path("id") id : Int) : Call<Character>

}