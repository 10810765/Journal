package com.example.marijn.journal;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Marijn Meijering <m.h.j.meijering@uva.nl>
 * 10810765 Universiteit van Amsterdam
 * Minor Programmeren 17/12/2018
 */
public class InputActivity extends AppCompatActivity {

    // Static variable to store the mood
    private static String mood;
    private ImageView[] moodButtons; // List with image view ID's

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // Get the ID's of various image views and put them in a list
        moodButtons = new ImageView[]{
                findViewById(R.id.sad),
                findViewById(R.id.worried),
                findViewById(R.id.neutral),
                findViewById(R.id.happy)
        };

        // If there is no previously selected mood, mood is an empty string
        if (savedInstanceState == null) {
            mood = "";
            return;
        } else if (!savedInstanceState.getString("mood").equals("")) {

            // If the mood is not an empty string, restore the selected mood
            String savedMood = savedInstanceState.getString("mood");
            ImageView moodImage = findViewById(R.id.pictures).findViewWithTag(savedMood);

            // Set the image views
            for (int i = 0, n = moodButtons.length; i < n; i++) {
                if (moodButtons[i].equals(moodImage)) {
                    moodButtons[i].setColorFilter(null);
                } else {
                    greyOutImage(moodButtons[i]);
                }
            }
        }
    }

    // Save the selected mood (empty string if no mood selected)
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mood", mood);
    }

    // Add the entry to the database
    public void addEntry(View view) {

        EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());

        // Get the filled in text from the title and content text fields
        String title = ((EditText) findViewById(R.id.titleEntry)).getText().toString();
        String content = ((EditText) findViewById(R.id.contentEntry)).getText().toString();

        // Check if all text fields have been filled
        if (title.isEmpty() || content.isEmpty() || mood.equals("")) {

            //If not all fields have been filled, show a message
            ((TextView) findViewById(R.id.message)).setText("*Please fill all fields before submitting!");
            return;
        } else {
            // If all field have been filled add the entry to the journal
            db.insert(new JournalEntry(title, content, mood));
            Intent intent = new Intent(InputActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    // Set an on mood clicked listener
    public void onItemClick(View view) {
        // If a mood is clicked, set the image views and save the mood
        for (int i = 0, n = moodButtons.length; i < n; i++) {
            if (moodButtons[i] == findViewById(view.getId())) {
                moodButtons[i].setColorFilter(null);
                mood = String.valueOf(moodButtons[i].getTag());
            } else {
                greyOutImage(moodButtons[i]);
            }
        }
    }

    // Grey out an image view
    // Source: https://stackoverflow.com/questions/38186885/is-there-a-way-i-can-gray-out-an-imagebutton-in-android-without-maintaining-a-se
    private void greyOutImage(ImageView image) {
        final ColorMatrix grayscaleMatrix = new ColorMatrix();
        grayscaleMatrix.setSaturation(0);

        final ColorMatrixColorFilter greyFilter = new ColorMatrixColorFilter(grayscaleMatrix);
        image.setColorFilter(greyFilter);

    }
}
