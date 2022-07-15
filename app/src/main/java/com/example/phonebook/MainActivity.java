package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button addContactNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addContactNav = findViewById(R.id.add_contact_nav);
        addContactNav.setOnClickListener(this::addContact);
    }

    void addContact(View v) {
        startActivity(new Intent(this, AddContactActivity.class));
    }
}