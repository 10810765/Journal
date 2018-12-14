package com.example.marijn.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Marijn Meijering <m.h.j.meijering@uva.nl>
 * 10810765 Universiteit van Amsterdam
 * Minor Programmeren 17/12/2018
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        TextView title = findViewById(R.id.titleText);
        TextView content = findViewById(R.id.contentText);
        TextView timestamp = findViewById(R.id.timestampText);
        TextView mood = findViewById(R.id.moodText);


        title.setText(intent.getStringExtra("title"));
        content.setText(intent.getStringExtra("content"));
        timestamp.setText(intent.getStringExtra("timestamp"));
        mood.setText(intent.getStringExtra("mood"));
    }
}
