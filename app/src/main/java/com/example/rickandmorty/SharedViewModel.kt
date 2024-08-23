package com.example.rickandmorty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.model.Character

class SharedViewModel: ViewModel() {
    private val _character = MutableLiveData<Character>()
    val character: LiveData<Character>
        get() = _character

    fun setCharacter(character: Character){
        _character.value =  character
    }
}