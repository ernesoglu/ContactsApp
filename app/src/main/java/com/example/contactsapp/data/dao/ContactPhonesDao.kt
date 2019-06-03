package com.example.contactsapp.data.dao

import androidx.room.*
import com.example.contactsapp.data.model.ContactPhoneNumber

@Dao
interface ContactPhonesDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contactPhone: ContactPhoneNumber)

    @Update
    fun update(contactPhone: ContactPhoneNumber)

    @Transaction
    @Query("SELECT * from contact_phone_numbers")
    fun getAll(): List<ContactPhoneNumber>

    @Query("DELETE FROM contact_phone_numbers WHERE contact_id = :contactId")
    fun deleteAll(contactId: Long)

    @Delete
    fun delete(contactPhone: ContactPhoneNumber)
}