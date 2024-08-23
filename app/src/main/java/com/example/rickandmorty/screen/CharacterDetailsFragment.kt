package com.example.rickandmorty.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.SharedViewModel
import com.example.rickandmorty.databinding.FragmentCharacterDetailsBinding

class CharacterDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailsBinding
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_details, container, false)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        sharedViewModel.character.observe(viewLifecycleOwner){character ->
            binding.sharedViewModel = sharedViewModel
            Glide.with(requireContext())
                .load(character.image)
                .into(binding.characterImg)
        }
        return binding.root
    }
}