package com.example.contactsapp.presentation.contact_info

interface OnContactInfoDataListener {
    fun onPhoneClicked(phoneNumber: String)
    fun onEmailClicked(email: String)
}