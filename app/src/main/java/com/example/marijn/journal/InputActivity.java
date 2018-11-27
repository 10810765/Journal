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

    String mood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        mood = "";
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


    public void onItemClick(View view){

        ImageView sadImage = findViewById(getResources().getIdentifier("sad", "id", getPackageName()));
        ImageView worriedImage = findViewById(getResources().getIdentifier("worried", "id", getPackageName()));
        ImageView neutralImage = findViewById(getResources().getIdentifier("neutral", "id", getPackageName()));
        ImageView happyImage = findViewById(getResources().getIdentifier("happy", "id", getPackageName()));

        final ColorMatrix grayscaleMatrix = new ColorMatrix();
        grayscaleMatrix.setSaturation(0);

        final ColorMatrixColorFilter filter = new ColorMatrixColorFilter(grayscaleMatrix);

        switch (view.getId()) {
            case R.id.sad:
                sadImage.setColorFilter(null);
                worriedImage.setColorFilter(filter);
                neutralImage.setColorFilter(filter);
                happyImage.setColorFilter(filter);

                mood = "sad";
                break;
            case R.id.worried:
                sadImage.setColorFilter(filter);
                worriedImage.setColorFilter(null);
                neutralImage.setColorFilter(filter);
                happyImage.setColorFilter(filter);

                mood = "worried";
                break;
            case R.id.neutral:
                sadImage.setColorFilter(filter);
                worriedImage.setColorFilter(filter);
                neutralImage.setColorFilter(null);
                happyImage.setColorFilter(filter);

                mood = "neutral";
                break;
            case R.id.happy:
                sadImage.setColorFilter(filter);
                worriedImage.setColorFilter(filter);
                neutralImage.setColorFilter(filter);
                happyImage.setColorFilter(null);

                mood = "happy";
                break;
        }

    }
}
