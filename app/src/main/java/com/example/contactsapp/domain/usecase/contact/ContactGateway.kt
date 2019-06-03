package com.example.contactsapp.domain.usecase.contact

import com.example.contactsapp.data.model.ContactFullData
import com.example.contactsapp.domain.model.ContactDomainModel
import com.example.contactsapp.presentation.model.ContactSortType
import io.reactivex.Completable
import io.reactivex.Single

interface ContactGateway {
    fun addContact(contactDomainModel: ContactDomainModel): Long?
    fun addContactPhone(phoneNumber: String, contactId: Long)
    fun addContactEmail(email: String, contactId: Long)
    fun editContact(contactDomainModel: ContactDomainModel)
    fun getAllContacts(sortType: ContactSortType, accountId: String): Single<List<ContactFullData>>?
    fun deleteAllPhoneNumbers(id: Long)
    fun deleteAllEmails(id: Long)
    fun deleteContact(contactDomainModel: ContactDomainModel): Completable?
}