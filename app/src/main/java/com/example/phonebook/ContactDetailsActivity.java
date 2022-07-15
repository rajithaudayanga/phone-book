package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonebook.Adapters.ContactListAdapter;
import com.example.phonebook.Database.ContactDB;
import com.example.phonebook.Helpers.DBHelper;
import com.example.phonebook.Models.ContactModel;

public class ContactDetailsActivity extends AppCompatActivity {

    TextView contactName, contactNumber;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        contactName = findViewById(R.id.contact_details_name);
        contactNumber = findViewById(R.id.contact_details_number);

        dbHelper = new DBHelper(getApplicationContext());

        long contactId = getIntent().getExtras().getLong("contactId");
        if (contactId < 0) {
            Toast.makeText(this, "Contact saved successfully", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        ContactModel contact = this.getContactById(contactId);
        contactName.setText(contact.getContactName());
        contactNumber.setText(contact.getContactNumber());

    }

    private ContactModel getContactById(long contactId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                ContactDB.ContactEntry._ID,
                ContactDB.ContactEntry.COLUMN_NAME_CONTACT_NAME,
                ContactDB.ContactEntry.COLUMN_NAME_NUMBER
        };

        String selection = ContactDB.ContactEntry._ID + " = ?";
        String[] selectionArgs = {contactId + ""};

        Cursor cursor = db.query(
                ContactDB.ContactEntry.TABLE_NAME,   // The table to query
                projection,             // The columns to return
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        long id;
        String name, number;
        cursor.moveToFirst();

        id = cursor.getInt(cursor.getColumnIndexOrThrow(ContactDB.ContactEntry._ID));
        name = cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.ContactEntry.COLUMN_NAME_CONTACT_NAME));
        number = cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.ContactEntry.COLUMN_NAME_NUMBER));
        cursor.close();
        return new ContactModel(id, name, number);
    }
}