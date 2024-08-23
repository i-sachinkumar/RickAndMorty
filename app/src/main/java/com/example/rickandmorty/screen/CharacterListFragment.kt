package com.example.rickandmorty.screen

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.adapter.CharacterListAdapter
import com.example.rickandmorty.R
import com.example.rickandmorty.SharedViewModel
import com.example.rickandmorty.databinding.FragmentCharacterListBinding

class CharacterListFragment : Fragment(), CharacterListAdapter.OnItemClickListener {

    private lateinit var binding: FragmentCharacterListBinding

    private lateinit var viewModel: CharacterListViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var adapter: CharacterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_list, container, false)
        viewModel = ViewModelProvider(this)[CharacterListViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        //start loading
        viewModel.setLoading(true)

        viewModel.getCharacters(1)


        binding.characterListRecycler.layoutManager = LinearLayoutManager(requireContext())

        viewModel.characters.observe(viewLifecycleOwner) { characterList ->
            println("list  updated ${characterList[0].name}")
            adapter = CharacterListAdapter(characterList, this)
            binding.characterListRecycler.adapter = adapter
        }

        viewModel.pageNum.observe(viewLifecycleOwner){pageNum  ->
            if (viewModel.totalPage == 0) return@observe
            binding.forth.visibility = View.VISIBLE
            binding.fifth.visibility = View.VISIBLE


            adapter = CharacterListAdapter(arrayListOf(), this)
            adapter.notifyDataSetChanged()

            viewModel.setLoading(true)
            viewModel.getCharacters(pageNum)

            val selectedColorList =  ColorStateList.valueOf(resources.getColor(R.color.selected_page_color))
            val unselectedColorList =  ColorStateList.valueOf(resources.getColor(R.color.unseleceted_page_color))

            when(pageNum){
                1 ->{
                    binding.first.backgroundTintList = selectedColorList
                    binding.first.text = "1"

                    binding.second.backgroundTintList = unselectedColorList
                    binding.second.text = "2"

                    binding.third.backgroundTintList = unselectedColorList
                    binding.third.text = "3"

                    binding.forth.backgroundTintList = unselectedColorList
                    binding.forth.text = "${viewModel.totalPage}"

                    binding.fifth.backgroundTintList = unselectedColorList
                    binding.fifth.text = "Next"
                }
                2 -> {
                    binding.first.backgroundTintList = unselectedColorList
                    binding.first.text = "1"

                    binding.second.backgroundTintList  = selectedColorList
                    binding.second.text = "2"

                    binding.third.backgroundTintList = unselectedColorList
                    binding.third.text = "3"

                    binding.forth.backgroundTintList = unselectedColorList
                    binding.forth.text = "${viewModel.totalPage}"

                    binding.fifth.backgroundTintList  = unselectedColorList
                    binding.fifth.text = "Next"
                }
                3 ->{
                    binding.first.backgroundTintList =  unselectedColorList
                    binding.first.text = "1"

                    binding.second.backgroundTintList = unselectedColorList
                    binding.second.text = "2"

                    binding.third.backgroundTintList = selectedColorList
                    binding.third.text = "3"

                    binding.forth.backgroundTintList = unselectedColorList
                    binding.forth.text = "${viewModel.totalPage}"

                    binding.fifth.backgroundTintList = unselectedColorList
                    binding.fifth.text = "Next"
                }

                viewModel.totalPage -> {
                    binding.first.backgroundTintList = unselectedColorList
                    binding.first.text = "Prev"

                    binding.second.backgroundTintList = unselectedColorList
                    binding.second.text = "1"

                    binding.third.backgroundTintList = selectedColorList
                    binding.third.text = "$pageNum"

                    binding.forth.visibility = View.GONE
                    binding.fifth.visibility = View.GONE
                }

                else -> {
                    binding.first.backgroundTintList = unselectedColorList
                    binding.first.text = "Prev"

                    binding.second.backgroundTintList  = unselectedColorList
                    binding.second.text = "1"

                    binding.third.backgroundTintList = selectedColorList
                    binding.third.text = "$pageNum"

                    binding.forth.backgroundTintList = unselectedColorList
                    binding.forth.text = "${viewModel.totalPage}"

                    binding.fifth.backgroundTintList = unselectedColorList
                    binding.fifth.text = "Next"
                }

            }
        }

        viewModel.loading.observe(viewLifecycleOwner){loading ->
            if(loading){
                binding.progressCircular?.visibility = View.VISIBLE
            }
            else{
                binding.progressCircular?.visibility = View.GONE
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.first.setOnClickListener {
            pageButtonClicked(binding.first.text.toString())
        }

        binding.second.setOnClickListener {
            pageButtonClicked(binding.second.text.toString())
        }

        binding.third.setOnClickListener {
            pageButtonClicked(binding.third.text.toString())
        }

        binding.forth.setOnClickListener {
            pageButtonClicked(binding.forth.text.toString())
        }

        binding.fifth.setOnClickListener {
            pageButtonClicked(binding.fifth.text.toString())
        }
    }

    private fun pageButtonClicked(btnText : String){
        if(btnText.equals("Prev",true)){
            viewModel.updatePageNumber(viewModel.pageNum.value?.minus(1) ?: 1)
        }
        else if(btnText.equals("Next",true)){
            viewModel.updatePageNumber(viewModel.pageNum.value?.plus(1) ?: 1)
        }
        else{
            try{
                viewModel.updatePageNumber(Integer.parseInt(btnText))
            } catch (e : Exception){
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onItemClick(character: com.example.rickandmorty.model.Character) {
        sharedViewModel.setCharacter(character)

        // Navigate to the detail fragment
        findNavController().navigate(R.id.action_fragment_list_to_fragment_details)
    }
}