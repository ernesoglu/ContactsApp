package com.example.contactsapp.presentation.contact_new

import com.example.contactsapp.presentation.model.ContactDataType

interface OnContactDataListener {
    fun onNewContactDataOption(contactDataType: ContactDataType, position: Int, value: String)
}