package com.example.contactsapp.presentation.contact_new

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.contactsapp.domain.usecase.contact.ContactUseCaseImpl
import com.example.contactsapp.domain.usecase.contact.ContactUseCaseImplProvider
import com.example.contactsapp.presentation.model.ContactPresentationModel
import com.example.contactsapp.presentation.model.ContactSortType
import com.example.contactsapp.presentation.model.PresentationMapper
import com.example.contactsapp.util.disposeBy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class AddEditContactViewModel(application: Application) : AndroidViewModel(application) {
    private var contactUseCase: ContactUseCaseImpl? = ContactUseCaseImplProvider.getContactUseCaseImpl(application)

    private val updateResultLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val addResultLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val disposable = CompositeDisposable()

    fun addNewContact(
        accountId: String,
        firstName: String, lastName: String,
        phoneNumbers: List<String>, emails: List<String>, createdAt: Long
    ) {
        val model = PresentationMapper.getContactPresentationModel(
            accountId,
            firstName,
            lastName,
            phoneNumbers,
            emails,
            createdAt
        )
        addResultLiveData.value = contactUseCase?.addContact(model)

    }

    fun editContact(
        id: Long, accountId: String,
        firstName: String, lastName: String,
        phoneNumbers: List<String>, emails: List<String>, createdAt: Long
    ) {
        val model = PresentationMapper.getContactWithIdPresentationModel(
            id,
            accountId,
            firstName,
            lastName,
            phoneNumbers,
            emails,
            createdAt
        )
        updateResultLiveData.value = contactUseCase?.editContact(model)
    }

    fun getAddContactResult(): MutableLiveData<Boolean> {
        return addResultLiveData
    }

    fun getUpdateContactResult(): MutableLiveData<Boolean> {
        return updateResultLiveData
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}