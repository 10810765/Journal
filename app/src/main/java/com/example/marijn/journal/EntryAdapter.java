package com.example.marijn.journal;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
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

        TextView titleText = view.findViewById(R.id.titleView);
        TextView moodText = view.findViewById(R.id.moodView);
        TextView dateText = view.findViewById(R.id.dateView);

        titleText.setText(title);
        moodText.setText(mood);
        dateText.setText(timestamp);
    }
}
