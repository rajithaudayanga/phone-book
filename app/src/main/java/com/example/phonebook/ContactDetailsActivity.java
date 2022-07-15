package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonebook.Adapters.ContactListAdapter;
import com.example.phonebook.Database.ContactDB;
import com.example.phonebook.Helpers.DBHelper;
import com.example.phonebook.Models.ContactModel;

public class ContactDetailsActivity extends AppCompatActivity {

    TextView contactName, contactNumber;
    DBHelper dbHelper;
    Button btnDelete, btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        contactName = findViewById(R.id.contact_details_name);
        contactNumber = findViewById(R.id.contact_details_number);
        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);

        dbHelper = new DBHelper(getApplicationContext());

        long contactId = getIntent().getExtras().getLong("contactId");
        if (contactId < 0) {
            Toast.makeText(this, "Contact saved successfully", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ContactModel contact = dbHelper.getContactById(contactId);
        contactName.setText(contact.getContactName());
        contactNumber.setText(contact.getContactNumber());

        btnDelete.setOnClickListener(v -> {
            deleteContact(contactId);
            finish();
        });

        btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(ContactDetailsActivity.this, AddContactActivity.class);
            intent.putExtra("contactId", contactId);
            finish();
            startActivity(intent);
        });
    }

    public void deleteContact(long contactId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = ContactDB.ContactEntry._ID + " = ?";
        String[] selectionArgs = {contactId + ""};
        db.delete(ContactDB.ContactEntry.TABLE_NAME, selection, selectionArgs);
        Toast.makeText(this, "Contact deleted successfully", Toast.LENGTH_SHORT).show();

        for (int i = 0; i < MainActivity.contacts.size(); i++) {
            if (MainActivity.contacts.get(i).getId() == contactId) {
                MainActivity.contacts.remove(i);
                break;
            }
        }
    }
}