package com.example.contactsapp.presentation.contact_info

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.App
import com.example.contactsapp.R
import com.example.contactsapp.presentation.MainActivity
import com.example.contactsapp.presentation.contact_new.FragmentAddEditContact
import com.example.contactsapp.presentation.contact_new.OnEditContactListener
import com.example.contactsapp.presentation.model.ContactDataType
import com.example.contactsapp.presentation.model.ContactInfoData
import com.example.contactsapp.presentation.model.ContactPresentationModel
import kotlinx.android.synthetic.main.fragment_contact_info.*


class FragmentContactInfo(
) : Fragment(), OnContactInfoDataListener, OnEditContactListener {

    private val LAYOUT_RES = R.layout.fragment_contact_info

    private lateinit var ivContactAvatar: ImageView
    private lateinit var tvContactName: TextView
    private lateinit var rvContactInfoData: RecyclerView

    private lateinit var contactInfoAdapter: ContactInfoAdapter
    private var contactInfoDataList = mutableListOf<ContactInfoData>()

    private lateinit var viewModel: ContactInfoViewModel
    private lateinit var contact: ContactPresentationModel

    companion object{
        private val CONTACT = "CONTACT"

        fun newInstance(contact: ContactPresentationModel) : FragmentContactInfo{
            val fragment = FragmentContactInfo()
            var args  = Bundle()
            args.putSerializable(CONTACT, contact)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContactData()
        initViewModel()
    }

    private fun setContactData() {
        contact = arguments?.getSerializable(CONTACT) as ContactPresentationModel

        contact.phoneNumbers.map {
            contactInfoDataList.add(ContactInfoData(ContactDataType.PHONE, it))
        }
        contact.emails.map {
            contactInfoDataList.add(ContactInfoData(ContactDataType.EMAIL, it))
        }
        contactInfoAdapter = ContactInfoAdapter(contactInfoDataList, this)
    }

    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this, ContactInfoVMFactory(App.instance))
            .get(ContactInfoViewModel::class.java)

        viewModel.getDeleteResultLiveData().observe(activity as MainActivity, Observer {
            if (it) {
                Toast.makeText(activity, getString(R.string.contact_deleted), Toast.LENGTH_SHORT)
                    .show()
                activity?.onBackPressed()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(LAYOUT_RES, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        fillViews()

        rvContactInfoData.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvContactInfoData.adapter = contactInfoAdapter
    }

    private fun initViews() {
        ivContactAvatar = iv_contact_info_avatar
        tvContactName = tv_contact_info_name
        rvContactInfoData = rv_contact_data
    }

    @SuppressLint("SetTextI18n")
    private fun fillViews() {
        context?.let { ctx ->
            ivContactAvatar.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorPrimary))
        }
        tvContactName.text = contact.firstName + " " + contact.lastName
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            R.id.delete -> {
                viewModel.deleteContact(contact)
                true
            }
            R.id.edit -> {
                (activity as MainActivity).replaceFragment(FragmentAddEditContact.newInstance(contact, this))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onPhoneClicked(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))
        startActivity(intent)
    }

    override fun onEmailClicked(email: String) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "plain/text"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        startActivity(
            Intent.createChooser(
                emailIntent, context?.resources?.getText(R.string.write_email)
            )
        )
    }

    override fun contactEdited() {
        activity?.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.getDeleteResultLiveData().removeObservers(activity as MainActivity)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}
