package com.example.marijn.journal;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Marijn Meijering <m.h.j.meijering@uva.nl>
 * 10810765 Universiteit van Amsterdam
 * Minor Programmeren 17/12/2018
 */
public class EntryAdapter extends ResourceCursorAdapter {
    public EntryAdapter(Context context, Cursor cursor) {
        super(context, R.layout.entry_row, cursor);
    }

    @Override // Takes a View and fills the right elements with data from the cursor
    public void bindView(View view, Context context, Cursor cursor) {

        // Get the profile name (TextView) and picture (ImageView) id
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String mood = cursor.getString(cursor.getColumnIndex("mood"));
        String timestamp = cursor.getString(cursor.getColumnIndex("timestamp"));
        int moodID = context.getResources().getIdentifier(mood, "drawable", context.getPackageName());

        // Get the ID's of various TextView and an ImageView
        TextView titleText = view.findViewById(R.id.titleText);
        TextView moodText = view.findViewById(R.id.moodText);
        TextView dateText = view.findViewById(R.id.dateText);
        ImageView image = view.findViewById(R.id.moodImage);

        // Set the title, date, mood string and mood image
        image.setImageResource(moodID);
        titleText.setText(title);
        moodText.setText(mood);
        dateText.setText(timestamp);
    }
}
