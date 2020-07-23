package com.jokesapplication.domain.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

private const val MODE_KEY = "current-mode"
private const val LAST_NAME_KEY = "current-last-name"
private const val FIRST_NAME_KEY = "current-first-name"

class PreferencesImpl(private val sp: SharedPreferences) : PreferencesProvider {

    private val listener
        get() = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                MODE_KEY -> isOffline.value = isOffline()
                FIRST_NAME_KEY -> firstName.value = getFirstName()
                LAST_NAME_KEY -> lastName.value = getLastName()
            }
        }

    private val firstName
        get() = MutableLiveData<String?>().apply {
            value = sp.getString(FIRST_NAME_KEY, null)
        }

    private val lastName
        get() = MutableLiveData<String?>().apply {
            value = sp.getString(LAST_NAME_KEY, null)
        }

    private val isOffline
        get() = MutableLiveData<Boolean>().apply {
            value = sp.getBoolean(MODE_KEY, false)
        }

    override fun getFirstName(): String? = sp.getString(FIRST_NAME_KEY, null)

    override fun getLastName(): String? = sp.getString(LAST_NAME_KEY, null)

    override fun getFirstNameLiveData(): LiveData<String?> = firstName

    override fun getLastNameLiveData(): LiveData<String?> = lastName

    override fun isOfflineLiveData(): LiveData<Boolean> = isOffline

    override fun setFirstName(firstName: String?) = sp.edit {
        putString(FIRST_NAME_KEY, firstName.prepareValue())
        commit()
    }

    override fun setLastName(lastName: String?) = sp.edit {
        putString(LAST_NAME_KEY, lastName.prepareValue())
        commit()
    }

    override fun isOffline(): Boolean = sp.getBoolean(MODE_KEY, false)

    override fun setNetworkMode(offline: Boolean) {
        sp.edit {
            putBoolean(MODE_KEY, offline)
            commit()
        }
    }

    override fun setSettings(firstName: String?, lastName: String?, offline: Boolean) {
        sp.edit {
            putString(FIRST_NAME_KEY, firstName.prepareValue())
            putString(LAST_NAME_KEY, lastName.prepareValue())
            putBoolean(MODE_KEY, offline)
        }
    }

    override fun register() {
        sp.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun unregister() {
        sp.unregisterOnSharedPreferenceChangeListener(listener)
    }

    private fun String?.prepareValue(): String? {
        return if (this.isNullOrEmpty()) null else this
    }

}