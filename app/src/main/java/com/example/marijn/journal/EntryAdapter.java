package com.example.marijn.journal;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class EntryAdapter extends ResourceCursorAdapter {
    public EntryAdapter(Context context, Cursor cursor) {
        super(context, R.layout.entry_row, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String title = cursor.getString(cursor.getColumnIndex("title"));
        String mood = cursor.getString(cursor.getColumnIndex("mood"));
        String timestamp = cursor.getString(cursor.getColumnIndex("timestamp"));

        ImageView image = view.findViewById(R.id.moodImage);

        int moodID = context.getResources().getIdentifier(mood, "drawable", context.getPackageName());

        image.setImageResource(moodID);

        TextView titleText = view.findViewById(R.id.titleText);
        TextView moodText = view.findViewById(R.id.moodText);
        TextView dateText = view.findViewById(R.id.dateText);

        titleText.setText(title);
        moodText.setText(mood);
        dateText.setText(timestamp);
    }
}
