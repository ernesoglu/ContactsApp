package com.example.contactsapp.data.repository

import android.app.Application

class ContactGatewayImplProvider {
    companion object{
        private var contactGatewayImpl : ContactGatewayImpl? = null

        fun getContactUseCaseImpl(application: Application): ContactGatewayImpl?{
            if (contactGatewayImpl == null){
                contactGatewayImpl = ContactGatewayImpl(application)
            }
            return contactGatewayImpl
        }
    }
}