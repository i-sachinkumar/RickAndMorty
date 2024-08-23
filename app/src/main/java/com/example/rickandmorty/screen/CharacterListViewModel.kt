package com.example.rickandmorty.screen

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.api.RetrofitInstance
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.model.CharacterList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CharacterListViewModel : ViewModel() {
    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>>
        get() = _characters

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    fun setLoading(isLoading: Boolean) {
        _loading.value = isLoading
    }


    //Error state
    private val _error = MutableLiveData(false)
    val error: LiveData<Boolean>
        get() = _error

    fun setError(isError: Boolean){
        _error.value = isError
    }


    //Page number
    private val _pageNum = MutableLiveData(1)
    val pageNum: LiveData<Int>
        get() = _pageNum

    var totalPage : Int = 0


    fun updatePageNumber(pageNum : Int){
        _pageNum.value =  pageNum
    }



    fun updateList(list: List<Character>){
        _characters.value = list
    }
    fun getCharacters(page : Int){
        println("page $page")
        val call = RetrofitInstance.getApiInterface().getAllCharacters(page)
        call.enqueue(object : Callback<CharacterList> {
            override fun onResponse(call: Call<CharacterList>, response: Response<CharacterList>) {
                if (response.isSuccessful){
                    totalPage = response.body()?.info?.pages?:0

                    val list : MutableList<Character> = arrayListOf()
                    for (character in response.body()?.characters!!){
                        character?.let { list.add(it) }
                    }
                    updateList(list)
                    setLoading(false)
                }
            }

            override fun onFailure(call: Call<CharacterList>, t: Throwable) {
                updateList(arrayListOf())
                setLoading(false)
                setError(true)
            }
        })
    }
}