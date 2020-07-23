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
import com.jokesapplication.domain.entity.JokeLocal


class LocalJokeDelegate(private val callback: Callback) :
    AdapterDelegate<MutableList<out Joke>>() {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        LocalJokeVH.create(
            callback,
            parent
        )

    override fun isForViewType(items: MutableList<out Joke>, position: Int): Boolean =
        items[position] is JokeLocal

    override fun onBindViewHolder(
        items: MutableList<out Joke>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as LocalJokeVH).onBind(items[position] as JokeLocal)
    }

    interface Callback {
        fun remove(obj: JokeLocal)
    }

    internal class LocalJokeVH private constructor(private val callback: Callback, view: View) :
        RecyclerView.ViewHolder(view) {

        private val text: AppCompatTextView = itemView.findViewById(R.id.text)
        private val remove: AppCompatButton = itemView.findViewById(R.id.remove_button)

        fun onBind(obj: JokeLocal) {
            text.text = obj.joke
            remove.setOnClickListener { callback.remove(obj) }
        }

        companion object {
            fun create(callback: Callback, parent: ViewGroup) =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_local_joke, parent, false)
                    .let {
                        LocalJokeVH(
                            callback,
                            it
                        )
                    }
        }
    }

}