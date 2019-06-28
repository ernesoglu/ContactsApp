package com.example.contactsapp.presentation.contacts_list

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.App
import com.example.contactsapp.R
import com.example.contactsapp.presentation.MainActivity
import com.example.contactsapp.presentation.auth.AuthActivity
import com.example.contactsapp.presentation.contact_info.FragmentContactInfo
import com.example.contactsapp.presentation.contact_new.AddEditContactVMFactory
import com.example.contactsapp.presentation.contact_new.AddEditContactViewModel
import com.example.contactsapp.presentation.contact_new.FragmentAddEditContact
import com.example.contactsapp.presentation.model.ContactPresentationModel
import com.example.contactsapp.presentation.model.ContactSortType
import com.example.contactsapp.util.PreferenceUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_contact_list.*

class FragmentContactList : Fragment(),
    ContactsListAdapter.OnContactClickListener {
    private val LAYOUT_RES = R.layout.fragment_contact_list

    private lateinit var rvContacts: RecyclerView
    private lateinit var fabAdd: FloatingActionButton

    private lateinit var contactsListAdapter: ContactsListAdapter
    private var contacts: MutableList<ContactPresentationModel> = mutableListOf()

    private lateinit var viewModel: ContactListViewModel
    private var currentSortType = ContactSortType.CREATION_TIME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)
        contactsListAdapter = ContactsListAdapter(contacts, this)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ContactListVMFactory(App.instance))
            .get(ContactListViewModel::class.java)

        viewModel.getAllContactsLiveData().observe(activity as MainActivity, Observer {
            contacts.clear()
            contacts.addAll(it)
            contactsListAdapter.notifyDataSetChanged()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(LAYOUT_RES, container, false)
        viewModel.getAllContacts(currentSortType, PreferenceUtils.getAccountId())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setClickListeners()

        rvContacts.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvContacts.adapter = contactsListAdapter
    }

    private fun initViews() {
        rvContacts = rv_contacts
        fabAdd = fab_new_contact
    }

    private fun setClickListeners() {
        fabAdd.setOnClickListener {
            (activity as MainActivity).replaceFragment(
                FragmentAddEditContact.newInstance(
                    null,
                    null
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sort, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                App.instance.getGoogleSignInClient().signOut().addOnCompleteListener {
                    startAuthActivity()
                }
                true
            }
            R.id.sort_by_first_name -> {
                currentSortType = ContactSortType.NAME
                viewModel.getAllContacts(currentSortType, PreferenceUtils.getAccountId())
                true
            }
            R.id.sort_by_info -> {
                currentSortType = ContactSortType.CONTACT_DATA
                viewModel.getAllContacts(currentSortType, PreferenceUtils.getAccountId())
                true
            }
            R.id.sort_by_date_added -> {
                currentSortType = ContactSortType.CREATION_TIME
                viewModel.getAllContacts(currentSortType, PreferenceUtils.getAccountId())
                true
            }
            else -> true
        }
    }

    override fun onContactClicked(position: Int) {
        (activity as MainActivity).replaceFragment(FragmentContactInfo.newInstance(contacts[position]))
    }

    private fun startAuthActivity() {
        val intent = Intent(activity, AuthActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.getAllContactsLiveData().removeObservers(activity as MainActivity)
    }
}