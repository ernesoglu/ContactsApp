package com.example.contactsapp.domain.usecase.contact

import android.app.Application
import com.example.contactsapp.data.repository.ContactGatewayImpl
import com.example.contactsapp.data.repository.ContactGatewayImplProvider
import com.example.contactsapp.domain.model.ContactDomainMapper
import com.example.contactsapp.domain.model.ContactDomainModel
import com.example.contactsapp.presentation.contact_new.ContactUseCase
import com.example.contactsapp.presentation.model.ContactPresentationModel
import com.example.contactsapp.presentation.model.ContactSortType
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ContactUseCaseImpl(application: Application) : ContactUseCase {
    private val contactGateway: ContactGatewayImpl? = ContactGatewayImplProvider.getContactUseCaseImpl(application)

    override fun addContact(model: ContactPresentationModel): Boolean {
        val domainModel = ContactDomainMapper.contactPresentationToDomainModel(model)
        val contactId = contactGateway?.addContact(domainModel)
        for (phone in model.phoneNumbers) {
            if (contactId != null) {
                contactGateway?.addContactPhone(phone, contactId)
            }
        }
        for (email in model.emails) {
            if (contactId != null) {
                contactGateway?.addContactEmail(email, contactId)
            }
        }
        return true
    }

    override fun editContact(model: ContactPresentationModel): Boolean {
        val domainModel = ContactDomainMapper.contactWithIdPresentationToDomainModel(model)
        contactGateway?.editContact(domainModel)
        contactGateway?.deleteAllPhoneNumbers(domainModel.id)
        for (phone in model.phoneNumbers) {
            contactGateway?.addContactPhone(phone, domainModel.id)
        }
        contactGateway?.deleteAllEmails(domainModel.id)
        for (email in model.emails) {
            contactGateway?.addContactEmail(email, domainModel.id)
        }
        return true
    }

    override fun deleteContact(model: ContactPresentationModel): Completable? {
        val domainModel = ContactDomainMapper.contactWithIdPresentationToDomainModel(model)
        return contactGateway?.deleteContact(domainModel)?.doOnComplete {
            contactGateway.deleteAllPhoneNumbers(domainModel.id)
            contactGateway.deleteAllEmails(domainModel.id)
        }?.doOnError { }?.subscribeOn(Schedulers.io())
    }


    override fun getAllContacts(
        sortType: ContactSortType,
        accountId: String
    ): Single<List<ContactDomainModel>>? {
        return contactGateway?.getAllContacts(sortType, accountId)?.map {
            ContactDomainMapper.contactDataToDomainModel(it)
        }?.subscribeOn(Schedulers.io())
    }
}