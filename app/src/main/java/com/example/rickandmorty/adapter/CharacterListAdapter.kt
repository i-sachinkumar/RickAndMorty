package com.example.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.model.Character


class CharacterListAdapter(
    private val characters: List<Character>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(character: Character)
    }

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val characterName: TextView = itemView.findViewById(R.id.character_name)
        val characterImage: ImageView = itemView.findViewById(R.id.character_img)

        fun bind(character: Character) {
            characterName.text = character.name
            Glide.with(itemView.context)
                .load(character.image)
                .into(characterImage)

            itemView.setOnClickListener {
                listener.onItemClick(character)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_list_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int = characters.size
}