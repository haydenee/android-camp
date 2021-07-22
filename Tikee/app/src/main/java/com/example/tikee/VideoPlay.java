package com.example.tikee;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class VideoPlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_video_play);
    }
}
