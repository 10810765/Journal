package com.example.marijn.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class EntryDatabase extends SQLiteOpenHelper {

    private static EntryDatabase instance;

    private EntryDatabase(@Nullable Context context) {
        super(context, "Journal", null, 1);
    }

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
        String entries = "CREATE TABLE entries (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, mood TEXT, timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(entries);

        // Test queries
        db.execSQL("INSERT INTO entries (title, content, mood) VALUES (\"First entry\", \"This is the first entry of the Journal\", \"happy\")");
        db.execSQL("INSERT INTO entries (title, content, mood) VALUES (\"Second entry\", \"This is the second entry of the Journal\", \"sad\")");
        db.execSQL("INSERT INTO entries (title, content, mood) VALUES (\"Third entry\", \"This is the third entry of the Journal\", \"neutral\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS entries");

        onCreate(db);
    }

    // Select all the fields available in the database
    public Cursor selectAll() {
        return getWritableDatabase().rawQuery("SELECT * FROM entries", null);
    }

    public void insert(JournalEntry entry) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", entry.getTitle());
        contentValues.put("content", entry.getContent());
        contentValues.put("mood", entry.getMood());

        getWritableDatabase().insert("entries", null, contentValues);
    }


    // Delete a whole row from the database found by id
    // Helper site: https://stackoverflow.com/questions/7510219/deleting-row-in-sqlite-in-android
    public void delete(long id) {

        getWritableDatabase().delete("entries", "_id = ?", new String[]{String.valueOf(id)});
    }
}
