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
import kotlinx.android.synthetic.main.item_email.view.*

class ContactEmailAdapter(
    private val items: List<String>,
    private val onContactDataListener: OnContactDataListener
) : RecyclerView.Adapter<ContactEmailAdapter.EmailViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmailViewHolder {
        return EmailViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_email,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(items[position], onContactDataListener)
    }

    class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tilEmail: TextInputLayout = itemView.til_contact_email
        private var etEmail: TextInputEditText = itemView.et_contact_email

        @SuppressLint("SetTextI18n")
        fun bind(email: String, onContactDataListener: OnContactDataListener) {
            etEmail.setText(email)
            etEmail.afterTextChanged {
                if (it.length > 1) {
                    onContactDataListener.onNewContactDataOption(
                        ContactDataType.EMAIL,
                        adapterPosition, it
                    )
                }
            }
        }
    }
}