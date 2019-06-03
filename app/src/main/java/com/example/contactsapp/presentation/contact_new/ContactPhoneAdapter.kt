package com.example.contactsapp.presentation.contact_new

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.R
import com.example.contactsapp.presentation.model.ContactDataType
import com.example.contactsapp.util.afterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.item_phone_number.view.*

class ContactPhoneAdapter(
    private val items: List<String>,
    private val onContactDataListener: OnContactDataListener
) : RecyclerView.Adapter<ContactPhoneAdapter.PhoneNumberViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhoneNumberViewHolder {
        return PhoneNumberViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_phone_number,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PhoneNumberViewHolder, position: Int) {
        holder.bind(items[position], onContactDataListener)

    }


    class PhoneNumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tilPhoneNumber: TextInputLayout = itemView.til_contact_phone_number
        private var etPhoneNumber: TextInputEditText = itemView.et_contact_phone_number

        @SuppressLint("SetTextI18n")
        fun bind(phone: String, onContactDataListener: OnContactDataListener) {
            etPhoneNumber.setText(phone)
            etPhoneNumber.afterTextChanged {
                if (it.length > 1) {
                    onContactDataListener.onNewContactDataOption(
                        ContactDataType.PHONE,
                        adapterPosition, it
                    )
                }
            }
        }

        fun showError(error: String) {
            tilPhoneNumber.isErrorEnabled = true
            tilPhoneNumber.error = error
        }
    }


}