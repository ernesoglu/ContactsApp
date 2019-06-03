package com.example.contactsapp.util

import com.orhanobut.hawk.Hawk

open class PreferenceUtils {

    companion object {
        private val ACCOUNT_ID = "ACCOUNT_ID"
        private val ACCOUNT_NAME = "ACCOUNT_NAME"

        fun saveAccountId(accountId: String) {
            Hawk.put(ACCOUNT_ID, accountId)
        }

        fun getAccountId(): String {
            return Hawk.get(ACCOUNT_ID, "")
        }

        fun saveAccountName(accountName: String) {
            Hawk.put(ACCOUNT_NAME, accountName)
        }

        fun getAccountName(): String {
            return Hawk.get(ACCOUNT_NAME, "")
        }
    }
}