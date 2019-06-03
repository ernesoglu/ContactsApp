package com.example.contactsapp.data.model

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "contacts_data")
data class ContactDataModel(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0,
    @ColumnInfo(name = "accountId") var accountId: String,
    @ColumnInfo(name = "firstName") var firstName: String,
    @ColumnInfo(name = "lastName") var lastName: String?,
    @ColumnInfo(name = "createdAt") var createdAt: Long
) : Serializable {
    constructor(accountId: String, firstName: String, lastName: String?, createdAt: Long) : this(
        0,
        accountId,
        firstName,
        lastName,
        createdAt
    )
}

@Entity(
    tableName = "contact_phone_numbers",
    indices = [androidx.room.Index("contact_id")],
    foreignKeys = [ForeignKey(
        entity = ContactDataModel::class,
        parentColumns = ["id"],
        childColumns = ["contact_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ContactPhoneNumber(
    @PrimaryKey(autoGenerate = true) var phoneId: Long,
    @ColumnInfo(name = "contact_id")
    var contactId: Long,
    var phoneNumber: String
) {
    constructor(phoneNumber: String, contactId: Long) : this(0, contactId, phoneNumber)
}

@Entity(
    tableName = "contact_emails",
    indices = [androidx.room.Index("contact_id")],
    foreignKeys = [ForeignKey(
        entity = ContactDataModel::class,
        parentColumns = ["id"],
        childColumns = ["contact_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ContactEmail(
    @PrimaryKey(autoGenerate = true) var emailId: Long,
    @ColumnInfo(name = "contact_id")
    var contactId: Long,
    var email: String
) {
    constructor(email: String, contactId: Long) : this(0, contactId, email)
}

data class ContactFullData(
    @Embedded
    var contactDataModel: ContactDataModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "contact_id"
    )
    var contactPhoneNumbers: List<ContactPhoneNumber>,
    @Relation(
        parentColumn = "id",
        entityColumn = "contact_id"
    )
    var contactEmails: List<ContactEmail>
) : Serializable