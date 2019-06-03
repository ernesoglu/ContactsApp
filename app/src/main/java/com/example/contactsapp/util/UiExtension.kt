package com.example.contactsapp.util

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
        ) {
            // Ignore.
        }

        override fun onTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
        ) {
            // Ignore.
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}