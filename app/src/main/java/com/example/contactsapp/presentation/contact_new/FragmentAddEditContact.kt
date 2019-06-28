package com.example.contactsapp.presentation.contact_new

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.App
import com.example.contactsapp.R
import com.example.contactsapp.presentation.MainActivity
import com.example.contactsapp.presentation.model.ContactDataType
import com.example.contactsapp.presentation.model.ContactPresentationModel
import com.example.contactsapp.util.PreferenceUtils
import com.example.contactsapp.util.afterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_new_contact.*

class FragmentAddEditContact : Fragment(), OnContactDataListener {
    private val LAYOUT_RES = R.layout.fragment_new_contact

    private lateinit var tilFirstName: TextInputLayout
    private lateinit var etFirstName: TextInputEditText
    private lateinit var tilLastName: TextInputLayout
    private lateinit var etLastName: TextInputEditText
    private lateinit var rvContactPhones: RecyclerView
    private lateinit var rvContactEmails: RecyclerView

    private lateinit var contactPhoneAdapter: ContactPhoneAdapter
    private lateinit var contactEmailAdapter: ContactEmailAdapter
    private lateinit var viewModel: AddEditContactViewModel

    private var firstName: String = ""
    private var lastName: String = ""
    private var contactPhonesList: MutableList<String> = mutableListOf()
    private var contactEmailsList: MutableList<String> = mutableListOf()
    private var validateFields: Boolean = false

    private var contact: ContactPresentationModel? = null
    private var onEditContactListener: OnEditContactListener? = null

    private fun setOnEditContactListener(onEditContactListener: OnEditContactListener?){
        this.onEditContactListener = onEditContactListener
    }

    companion object{
        private val CONTACT = "CONTACT"

        fun newInstance(contact: ContactPresentationModel?,
                        onEditContactListener: OnEditContactListener?) : FragmentAddEditContact{
            val fragment = FragmentAddEditContact()
            fragment.setOnEditContactListener(onEditContactListener)
            val args  = Bundle()
            args.putSerializable(CONTACT, contact)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupContactDataIfExists()
        initViewModel()
    }

    private fun setupContactDataIfExists() {
        if (arguments?.getSerializable(CONTACT) != null){
            contact = arguments?.getSerializable(CONTACT) as ContactPresentationModel
        }

        if (contact == null) {
            contactPhonesList.add("")
            contactEmailsList.add("")
        } else {
            if (contact?.phoneNumbers?.size == 0){
                contactPhonesList.add("")
            } else {
                contact?.phoneNumbers?.map {
                    contactPhonesList.add(it)
                }
            }
            if (contact?.emails?.size == 0){
                contactEmailsList.add("")
            } else {
                contact?.emails?.map {
                    contactEmailsList.add(it)
                }
            }
        }

        contactPhoneAdapter = ContactPhoneAdapter(contactPhonesList, this)
        contactEmailAdapter = ContactEmailAdapter(contactEmailsList, this)
    }

    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this, AddEditContactVMFactory(App.instance))
            .get(AddEditContactViewModel::class.java)

        viewModel.getUpdateContactResult().observe(activity as MainActivity, Observer {
            Toast.makeText(activity, getString(R.string.contact_updated), Toast.LENGTH_SHORT).show()
            activity?.onBackPressed()
            onEditContactListener?.contactEdited()
        })

        viewModel.getAddContactResult().observe(activity as MainActivity, Observer {
            if (it)
                Toast.makeText(
                    context,
                    getString(R.string.contact_add_success),
                    Toast.LENGTH_SHORT
                ).show()
            else
                Toast.makeText(
                    context,
                    getString(R.string.contact_add_error),
                    Toast.LENGTH_SHORT
                ).show()
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(LAYOUT_RES, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setTextWatchers()
        initContactInfoList()
        fillViews()

    }

    private fun initViews() {
        tilFirstName = til_contact_first_name
        etFirstName = et_contact_first_name
        tilLastName = til_contact_last_name
        etLastName = et_contact_last_name
        rvContactPhones = rv_contact_phones
        rvContactEmails = rv_contact_emails
    }

    private fun initContactInfoList() {
        rvContactPhones.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvContactPhones.adapter = contactPhoneAdapter
        rvContactPhones.isNestedScrollingEnabled = false

        rvContactEmails.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvContactEmails.adapter = contactEmailAdapter
        rvContactEmails.isNestedScrollingEnabled = false
    }

    private fun fillViews() {
        etFirstName.setText(contact?.firstName)
        etLastName.setText(contact?.lastName)
    }


    private fun setTextWatchers() {
        etFirstName.afterTextChanged {
            if (validateFields) {
                validateFirstName()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            R.id.save -> {
                checkFields()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun checkFields() {
        validateFields = true
        firstName = etFirstName.text.toString()
        lastName = etLastName.text.toString()
        if (validateFirstName()) {
            addEditContact()
        }
    }

    private fun addEditContact() {
        contactPhonesList.map {
            if (it.isEmpty())
                contactPhonesList.remove(it)
        }
        contactEmailsList.map {
            if (it.isEmpty())
                contactEmailsList.remove(it)
        }
        if (contact == null) {
            viewModel.addNewContact(
                PreferenceUtils.getAccountId(),
                firstName,
                lastName,
                contactPhonesList,
                contactEmailsList,
                System.currentTimeMillis()
            )
        } else {
            contact?.let {
                viewModel.editContact(
                    it.id,
                    it.accountId,
                    firstName,
                    lastName,
                    contactPhonesList,
                    contactEmailsList,
                    it.createdAt
                )
            }
        }
        activity?.onBackPressed()
    }

    private fun validateFirstName(): Boolean {
        return if (firstName.length !in 2..50) {
            tilFirstName.error = getString(R.string.field_range_error)
            tilFirstName.isErrorEnabled = true
            firstName = etFirstName.text.toString()
            false
        } else {
            tilFirstName.isErrorEnabled = false
            firstName = etFirstName.text.toString()
            true
        }
    }

    override fun onNewContactDataOption(
        contactDataType: ContactDataType,
        position: Int,
        value: String
    ) {
        when (contactDataType) {
            ContactDataType.PHONE -> {
                contactPhonesList[position] = value
                if (position == contactPhonesList.size - 1)
                    contactPhonesList.add(
                        contactPhonesList.size,
                        ""
                    )
                contactPhoneAdapter.notifyItemInserted(contactPhonesList.size)
            }
            ContactDataType.EMAIL -> {
                contactEmailsList[position] = value
                if (position == contactEmailsList.size - 1)
                    contactEmailsList.add(
                        contactEmailsList.size,
                        ""
                    )
                contactEmailAdapter.notifyItemInserted(contactEmailsList.size)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.getUpdateContactResult().removeObservers(activity as MainActivity)
        viewModel.getAddContactResult().removeObservers(activity as MainActivity)
        if (onEditContactListener == null)
            (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}