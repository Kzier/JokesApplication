package com.jokesapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.jokesapplication.R
import com.jokesapplication.domain.entity.Joke


class JokeDelegate(private val callback: Callback) :
    AdapterDelegate<MutableList<out Joke>>() {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        JokeVH.create(
            callback,
            parent
        )

    override fun isForViewType(items: MutableList<out Joke>, position: Int): Boolean =
        items[position] is Joke

    override fun onBindViewHolder(
        items: MutableList<out Joke>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as JokeVH).onBind(items[position])
    }

    interface Callback {
        fun share(obj: Joke)
        fun like(obj: Joke)
    }

    internal class JokeVH private constructor(private val callback: Callback, view: View) :
        RecyclerView.ViewHolder(view) {
        private val text: AppCompatTextView = itemView.findViewById(R.id.text)
        private val share: AppCompatButton = itemView.findViewById(R.id.share_button)
        private val like: AppCompatButton = itemView.findViewById(R.id.like_button)

        fun onBind(obj: Joke) {
            text.text = obj.joke
            share.setOnClickListener { callback.share(obj) }
            like.setOnClickListener { callback.like(obj) }
        }

        companion object {
            fun create(callback: Callback, parent: ViewGroup) =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_joke, parent, false)
                    .let {
                        JokeVH(callback, it)
                    }
        }
    }

}