package com.jokesapplication.presentation.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jokesapplication.R
import com.jokesapplication.domain.entity.JokeLocal
import com.jokesapplication.injection.JokeApp
import com.jokesapplication.presentation.adapter.JokeAdapter
import com.jokesapplication.presentation.adapter.LocalJokeDelegate
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocalJokesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var localJokesViewModel: LocalJokesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as? JokeApp)?.appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        localJokesViewModel =
            ViewModelProvider(this, viewModelFactory).get(LocalJokesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_local_jokes, container, false)

        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerView)

        val adapter = JokeAdapter(
            LocalJokeDelegate(object :
                LocalJokeDelegate.Callback {

                override fun remove(obj: JokeLocal) {
                    localJokesViewModel.deleteJoke(obj)
                }

            })
        )

        recyclerView.adapter = adapter

        val fab: FloatingActionButton = root.findViewById(R.id.fab)
        fab.setOnClickListener {
            showAddDialog()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            localJokesViewModel.localJokes.collect {
                adapter.submitList(it)
            }
        }

        return root
    }

    private fun showAddDialog() {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            val customView = LayoutInflater.from(it).inflate(R.layout.add_joke_dialog, null)
            val editText = customView.findViewById<AppCompatEditText>(R.id.editText)
            val limit = resources.getInteger(R.integer.dialog_joke_characters_limit)
            val limitText = customView.findViewById<AppCompatTextView>(R.id.limit)
                .apply {
                    text =
                        resources.getString(R.string.dialog_add_joke_limit_pattern, 0, limit)
                }

            editText.doOnTextChanged { text, _, _, _ ->
                run {
                    limitText.text =
                        resources.getString(
                            R.string.dialog_add_joke_limit_pattern,
                            text?.length ?: 0,
                            limit
                        )
                }
            }

            builder.setTitle(R.string.dialog_add_joke_label)
                .setView(customView)
                .setPositiveButton(
                    android.R.string.ok
                ) { _, _ ->
                    editText.text?.toString()?.apply {
                        localJokesViewModel.addJoke(this)
                    }

                }
                .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }

            builder
        }?.show()
    }
}