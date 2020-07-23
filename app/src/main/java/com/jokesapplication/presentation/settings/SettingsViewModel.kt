package com.jokesapplication.presentation.settings

import androidx.lifecycle.LiveData
import com.jokesapplication.domain.preferences.PreferencesProvider
import com.jokesapplication.presentation.BaseViewModel
import com.jokesapplication.presentation.DispatcherProvider
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val preferencesProvider: PreferencesProvider,
    dispatcher: DispatcherProvider
) : BaseViewModel(dispatcher) {

    init {
        preferencesProvider.register()
    }

    val firstName: LiveData<String?> = preferencesProvider.getFirstNameLiveData()
    val lastName: LiveData<String?> = preferencesProvider.getLastNameLiveData()
    val offline: LiveData<Boolean> = preferencesProvider.isOfflineLiveData()

    fun saveFirstName(firstName: String?) {
        preferencesProvider.setFirstName(firstName)
    }

    fun saveLastName(lastName: String?) {
        preferencesProvider.setLastName(lastName)
    }

    fun saveMode(offline: Boolean) {
        preferencesProvider.setNetworkMode(offline)
    }

    override fun onCleared() {
        preferencesProvider.unregister()
        super.onCleared()
    }

}