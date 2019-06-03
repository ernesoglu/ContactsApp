package com.example.contactsapp.data.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.contactsapp.data.model.ContactDataModel
import com.example.contactsapp.data.model.ContactFullData
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface ContactsDao {

    @Insert(onConflict = REPLACE)
    fun insert(contactData: ContactDataModel): Long

    @Update
    fun update(contactData: ContactDataModel)

    @Transaction
    @Query("SELECT * FROM contacts_data WHERE accountId = :accountId")
    fun getAll(accountId: String): Single<List<ContactFullData>>

    @Transaction
    @Query("SELECT * FROM contacts_data WHERE accountId = :accountId ORDER BY firstName")
    fun getAllByName(accountId: String): Single<List<ContactFullData>>

    @Transaction
    @Query(
        "SELECT id, accountId, firstName, lastName, createdAt, COUNT(id) AS COUNT FROM contacts_data \n" +
                "LEFT JOIN contact_phone_numbers ON contact_phone_numbers.contact_id=id\n" +
                "LEFT JOIN contact_emails ON contact_emails.contact_id=id\n" +
                "WHERE accountId = :accountId GROUP BY id\n" +
                "ORDER BY COUNT"
    )
    fun getAllByContactInfo(accountId: String): Single<List<ContactFullData>>

    @Transaction
    @Query("SELECT * FROM contacts_data WHERE accountId = :accountId ORDER BY createdAt")
    fun getAllByCreation(accountId: String): Single<List<ContactFullData>>

    @Delete
    fun delete(contactData: ContactDataModel): Completable

}