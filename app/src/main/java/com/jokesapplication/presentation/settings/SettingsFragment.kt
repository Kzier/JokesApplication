package com.jokesapplication.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.SwitchCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jokesapplication.R
import com.jokesapplication.injection.JokeApp
import javax.inject.Inject

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var firstName: AppCompatEditText
    private lateinit var lastName: AppCompatEditText
    private lateinit var offlineSwitcher: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as? JokeApp)?.appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
            ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        setupUi(root)

        observeData()
        return root
    }

    private fun observeData() {
        settingsViewModel.firstName.observe(this.viewLifecycleOwner, Observer {
            it?.also {
                firstName.setText(it)
            }
        })
        settingsViewModel.lastName.observe(this.viewLifecycleOwner, Observer {
            it?.also {
                lastName.setText(it)
            }
        })
        settingsViewModel.offline.observe(this.viewLifecycleOwner, Observer {
            if (offlineSwitcher.isChecked != it) offlineSwitcher.isChecked = it
        })
    }

    private fun setupUi(root: View) {
        firstName = root.findViewById<AppCompatEditText>(R.id.first_name)
            .apply {
                doAfterTextChanged {
                    settingsViewModel.saveFirstName(it?.toString())
                }
            }
        lastName = root.findViewById<AppCompatEditText>(R.id.last_name).apply {
            doAfterTextChanged {
                settingsViewModel.saveLastName(it?.toString())
            }
        }
        offlineSwitcher = root.findViewById<SwitchCompat>(R.id.offline_switcher).apply {
            setOnCheckedChangeListener { _, isChecked -> settingsViewModel.saveMode(isChecked) }
        }
    }

}