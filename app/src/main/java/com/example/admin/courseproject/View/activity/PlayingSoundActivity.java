package com.example.admin.courseproject.View.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.admin.courseproject.R;

import java.io.IOException;

public class PlayingSoundActivity extends Activity {

    Button btn_playMP3;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_sound);
        btn_playMP3 = (Button) findViewById(R.id.btn_playMP3);

        btn_playMP3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(PlayingSoundActivity.this, R.raw.sample1);

                mediaPlayer.start();
            }
        });

    }

    public void playMedia(Uri file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(file);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
