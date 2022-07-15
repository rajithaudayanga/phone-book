package com.example.phonebook.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.phonebook.Database.ContactDB;
import com.example.phonebook.Models.ContactModel;

public class DBHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContactDB.ContactEntry.TABLE_NAME + " (" +
                    ContactDB.ContactEntry._ID + " INTEGER PRIMARY KEY," +
                    ContactDB.ContactEntry.COLUMN_NAME_CONTACT_NAME + " TEXT," +
                    ContactDB.ContactEntry.COLUMN_NAME_NUMBER + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContactDB.ContactEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PhoneBook.db";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public ContactModel getContactById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                ContactDB.ContactEntry._ID,
                ContactDB.ContactEntry.COLUMN_NAME_CONTACT_NAME,
                ContactDB.ContactEntry.COLUMN_NAME_NUMBER
        };

        String selection = ContactDB.ContactEntry._ID + " = ?";
        String[] selectionArgs = {id + ""};

        Cursor cursor = db.query(
                ContactDB.ContactEntry.TABLE_NAME,   // The table to query
                projection,             // The columns to return
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        cursor.moveToFirst();

        String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.ContactEntry.COLUMN_NAME_CONTACT_NAME));
        String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactDB.ContactEntry.COLUMN_NAME_NUMBER));
        cursor.close();
        return new ContactModel(id, name, number);
    }
}
