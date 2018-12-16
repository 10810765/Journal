package com.example.marijn.journal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Marijn Meijering <m.h.j.meijering@uva.nl>
 * 10810765 Universiteit van Amsterdam
 * Minor Programmeren 17/12/2018
 */
public class DetailActivity extends AppCompatActivity {

    private ToggleButton favouriteButton;
    private String titleText, timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get the ID's of various TextViews, an ImageView and a ToggleButton
        TextView title = findViewById(R.id.titleText);
        TextView content = findViewById(R.id.contentText);
        TextView timestamp = findViewById(R.id.timestampText);
        TextView mood = findViewById(R.id.moodText);
        favouriteButton = findViewById(R.id.favouriteButton);

        // Retrieve the entry information from the previous activity
        Intent intent = getIntent();

        titleText = intent.getStringExtra("title");
        timeText = intent.getStringExtra("timestamp");

        // Set the title, content, date and mood
        title.setText(titleText);
        content.setText(intent.getStringExtra("content"));
        timestamp.setText(timeText);
        mood.setText(intent.getStringExtra("mood"));

        // Set an on favourite change listener
        favouriteButton.setOnCheckedChangeListener(new ProfileFavourite());

        // Get a previously stored favourite boolean (title+time for a unique combination)
        SharedPreferences prefsFav = getSharedPreferences("favourite", MODE_PRIVATE);
        Boolean isFav = prefsFav.getBoolean((titleText + timeText).toLowerCase(), false);

        // Set the previously stored favourite state
        if (isFav) { // If this person was a favourite
            favouriteButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.yellow_star));

        } else { // If this person was NOT a favourite
            favouriteButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.grey_star));
        }
    }

    // Create an on favourite click listener
    // With help from: https://stackoverflow.com/questions/34980309/
    private class ProfileFavourite implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isFavourite) {
            if (isFavourite) { // If favourited
                favouriteButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.yellow_star));
            } else { // If unfavourited
                favouriteButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.grey_star));
            }

            // Edit the old favourite Boolean and store the new value
            SharedPreferences.Editor editor = getSharedPreferences("favourite", MODE_PRIVATE).edit();
            editor.putBoolean((titleText + timeText).toLowerCase(), isFavourite);
            editor.apply();
        }
    }
}