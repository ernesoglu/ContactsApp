package com.example.contactsapp.presentation.contacts_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.R
import com.example.contactsapp.presentation.model.ContactPresentationModel
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactsListAdapter(
    private val items: List<ContactPresentationModel>,
    private val clickListener: OnContactClickListener
) : RecyclerView.Adapter<ContactsListAdapter.ContactsViewHolder>() {

    private val LAYOUT_RES = R.layout.item_contact

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsViewHolder {
        return ContactsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                LAYOUT_RES,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }

    class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var contactName: TextView = itemView.tv_contact_name

        @SuppressLint("SetTextI18n")
        fun bind(model: ContactPresentationModel, clickListener: OnContactClickListener) {
            itemView.setOnClickListener {
                clickListener.onContactClicked(adapterPosition)
            }

            contactName.text = model.firstName + " " + model.lastName
        }
    }

    interface OnContactClickListener {
        fun onContactClicked(position: Int)
    }
}