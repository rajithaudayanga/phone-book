package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.phonebook.Adapters.ContactListAdapter;
import com.example.phonebook.Database.ContactDB;
import com.example.phonebook.Helpers.DBHelper;
import com.example.phonebook.Models.ContactModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button addContactNav;
    RecyclerView contactList;
    public static ArrayList<ContactModel> contacts;
    DBHelper dbHelper;
    ContactListAdapter contactListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addContactNav = findViewById(R.id.add_contact_nav);
        contactList = findViewById(R.id.contact_recycle_view);

        contacts = new ArrayList<>();
        dbHelper = new DBHelper(getApplicationContext());

        // setup recycle view
        contactList.setHasFixedSize(true);
        contactList.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        contactList.setLayoutManager(linearLayoutManager);
        contactList.addItemDecoration(new DividerItemDecoration(contactList.getContext(), DividerItemDecoration.VERTICAL));

        getContacts();
        addContactNav.setOnClickListener(this::addContact);
    }

    void addContact(View v) {
        startActivity(new Intent(this, AddContactActivity.class));
    }

    void getContacts() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                ContactDB.ContactEntry._ID,
                ContactDB.ContactEntry.COLUMN_NAME_CONTACT_NAME,
                ContactDB.ContactEntry.COLUMN_NAME_NUMBER
        };

        Cursor cursor = db.query(
                ContactDB.ContactEntry.TABLE_NAME,   // The table to query
                projection,             // The columns to return
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ContactDB.ContactEntry._ID));
            String contactName = cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.ContactEntry.COLUMN_NAME_CONTACT_NAME));
            String contactNumber = cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.ContactEntry.COLUMN_NAME_NUMBER));

            contacts.add(new ContactModel(id, contactName, contactNumber));
        }

        contactListAdapter =
                new ContactListAdapter(this, contacts);
        contactList.setAdapter(contactListAdapter);
        cursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactListAdapter.notifyDataSetChanged();
    }
}