package com.example.contactsapp.domain.model

import com.example.contactsapp.data.model.ContactFullData
import com.example.contactsapp.presentation.model.ContactPresentationModel

object ContactDomainMapper {

    fun contactPresentationToDomainModel(model: ContactPresentationModel): ContactDomainModel {
        return ContactDomainModel(
            model.accountId,
            model.firstName,
            model.lastName,
            model.phoneNumbers,
            model.emails,
            model.createdAt
        )
    }

    fun contactWithIdPresentationToDomainModel(model: ContactPresentationModel): ContactDomainModel {
        return ContactDomainModel(
            model.id,
            model.accountId,
            model.firstName,
            model.lastName,
            model.phoneNumbers,
            model.emails,
            model.createdAt
        )
    }

    fun contactDataToDomainModel(contacts: List<ContactFullData>): List<ContactDomainModel> {
        return contacts.map { contact ->
            ContactDomainModel(
                contact.contactDataModel.id,
                contact.contactDataModel.accountId,
                contact.contactDataModel.firstName,
                contact.contactDataModel.lastName,
                contact.contactPhoneNumbers.map {
                    it.phoneNumber
                },
                contact.contactEmails.map {
                    it.email
                },
                contact.contactDataModel.createdAt
            )
        }
    }
}