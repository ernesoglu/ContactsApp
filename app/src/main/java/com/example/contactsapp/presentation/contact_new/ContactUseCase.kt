package com.example.contactsapp.presentation.contact_new

import com.example.contactsapp.domain.model.ContactDomainModel
import com.example.contactsapp.presentation.model.ContactPresentationModel
import com.example.contactsapp.presentation.model.ContactSortType
import io.reactivex.Completable
import io.reactivex.Single

interface ContactUseCase {
    fun addContact(model: ContactPresentationModel): Boolean
    fun editContact(model: ContactPresentationModel): Boolean
    fun deleteContact(model: ContactPresentationModel): Completable?
    fun getAllContacts(
        sortType: ContactSortType,
        accountId: String
    ): Single<List<ContactDomainModel>>?
}