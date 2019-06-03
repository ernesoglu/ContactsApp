package com.example.contactsapp.data.dao

import androidx.room.*
import com.example.contactsapp.data.model.ContactEmail

@Dao
interface ContactEmailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contactEmail: ContactEmail)

    @Update
    fun update(contactEmail: ContactEmail)

    @Transaction
    @Query("SELECT * from contact_emails")
    fun getAll(): List<ContactEmail>

    @Query("DELETE FROM contact_emails WHERE contact_id = :contactId")
    fun deleteAll(contactId: Long)

    @Delete
    fun delete(contactEmail: ContactEmail)
}