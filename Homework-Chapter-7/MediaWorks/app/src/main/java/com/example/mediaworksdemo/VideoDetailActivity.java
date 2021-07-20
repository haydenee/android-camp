package com.example.mediaworksdemo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VideoDetailActivity extends AppCompatActivity {

    String mockUrl = "https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218114723HDu3hhxqIT.mp4";
    private SeekBar seekbar;
    private TextView tvTime;
    private TextView tvCurrentPos;
    private VideoView videoView;
    private int current;
    private boolean isChanging = false;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            if(videoView.isPlaying()&&!isChanging){
                current = videoView.getCurrentPosition();
                seekbar.setProgress(current);
                tvCurrentPos.setText(time(videoView.getCurrentPosition()));
            }
            handler.postDelayed(runnable,100);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        seekbar = findViewById(R.id.seekBar);
        tvTime = findViewById(R.id.tvTotal);
        tvCurrentPos = findViewById(R.id.tvCurrentPos);
        videoView = findViewById(R.id.vv_detail);

        videoView.setVideoURI(Uri.parse(mockUrl));
        //videoView.setMediaController(new MediaController(this));



        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (videoView.isPlaying()){
                    videoView.pause();
                }else {
                    videoView.start();
                }
                return false;
            }
        });
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                tvTime.setText(time(videoView.getDuration()));
                seekbar.setMax(videoView.getDuration());
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isChanging = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int prog = seekBar.getProgress();
                videoView.seekTo(prog);
                isChanging = false;
            }
        });
        videoView.start();
        handler.post(runnable);


    }

    protected String time(long millionSeconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millionSeconds);
        return simpleDateFormat.format(c.getTime());
    }
}

