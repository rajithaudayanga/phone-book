package com.example.phonebook.Database;

import android.provider.BaseColumns;

public class ContactDB {
    private ContactDB() {}

    public static class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "contact_table";
        public static final String COLUMN_NAME_CONTACT_NAME = "contact_name";
        public static final String COLUMN_NAME_NUMBER = "contact_number";
    }
}
