package com.example.contactsapp

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.orhanobut.hawk.Hawk
import io.github.skyhacker2.sqliteonweb.SQLiteOnWeb

class App : Application() {

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate() {
        super.onCreate()
        instance = this

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        SQLiteOnWeb.init(this).start()
        Hawk.init(this).build()
    }

    fun getGoogleSignInClient(): GoogleSignInClient {
        return googleSignInClient
    }

    companion object {
        lateinit var instance: App
    }
}