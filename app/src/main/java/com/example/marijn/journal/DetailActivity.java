package com.example.marijn.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        TextView title = findViewById(R.id.title);
        TextView content = findViewById(R.id.content);
        TextView timestamp = findViewById(R.id.timestamp);
        TextView mood = findViewById(R.id.mood);


        title.setText(intent.getStringExtra("title"));
        content.setText(intent.getStringExtra("content"));
        timestamp.setText(intent.getStringExtra("timestamp"));
        mood.setText(intent.getStringExtra("mood"));
    }
}
