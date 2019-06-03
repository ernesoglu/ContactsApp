package com.example.contactsapp.presentation.model

import java.io.Serializable

class ContactPresentationModel(
    var id: Long,
    var accountId: String,
    var firstName: String,
    var lastName: String?,
    var phoneNumbers: List<String>,
    var emails: List<String>,
    var createdAt: Long
) : Serializable{
    constructor(
        accountId: String,
        firstName: String,
        lastName: String?,
        phoneNumbers: List<String>,
        emails: List<String>,
        createdAt: Long
    ) : this(0, accountId, firstName, lastName, phoneNumbers, emails, createdAt)
}