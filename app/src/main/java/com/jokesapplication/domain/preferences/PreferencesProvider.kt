package com.jokesapplication.domain.preferences

import androidx.lifecycle.LiveData

interface PreferencesProvider {

    fun getFirstName(): String?
    fun getLastName(): String?

    fun getFirstNameLiveData(): LiveData<String?>
    fun getLastNameLiveData(): LiveData<String?>

    fun setFirstName(firstName: String?)
    fun setLastName(lastName: String?)
    fun setSettings(firstName: String?, lastName: String?, offline: Boolean)

    fun setNetworkMode(offline: Boolean)
    fun isOfflineLiveData(): LiveData<Boolean>
    fun isOffline(): Boolean


    fun register()
    fun unregister()

}