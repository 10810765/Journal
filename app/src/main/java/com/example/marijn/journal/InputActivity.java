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

    private String savedMood;
    private static String mood;
    private ImageView[] moodButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        moodButtons = new ImageView[]{
                findViewById(R.id.sad),
                findViewById(R.id.worried),
                findViewById(R.id.neutral),
                findViewById(R.id.happy)
        };

        if (savedInstanceState == null) {
            mood = "";
            return;
        } else if (!savedInstanceState.getString("mood").equals("")) {
            savedMood = savedInstanceState.getString("mood");
            ImageView moodImage = findViewById(R.id.pictures).findViewWithTag(savedMood);

            final ColorMatrix grayscaleMatrix = new ColorMatrix();
            grayscaleMatrix.setSaturation(0);
            final ColorMatrixColorFilter filter = new ColorMatrixColorFilter(grayscaleMatrix);

            for (int i = 0, n = moodButtons.length; i < n; i++) {
                if (moodButtons[i].equals(moodImage)) {
                    moodButtons[i].setColorFilter(null);
                } else {
                    moodButtons[i].setColorFilter(filter);
                }
            }
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mood", mood);
    }

    public void addEntry(View view) {

        EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());

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


    public void onItemClick(View view) {

        final ColorMatrix grayscaleMatrix = new ColorMatrix();
        grayscaleMatrix.setSaturation(0);

        final ColorMatrixColorFilter filter = new ColorMatrixColorFilter(grayscaleMatrix);

        for (int i = 0, n = moodButtons.length; i < n; i++) {
            if (moodButtons[i] == findViewById(view.getId())) {
                moodButtons[i].setColorFilter(null);
                mood = String.valueOf(moodButtons[i].getTag());
            } else {
                moodButtons[i].setColorFilter(filter);
            }
        }
    }
}
