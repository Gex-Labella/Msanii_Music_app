package com.example.music_app;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import java.io.IOException;
import java.util.List;

public class MyMediaPlayer {
    private static MyMediaPlayer instance;
    private MediaPlayer mediaPlayer;
    private List<AudioModel> audioList;
    private AudioManager audioManager;
    private AudioFocusRequest focusRequest;
    private Context context;
    private int currentIndex = -1; // Define currentIndex variable

    private MyMediaPlayer(Context context) {
        this.context = context;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build());
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build();
        focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(audioAttributes)
                .setOnAudioFocusChangeListener(null)
                .build();
    }

    public static MyMediaPlayer getInstance(Context context) {
        if (instance == null) {
            instance = new MyMediaPlayer(context);
        }
        return instance;
    }

    // Getter method for currentIndex
    public int getCurrentIndex() {
        return currentIndex;
    }

    // Setter method for currentIndex
    public void setCurrentIndex(int index) {
        this.currentIndex = index;
    }


    public void play(String url, int position) {
        currentIndex = position; // Update currentIndex
        try {
            if (requestAudioFocus()) {
               // mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                      //  .setUsage(AudioAttributes.USAGE_MEDIA)
                       // .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                       // .build());
                mediaPlayer.reset();
                mediaPlayer.setDataSource(url);  // Pass the url directly
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(mp -> {
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mp2 -> release());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMediaPlayer(Context context, int position) {
        MyMediaPlayer mediaPlayer = MyMediaPlayer.getInstance(context);
        if (audioList != null && position >= 0 && position < audioList.size()) {
            String songUrl = audioList.get(position).getPath(); // Use getPath() from AudioModel
            mediaPlayer.play(songUrl, position);
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            abandonAudioFocus();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            abandonAudioFocus();
        }
    }


    public void setDataSource(String path) throws Exception {
        mediaPlayer.setDataSource(context, Uri.parse(path));
    }

    public void prepare() throws Exception {
        mediaPlayer.prepare();
    }

    public void start() {
        mediaPlayer.start();
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void seekTo(int position) {
        mediaPlayer.seekTo(position);
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            abandonAudioFocus();
        }
        currentIndex = -1; // Reset currentIndex when releasing
    }

    public void reset() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
    }

    private boolean requestAudioFocus() {
        int result = audioManager.requestAudioFocus(focusRequest);
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    private void abandonAudioFocus() {
        audioManager.abandonAudioFocus(null);
    }

    public class Song {
        private String url;

        public Song(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
}