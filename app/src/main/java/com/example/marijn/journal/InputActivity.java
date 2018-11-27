package com.example.marijn.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
    }

    public void addEntry(View view) {

            EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());

            String title = ((EditText) findViewById(R.id.titleEntry)).getText().toString();
            String content = ((EditText) findViewById(R.id.contentEntry)).getText().toString();
            String mood = ((EditText) findViewById(R.id.moodEntry)).getText().toString();

            db.insert(new JournalEntry(title, content, mood));

            Intent intent = new Intent(InputActivity.this, MainActivity.class);

            startActivity(intent);
    }
}
