package com.example.music_app;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.content.Intent;

import android.os.Bundle;

public class DisplayLyricsActivity extends AppCompatActivity {

    private TextView lyricsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lyrics); // Create a layout file for displaying lyrics

        lyricsTextView = findViewById(R.id.lyricsTextView); // Reference to the TextView in your layout file

        // Retrieve lyrics for the currently playing song from a source (e.g., API, database)
        String lyrics = "Insert lyrics for the song here";

        // Set the lyrics text in the TextView
        lyricsTextView.setText(lyrics);
    }
}