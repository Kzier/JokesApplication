package com.jokesapplication.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.jokesapplication.domain.entity.Joke


internal class JokeAdapter(vararg delegates: AdapterDelegate<MutableList<out Joke>>) :
    AsyncListDifferDelegationAdapter<Joke>(diffCallback, *delegates) {

    fun submitList(data: MutableList<out Joke>) {
        items = data
        notifyDataSetChanged()
    }

    companion object {

        val diffCallback = object : DiffUtil.ItemCallback<Joke>() {

            override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean =
                oldItem.joke == newItem.joke
        }
    }

}
