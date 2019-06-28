package com.example.contactsapp.presentation.contact_info

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.R
import com.example.contactsapp.presentation.model.ContactDataType
import com.example.contactsapp.presentation.model.ContactInfoData
import kotlinx.android.synthetic.main.item_contact_info_data.view.*

class ContactInfoAdapter(
    private val items: List<ContactInfoData>,
    private val onContactInfoDataListener: OnContactInfoDataListener
) : RecyclerView.Adapter<ContactInfoAdapter.ContactInfoHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactInfoHolder {
        return ContactInfoHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_contact_info_data,
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            items[position].type == ContactDataType.PHONE -> 0
            items[position].type == ContactDataType.EMAIL -> 1
            else -> 1
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ContactInfoHolder, position: Int) {
        holder.bind(items[position], onContactInfoDataListener)
    }

    class ContactInfoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var vgContactInfoData: ViewGroup = itemView.vg_contact_info_data
        private var tvContactInfoData: TextView = itemView.tv_contact_info_data
        private var tvContactInfoDataType: TextView = itemView.tv_contact_info_data_type

        @SuppressLint("SetTextI18n")
        fun bind(
            contactInfoData: ContactInfoData,
            onContactInfoDataListener: OnContactInfoDataListener
        ) {
            vgContactInfoData.setOnClickListener {
                when (contactInfoData.type) {
                    ContactDataType.PHONE -> {
                        onContactInfoDataListener.onPhoneClicked(contactInfoData.value)
                    }
                    ContactDataType.EMAIL -> {
                        onContactInfoDataListener.onEmailClicked(contactInfoData.value)
                    }
                }
            }
            tvContactInfoData.text = contactInfoData.value
            tvContactInfoDataType.text =
                if (contactInfoData.type == ContactDataType.PHONE) "Phone" else "Email"

        }
    }
}