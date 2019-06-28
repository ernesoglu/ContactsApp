package com.example.contactsapp.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contactsapp.App
import com.example.contactsapp.R
import com.example.contactsapp.presentation.MainActivity
import com.example.contactsapp.util.PreferenceUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_auth.*


class AuthActivity : AppCompatActivity() {

    private lateinit var signInButton: SignInButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        initViews()
        setClickListeners()
    }

    private fun initViews() {
        signInButton = btn_sign_in
    }

    private fun setClickListeners() {
        signInButton.setOnClickListener {
            val signInIntent = App.instance.getGoogleSignInClient().signInIntent
            startActivityForResult(signInIntent, 101)
        }

    }

    override fun onStart() {
        super.onStart()
        val alreadyLogginInAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (alreadyLogginInAccount != null) {
            startMainActivity()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                101 -> {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    val account = task.getResult(ApiException::class.java)
                    account?.id?.let {
                        PreferenceUtils.saveAccountId(it)
                    }
                    account?.displayName?.let {
                        PreferenceUtils.saveAccountName(it)
                    }
                    startMainActivity()

                }
            }
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}