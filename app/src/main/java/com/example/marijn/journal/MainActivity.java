package com.example.marijn.journal;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private EntryDatabase db;
    private EntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = EntryDatabase.getInstance(getApplicationContext());

        ListView list = findViewById(R.id.journalView);

        adapter = new EntryAdapter(MainActivity.this, db.selectAll());

        list.setAdapter(adapter);

        list.setOnItemClickListener(new ListItemClickListener());
        list.setOnItemLongClickListener(new ListItemLongClickListener());
    }

    public void addEntry(View view) {
        Intent addEntry = new Intent(MainActivity.this, InputActivity.class);
        startActivity(addEntry);
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Cursor clickedEntry = (Cursor) parent.getItemAtPosition(position);

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);

            Bundle bundle = new Bundle();

            bundle.putString("title", clickedEntry.getString(clickedEntry.getColumnIndex("title")));
            bundle.putString("content", clickedEntry.getString(clickedEntry.getColumnIndex("content")));
            bundle.putString("mood", clickedEntry.getString(clickedEntry.getColumnIndex("mood")));
            bundle.putString("timestamp", clickedEntry.getString(clickedEntry.getColumnIndex("timestamp")));

            intent.putExtras(bundle);

            startActivity(intent);
        }
    }

    private class ListItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor clickedEntry = (Cursor) parent.getItemAtPosition(position);
            long Id = clickedEntry.getInt(clickedEntry.getColumnIndex("_id"));

            db.delete(Id);

            // Update the journals list view
            updateData();

            return true;
        }
    }

    private void updateData() {
        adapter.swapCursor(db.selectAll());
    }
}
