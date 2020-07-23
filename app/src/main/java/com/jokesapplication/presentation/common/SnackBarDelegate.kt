package com.jokesapplication.presentation.common

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jokesapplication.R

fun Fragment.showErrorDialog(t: Throwable) {
    /**
     * @param t
     * Parsing not implemented
     */
    view?.let {
        Snackbar.make(it, R.string.error_msg, Snackbar.LENGTH_LONG).show()
    }

}