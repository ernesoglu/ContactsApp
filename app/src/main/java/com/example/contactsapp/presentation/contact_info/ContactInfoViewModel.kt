package com.example.contactsapp.presentation.contact_info

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.contactsapp.domain.usecase.contact.ContactUseCaseImpl
import com.example.contactsapp.domain.usecase.contact.ContactUseCaseImplProvider
import com.example.contactsapp.presentation.contact_new.ContactUseCase
import com.example.contactsapp.presentation.model.ContactPresentationModel
import com.example.contactsapp.util.disposeBy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ContactInfoViewModel(application: Application) : AndroidViewModel(application) {
    private var contactUseCase: ContactUseCaseImpl? = ContactUseCaseImplProvider.getContactUseCaseImpl(application)
    private val deleteResultLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val disposable = CompositeDisposable()

    fun deleteContact(contactPresentationModel: ContactPresentationModel) {
        contactUseCase?.deleteContact(contactPresentationModel)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                deleteResultLiveData.value = true
            }?.disposeBy(disposable)
    }

    fun getDeleteResultLiveData(): MutableLiveData<Boolean> {
        return deleteResultLiveData
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}