package com.example.contactsapp.presentation.model

import com.example.contactsapp.domain.model.ContactDomainModel

object PresentationMapper {

    fun getContactPresentationModel(
        accountId: String,
        firstName: String, lastName: String,
        phoneNumbers: List<String>, emails: List<String>, createdAt: Long
    ): ContactPresentationModel {
        return ContactPresentationModel(
            accountId,
            firstName,
            lastName,
            phoneNumbers,
            emails,
            createdAt
        )
    }

    fun getContactWithIdPresentationModel(
        id: Long, accountId: String,
        firstName: String, lastName: String,
        phoneNumbers: List<String>, emails: List<String>, createdAt: Long
    ): ContactPresentationModel {
        return ContactPresentationModel(
            id,
            accountId,
            firstName,
            lastName,
            phoneNumbers,
            emails,
            createdAt
        )
    }

    fun contactDomainToPresentationModel(contacts: List<ContactDomainModel>): List<ContactPresentationModel> {
        return contacts.map { contact ->
            ContactPresentationModel(
                contact.id,
                contact.accountId,
                contact.firstName,
                contact.lastName,
                contact.phoneNumbers,
                contact.emails,
                contact.createdAt
            )
        }
    }
}