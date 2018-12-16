package com.example.marijn.journal;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Marijn Meijering <m.h.j.meijering@uva.nl>
 * 10810765 Universiteit van Amsterdam
 * Minor Programmeren 17/12/2018
 */
public class MainActivity extends AppCompatActivity {

    private EntryDatabase db; // Variable used to hold the database
    private EntryAdapter adapter; // Variable used to hold the adapter
    private ListView list; // Variable used to hold the ListView id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the entry database
        db = EntryDatabase.getInstance(getApplicationContext());

        // Instantiate the adapter for the journal entries
        adapter = new EntryAdapter(MainActivity.this, db.selectAll());

        // Get the ListView ID
        list = findViewById(R.id.journalView);

        // Attach the adapter to the list view
        list.setAdapter(adapter);

        // Instantiate an on list item click and long click listener
        list.setOnItemClickListener(new ListItemClickListener());
        list.setOnItemLongClickListener(new ListItemLongClickListener());
    }

    @Override // After a pause or restart
    public void onResume() {
        super.onResume();
        // Refresh the list view
        EntryAdapter updateAdapter = new EntryAdapter(MainActivity.this, db.selectAll());
        list.setAdapter(updateAdapter);
    }

    // On add entry button click, go to next activity
    public void addEntry(View view) {
        Intent addEntry = new Intent(MainActivity.this, InputActivity.class);
        startActivity(addEntry);
    }

    // Create an on journal entry clicked listener
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Get the cursor object of the clicked journal entry
            Cursor clickedEntry = (Cursor) parent.getItemAtPosition(position);

            // Create a new bundle
            Bundle bundle = new Bundle();

            // Put entry information into the bundle
            bundle.putString("title", clickedEntry.getString(clickedEntry.getColumnIndex("title")));
            bundle.putString("content", clickedEntry.getString(clickedEntry.getColumnIndex("content")));
            bundle.putString("mood", clickedEntry.getString(clickedEntry.getColumnIndex("mood")));
            bundle.putString("timestamp", clickedEntry.getString(clickedEntry.getColumnIndex("timestamp")));

            // Pass the Bundle to the next activity
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    // Create an on journal entry long click listener
    private class ListItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            // Get the cursor object of the clicked journal entry
            Cursor clickedEntry = (Cursor) parent.getItemAtPosition(position);

            // Use the cursor object to retrieve the id of the clicked journal entry
            long Id = clickedEntry.getInt(clickedEntry.getColumnIndex("_id"));

            // Delete the journal entry
            db.delete(Id);

            // Update the journals list view
            updateData();

            return true;
        }
    }

    // Update the list view
    private void updateData() {
        adapter.swapCursor(db.selectAll());
    }
}
