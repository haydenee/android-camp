package com.example.tikee;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;

public class RecordingActivity extends AppCompatActivity {

    private int CAMERA_AUDIO_PERMISSION = 1001;
    private int WRITE_PERMISSION = 1025;

    private String videoPath = "";//directory to video
    static String picPath = "";
    private boolean isRecording = false;
    private boolean usingFrontCamera = false;
    private long startTime;
    ImageView im_return, im_record;
    TextView tv_countdown;
    private ProcessCameraProvider cameraProvider;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Preview preview;
    private CameraSelector cameraSelector;
    private VideoCapture videoCapture;
    private ImageCapture imageCapture;
    private PreviewView previewView;
    Bitmap lastFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recording);

        previewView = findViewById(R.id.vp);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        im_return = findViewById(R.id.im_cancel);
        im_record = findViewById(R.id.im_record);
        tv_countdown = findViewById(R.id.tv_countdown);
        /*初始化按钮*/
        initReturnButton();//后退按钮
        initRecordButton();//录制按钮

        /*初始化权限*/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO},
                    CAMERA_AUDIO_PERMISSION);
            ActivityCompat.requestPermissions(this
                    , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    , WRITE_PERMISSION);
        }
        /*开始预览*/
        startCameraPreview();


    }


    private void initReturnButton() {
        im_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecordButton() {
        im_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording == false) {
                    isRecording = true;
                    tv_countdown.setVisibility(View.VISIBLE);
                    im_record.setImageResource(R.drawable.ic_shutter_button_rec);
                    startTime = System.currentTimeMillis();
                    startRecording();
                    handler.post(runnable);

                } else {
                    stopRecording();

                }
            }
        });
    }

    private void startCameraPreview() {
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                preview = new Preview.Builder().build();
                cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());
                videoCapture = new VideoCapture.Builder().build();
                cameraProvider.bindToLifecycle(this, cameraSelector, videoCapture, preview);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @SuppressLint("RestrictedApi")
    private void startRecording() {

        videoPath = setOutputPath();
        File videoFile = new File(videoPath);
        VideoCapture.OutputFileOptions outputFileOptions = new VideoCapture.OutputFileOptions.Builder(videoFile).build();

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO},
                    CAMERA_AUDIO_PERMISSION);
        }//权限检查

        videoCapture.startRecording(outputFileOptions, Executors.newSingleThreadExecutor(),
                new VideoCapture.OnVideoSavedCallback() {
                    @Override
                    public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }

                    @Override
                    public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {

                    }
                });
    }
    @SuppressLint("RestrictedApi")
    private void stopRecording(){
        videoCapture.stopRecording();
        tv_countdown.setVisibility(View.INVISIBLE);
        im_record.setImageResource(R.drawable.ic_shuter_button);
        isRecording = false;
        cameraProvider.shutdown();

        Intent intent = new Intent(RecordingActivity.this, UploadActivity.class);
        intent.putExtra("video_path", videoPath);
        startActivity(intent);

        
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long currTime = System.currentTimeMillis();
            long elapsedTime = currTime - startTime;
            if(elapsedTime <= 10000){
                handler.postDelayed(this,10);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_countdown.setText(String.format("00:%02.0f",((float)(10000-elapsedTime))/1000));
                    }
                });
            }
            else    stopRecording();

        }
    };

    private String setOutputPath() {
        File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir, "Tikee_Vid_" + timeStamp + ".mp4");
        if (!mediaFile.exists()) {
            mediaFile.getParentFile().mkdirs();
        }
        return mediaFile.getAbsolutePath();
    }

}
