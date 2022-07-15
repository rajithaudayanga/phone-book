package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonebook.Database.ContactDB;
import com.example.phonebook.Helpers.DBHelper;
import com.example.phonebook.Models.ContactModel;

public class AddContactActivity extends AppCompatActivity {

    Button saveContact;
    DBHelper dbHelper;
    TextView contactName, contactNumber, addContactTitle;
    long contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        dbHelper = new DBHelper(getApplicationContext());

        saveContact = findViewById(R.id.save_contact);
        contactName = findViewById(R.id.contact_name);
        contactNumber = findViewById(R.id.contact_number);
        addContactTitle = findViewById(R.id.add_contact_title);

        contactId = getIntent().getExtras().getLong("contactId");
        if (contactId > 0) {
            addContactTitle.setText("Update Contact");
            ContactModel contact = dbHelper.getContactById(contactId);
            contactName.setText(contact.getContactName());
            contactNumber.setText(contact.getContactNumber());
        }

        saveContact.setOnClickListener(v -> {
            if (contactId > 0) {
                updateContact();
            } else {
                saveContact(v);
            }
        });
    }

    void saveContact(View v) {

        String name = contactName.getText().toString().trim();
        String number = contactNumber.getText().toString().trim();

        if (name.isEmpty() || number.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContactDB.ContactEntry.COLUMN_NAME_CONTACT_NAME, name);
        values.put(ContactDB.ContactEntry.COLUMN_NAME_NUMBER, number);

        long id = db.insert(ContactDB.ContactEntry.TABLE_NAME, null, values);

        MainActivity.contacts.add(new ContactModel(id, name, number));
        Toast.makeText(this, "Contact saved successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void updateContact() {

        String name = contactName.getText().toString().trim();
        String number = contactNumber.getText().toString().trim();

        if (name.isEmpty() || number.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = ContactDB.ContactEntry._ID + " = ?";
        String[] selectionArgs = {contactId + ""};
        ContentValues values = new ContentValues();
        values.put(ContactDB.ContactEntry.COLUMN_NAME_CONTACT_NAME, name);
        values.put(ContactDB.ContactEntry.COLUMN_NAME_NUMBER, number);
        db.update(ContactDB.ContactEntry.TABLE_NAME, values, selection, selectionArgs);

        for (ContactModel contact : MainActivity.contacts) {
            if (contact.getId() == contactId) {
                contact.setContactName(name);
                contact.setContactNumber(number);
                break;
            }
        }
        finish();
    }
}