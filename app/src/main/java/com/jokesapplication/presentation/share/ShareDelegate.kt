package com.jokesapplication.presentation.share

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.jokesapplication.R


fun Context.showShareTextChooser(text: String) {
    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(Intent.EXTRA_TEXT, text)
    sendIntent.type = "text/plain"
    startActivity(Intent.createChooser(sendIntent, resources.getString(R.string.chooser_share_title)))
}