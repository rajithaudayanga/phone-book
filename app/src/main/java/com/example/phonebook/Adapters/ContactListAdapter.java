package com.example.phonebook.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonebook.Models.ContactModel;
import com.example.phonebook.R;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<ContactModel> contacts;

    public ContactListAdapter(Context context, ArrayList<ContactModel> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        LayoutInflater mLayoutInflater = LayoutInflater.from(context);
        view = mLayoutInflater.inflate(R.layout.contact_item, parent, false);

        return new ContactListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactModel contact = contacts.get(position);
        holder.name.setText(contact.getContactName());
        holder.phone.setText(contact.getContactNumber());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, phone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.contact_item_name);
            phone = itemView.findViewById(R.id.contact_item_number);
        }
    }
}
