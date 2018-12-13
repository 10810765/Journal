package com.example.marijn.journal;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class InputActivity extends AppCompatActivity {

    private static final String SAVED_TEXT = "text_input";

    private String mood;
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
        } else {
            mood = savedInstanceState.getString("mood");
            ImageView moodImage = findViewById(R.id.pictures).findViewWithTag(mood);

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

            EditText title = findViewById(R.id.titleEntry);
            EditText content = findViewById(R.id.contentEntry);
            title.setText(savedInstanceState.getString(SAVED_TEXT));
            content.setText(savedInstanceState.getString(SAVED_TEXT));
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState); // always call super
        outState.putString("mood", mood);
        String title = ((EditText) findViewById(R.id.titleEntry)).getText().toString();
        String content = ((EditText) findViewById(R.id.contentEntry)).getText().toString();
        outState.putString(SAVED_TEXT, title);
        outState.putString(SAVED_TEXT, content);
    }

    public void addEntry(View view) {

        EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());

        String title = ((EditText) findViewById(R.id.titleEntry)).getText().toString();
        String content = ((EditText) findViewById(R.id.contentEntry)).getText().toString();

        // Check if all text fields have been filled
        if (title.equals("") || content.equals("") || mood.equals("")) {

            //Type a title, message and select a mood before submitting your entry!
            ((TextView) findViewById(R.id.message)).setText("*Please fill all fields before submitting!");
            return;

        } else {

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
            if (moodButtons[i].equals(view.getId())) {
                moodButtons[i].setColorFilter(null);
                mood = String.valueOf(moodButtons[i].getTag());
            } else {
                moodButtons[i].setColorFilter(filter);
            }
        }
    }
}
