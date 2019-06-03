package com.example.contactsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactsapp.data.dao.ContactEmailsDao
import com.example.contactsapp.data.dao.ContactPhonesDao
import com.example.contactsapp.data.dao.ContactsDao
import com.example.contactsapp.data.model.ContactDataModel
import com.example.contactsapp.data.model.ContactEmail
import com.example.contactsapp.data.model.ContactPhoneNumber

@Database(
    entities = arrayOf(ContactDataModel::class, ContactPhoneNumber::class, ContactEmail::class),
    version = 1, exportSchema = false
)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
    abstract fun contactPhonesDao(): ContactPhonesDao
    abstract fun contactEmailsDao(): ContactEmailsDao

    companion object {
        private var INSTANCE: ContactsDatabase? = null

        fun getInstance(context: Context): ContactsDatabase? {
            if (INSTANCE == null) {
                synchronized(ContactsDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ContactsDatabase::class.java, "contacts.db"
                    )
                        .allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }
}