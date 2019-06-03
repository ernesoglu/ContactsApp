package com.example.contactsapp.presentation

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.contactsapp.R
import com.example.contactsapp.presentation.contacts_list.FragmentContactList
import com.example.contactsapp.util.PreferenceUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var container: ViewGroup

    private var lastTimeCallExit = System.currentTimeMillis()

    private lateinit var mAddEditContactFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = PreferenceUtils.getAccountName()
        initViews()
        replaceFragment(FragmentContactList())
    }

    private fun initViews() {
        container = frame_container
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment, fragment::class.java.simpleName)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            if (lastTimeCallExit + 2000L > System.currentTimeMillis()) {
                finish()
            } else {
                lastTimeCallExit = System.currentTimeMillis()
                Toast.makeText(
                    this,
                    getString(R.string.double_tap_to_exit),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            super.onBackPressed()
        }
    }

}
