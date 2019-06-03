package com.example.contactsapp.presentation.contacts_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.contactsapp.domain.usecase.contact.ContactUseCaseImpl
import com.example.contactsapp.domain.usecase.contact.ContactUseCaseImplProvider
import com.example.contactsapp.presentation.contact_new.ContactUseCase
import com.example.contactsapp.presentation.model.ContactPresentationModel
import com.example.contactsapp.presentation.model.ContactSortType
import com.example.contactsapp.presentation.model.PresentationMapper
import com.example.contactsapp.util.disposeBy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ContactListViewModel (application: Application) : AndroidViewModel(application) {
    private var contactUseCase: ContactUseCase? = ContactUseCaseImplProvider.getContactUseCaseImpl(application)
    private var allContactsLiveData: MutableLiveData<List<ContactPresentationModel>> =
        MutableLiveData()
    private val disposable = CompositeDisposable()

    fun getAllContacts(sortType: ContactSortType, accountId: String) {
        contactUseCase?.getAllContacts(sortType, accountId)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.map {
                PresentationMapper.contactDomainToPresentationModel(it)
            }?.subscribe { it ->
                allContactsLiveData.value = it
            }?.disposeBy(disposable)
    }

    fun getAllContactsLiveData(): MutableLiveData<List<ContactPresentationModel>> {
        return allContactsLiveData
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}