package com.example.marijn.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * Marijn Meijering <m.h.j.meijering@uva.nl>
 * 10810765 Universiteit van Amsterdam
 * Minor Programmeren 17/12/2018
 */
public class EntryDatabase extends SQLiteOpenHelper {

    // Store unique instance of the class once created
    private static EntryDatabase instance;

    private EntryDatabase(@Nullable Context context) {
        super(context, "Journal", null, 1);
    }

    // Return value of instance if available, otherwise call the EntryDatabase constructor
    public static EntryDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;
        } else {
            instance = new EntryDatabase(context);
            return instance;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create journal entries table
        String entries = "CREATE TABLE entries (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, mood TEXT, timestamp DATETIME DEFAULT (datetime('now','localtime')))";
        db.execSQL(entries);

        // Test queries
        db.execSQL("INSERT INTO entries (title, content, mood) VALUES (\"First entry\", \"This is the first entry of the Journal\", \"happy\")");
        db.execSQL("INSERT INTO entries (title, content, mood) VALUES (\"Second entry\", \"This is the second entry of the Journal\", \"sad\")");
        db.execSQL("INSERT INTO entries (title, content, mood) VALUES (\"Third entry\", \"This is the third entry of the Journal\", \"neutral\")");
    }

    @Override // Drop the entries table and recreate it
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS entries");
        onCreate(db);
    }

    // Select all the fields available in the database
    public Cursor selectAll() {
        return getWritableDatabase().rawQuery("SELECT * FROM entries", null);
    }

    // Method used to insert information into the database
    public void insert(JournalEntry entry) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", entry.getTitle());
        contentValues.put("content", entry.getContent());
        contentValues.put("mood", entry.getMood());

        // Open up a connection and insert the values
        getWritableDatabase().insert("entries", null, contentValues);
    }

    // Delete a database entry by id
    // Helper site: https://stackoverflow.com/questions/7510219/
    public void delete(long id) {
        getWritableDatabase().delete("entries", "_id = ?", new String[]{String.valueOf(id)});
    }
}
