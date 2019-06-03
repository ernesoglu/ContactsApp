package com.example.contactsapp.data.model

import com.example.contactsapp.domain.model.ContactDomainModel

object ContactDataMapper {
    fun contactDomainToDataModel(model: ContactDomainModel): ContactDataModel {
        return ContactDataModel(model.accountId, model.firstName, model.lastName, model.createdAt)
    }

    fun contactWithIdDomainToDataModel(model: ContactDomainModel): ContactDataModel {
        return ContactDataModel(
            model.id,
            model.accountId,
            model.firstName,
            model.lastName,
            model.createdAt
        )
    }

    fun contactPhoneDomainToDataModel(phoneNumber: String, contactId: Long): ContactPhoneNumber {
        return ContactPhoneNumber(phoneNumber, contactId)
    }

    fun contactEmailDomainToDataModel(email: String, contactId: Long): ContactEmail {
        return ContactEmail(email, contactId)
    }
}