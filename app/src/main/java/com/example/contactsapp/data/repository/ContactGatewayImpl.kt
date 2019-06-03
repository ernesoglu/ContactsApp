package com.example.contactsapp.data.repository

import android.app.Application
import com.example.contactsapp.data.db.ContactsDatabase
import com.example.contactsapp.data.model.ContactDataMapper
import com.example.contactsapp.data.model.ContactFullData
import com.example.contactsapp.domain.model.ContactDomainModel
import com.example.contactsapp.domain.usecase.contact.ContactGateway
import com.example.contactsapp.presentation.model.ContactSortType
import io.reactivex.Completable
import io.reactivex.Single

class ContactGatewayImpl(application: Application) : ContactGateway {
    private val database = ContactsDatabase.getInstance(application)

    override fun addContact(contactDomainModel: ContactDomainModel): Long? {
        val dataModel = ContactDataMapper.contactDomainToDataModel(contactDomainModel)
        return database?.contactsDao()?.insert(dataModel)
    }

    override fun addContactPhone(phoneNumber: String, contactId: Long) {
        val phoneModel = ContactDataMapper.contactPhoneDomainToDataModel(phoneNumber, contactId)
        database?.contactPhonesDao()?.insert(phoneModel)
    }

    override fun addContactEmail(email: String, contactId: Long) {
        val emailModel = ContactDataMapper.contactEmailDomainToDataModel(email, contactId)
        database?.contactEmailsDao()?.insert(emailModel)
    }

    override fun editContact(contactDomainModel: ContactDomainModel) {
        val dataModel = ContactDataMapper.contactWithIdDomainToDataModel(contactDomainModel)
        database?.contactsDao()?.update(dataModel)
    }

    override fun getAllContacts(
        sortType: ContactSortType,
        accountId: String
    ): Single<List<ContactFullData>>? {
        return when (sortType) {
            ContactSortType.DEFAULT -> {
                database?.contactsDao()?.getAll(accountId)
            }
            ContactSortType.NAME -> {
                database?.contactsDao()?.getAllByName(accountId)
            }
            ContactSortType.CONTACT_DATA -> {
                database?.contactsDao()?.getAllByContactInfo(accountId)
            }
            ContactSortType.CREATION_TIME -> {
                database?.contactsDao()?.getAllByCreation(accountId)
            }
        }
    }

    override fun deleteAllPhoneNumbers(id: Long) {
        database?.contactPhonesDao()?.deleteAll(id)
    }

    override fun deleteAllEmails(id: Long) {
        database?.contactEmailsDao()?.deleteAll(id)
    }

    override fun deleteContact(contactDomainModel: ContactDomainModel): Completable? {
        val dataModel = ContactDataMapper.contactWithIdDomainToDataModel(contactDomainModel)
        return database?.contactsDao()?.delete(dataModel)
    }
}