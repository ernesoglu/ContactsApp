package com.example.contactsapp.domain.model

class ContactDomainModel(
    var id: Long,
    var accountId: String,
    var firstName: String,
    var lastName: String?,
    var phoneNumbers: List<String>,
    var emails: List<String>,
    var createdAt: Long
) {
    constructor(
        accountId: String,
        firstName: String,
        lastName: String?,
        phoneNumbers: List<String>,
        emails: List<String>,
        createdAt: Long
    ) : this(0, accountId, firstName, lastName, phoneNumbers, emails, createdAt)
}