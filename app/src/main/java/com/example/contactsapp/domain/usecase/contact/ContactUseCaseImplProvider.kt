package com.example.contactsapp.domain.usecase.contact

import android.app.Application

class ContactUseCaseImplProvider {
    companion object{
        private var contactUseCaseImpl : ContactUseCaseImpl? = null

        fun getContactUseCaseImpl(application: Application): ContactUseCaseImpl?{
            if (contactUseCaseImpl == null){
                contactUseCaseImpl = ContactUseCaseImpl(application)
            }
            return contactUseCaseImpl
        }
    }
}