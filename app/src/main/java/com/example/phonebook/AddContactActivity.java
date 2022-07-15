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
    TextView contactName, contactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        dbHelper = new DBHelper(getApplicationContext());

        saveContact = findViewById(R.id.save_contact);
        contactName = findViewById(R.id.contact_name);
        contactNumber = findViewById(R.id.contact_number);

        saveContact.setOnClickListener(this::saveContact);
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

        db.insert(ContactDB.ContactEntry.TABLE_NAME, null, values);

        MainActivity.contacts.add(new ContactModel(name, number));
        Toast.makeText(this, "Contact saved successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}