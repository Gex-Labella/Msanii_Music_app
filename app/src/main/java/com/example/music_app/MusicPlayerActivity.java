package com.example.music_app;

import android.media.MediaPlayer;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import android.content.Context; // Import for Context
import java.util.List; // Import for List
import com.example.music_app.MyMediaPlayer; // Import your MyMediaPlayer class
import com.example.music_app.AudioModel; // Assuming Song is a class in your project

public class MusicPlayerActivity extends AppCompatActivity {

    TextView titleTv, currentTimeTv,totalTimeTv;
    SeekBar seekBar;
    ImageView pausePlay, nextBtn, previousBtn, musicIcon;
    ArrayList<AudioModel> songsLIST;
    AudioModel currentSong;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        titleTv = findViewById(R.id.song_title);
        currentTimeTv = findViewById(R.id.current_time);
        totalTimeTv = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seek_bar);
        pausePlay = findViewById(R.id.play_pause);
        nextBtn = findViewById(R.id.next);
        previousBtn = findViewById(R.id.previous);
        musicIcon = findViewById(R.id.music_icon_big);

        songsLIST = (ArrayList<AudioModel>) getIntent().getSerializableExtra("LIST");
    }

    void setResourcesWithMusic() {
        currentSong = songsLIST.get(MyMediaPlayer.currentIndex);
        titleTv.setText(currentSong.getTitle());
        totalTimeTv.setText(convertTOHHMMSS(currentSong.getDuration()));
    }

    public static String convertTOHHMMSS(String duration) {
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis) % TimeUnit.DAYS.toHours(1),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
        );
    }
}