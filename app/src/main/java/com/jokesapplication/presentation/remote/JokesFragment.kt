package com.jokesapplication.presentation.remote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.jokesapplication.R
import com.jokesapplication.domain.entity.Joke
import com.jokesapplication.domain.entity.JokeLocal
import com.jokesapplication.injection.JokeApp
import com.jokesapplication.presentation.adapter.JokeAdapter
import com.jokesapplication.presentation.adapter.JokeDelegate
import com.jokesapplication.presentation.adapter.LocalJokeDelegate
import com.jokesapplication.presentation.common.shake.ShakeDetectorDelegate
import com.jokesapplication.presentation.common.showErrorDialog
import com.jokesapplication.presentation.share.showShareTextChooser
import javax.inject.Inject

class JokesFragment : Fragment(), ShakeDetectorDelegate.ShakeListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var shakeDetectorDelegate: ShakeDetectorDelegate

    private lateinit var jokeViewModel: JokeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as? JokeApp)?.appComponent?.inject(this)
        shakeDetectorDelegate.init(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        jokeViewModel = ViewModelProvider(this, viewModelFactory).get(JokeViewModel::class.java)
        shakeDetectorDelegate.setShakeListener(this)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerView)

        val adapter = JokeAdapter(
            LocalJokeDelegate(object :
                LocalJokeDelegate.Callback {

                override fun remove(obj: JokeLocal) {
                    /**
                     * Will implemented later
                     */
                }

            }), JokeDelegate(object : JokeDelegate.Callback {
                override fun share(obj: Joke) {
                    activity?.showShareTextChooser(obj.joke)
                }

                override fun like(obj: Joke) {
                    jokeViewModel.likeJoke(obj)
                }

            })
        )

        recyclerView.adapter = adapter

        jokeViewModel.jokes.observe(this.viewLifecycleOwner, Observer {
            it?.also {
                adapter.submitList(it)
            }
        })

        jokeViewModel.error.observe(this.viewLifecycleOwner, Observer {
            showErrorDialog(it)
        })
        return root
    }

    override fun onShake() {
        jokeViewModel.random()
    }

}