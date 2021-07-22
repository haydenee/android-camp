package com.example.tikee;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.example.tikee.Utils.Constants;
import com.example.tikee.Utils.Upload;
import com.example.tikee.Utils.UploadInfo;
import com.example.tikee.Utils.UploadResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadActivity extends AppCompatActivity {

    EditText editText;
    private String extraText = "";
    ImageView videoPreview;
    ImageView im_send;
    LottieAnimationView animationView;

    Bitmap uploadCover;
    private Bitmap videoThumbnail;
    private String videoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_upload);

        animationView = findViewById(R.id.animation_view);
        editText = findViewById(R.id.editText);
        im_send = findViewById(R.id.im_send);
        im_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //animationView.setVisibility(View.VISIBLE);
                        uploadVideo();
                    }
                }).start();
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        videoPreview = findViewById(R.id.im_preview);
        Bundle extras = getIntent().getExtras();
        videoPath = (String) extras.get("video_path");
        Log.d("info","VideoPath is :" + videoPath);
        File videoFile = new File(videoPath);
        new Thread(new Runnable() {
            @Override
            public void run() {

                setPreview();
            }
        }).start();

    }
    @TargetApi(Build.VERSION_CODES.P)
    private void setPreview(){

        if(ContextCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(UploadActivity.this, new String[]{Manifest
                    .permission.READ_EXTERNAL_STORAGE}, 2);
        }
        ActivityCompat.requestPermissions(this
                , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                , 1025);

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoPath);

//        String frameCount = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT);
//
//        String fc = retriever.extractMetadata(FFmpegMediaMetadataRetriever.);
//        while(fc == null)   fc = retriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_CHAPTER_COUNT);
//        videoThumbnail = retriever.getFrameAtIndex(Integer.valueOf(fc)/2);

        //Log.d("info","VideoFrame is :" + fc);
        animationView.setVisibility(View.VISIBLE);
        String duration_s = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        while (duration_s == null)  duration_s = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        animationView.setVisibility(View.GONE);
        long videoDuration = Long.valueOf(duration_s);
        int centerFrameTime = (int)(videoDuration / 2);
        videoThumbnail =  retriever.getFrameAtTime(centerFrameTime, MediaMetadataRetriever.OPTION_CLOSEST);

        Log.d("info","VideoDuration is :" + duration_s);
//        long videoDuration = Long.valueOf(duration_s);
//        int centerFrameTime = (int)(videoDuration / 2);
//        videoThumbnail =  retriever.getFrameAtTime(centerFrameTime, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        //videoThumbnail = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Video.Thumbnails.MINI_KIND);
        uploadCover = videoThumbnail;
        Bitmap previewThumbnail = resizeImage(videoThumbnail);

        //videoThumbnail = BitmapFactory.decodeFile(RecordingActivity.picPath);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                videoPreview.setImageBitmap(previewThumbnail);
            }
        });

    }
    private void uploadVideo(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Upload upload ;
        upload = retrofit.create(Upload.class);
        extraText = editText.getText().toString();
        UploadInfo info = composeUploadParam(videoPath, uploadCover, extraText);
        try {

            Call<UploadResponse> uploadCall = upload.submitVideo(info.getStudentId(),info.getUserName(),info.getExtraValue(),info.getCoverImage(),info.getVideo(),info.getToken());
            Response<UploadResponse> uploadResponse = uploadCall.execute();
            if(uploadResponse.isSuccessful()) {
                //animationView.setVisibility(View.GONE);
                Log.d("info","上传成功");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UploadActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(UploadActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Log.d("info","上传失败");
            }

    }
        catch(IOException e) {
            e.printStackTrace();
        }

}
    private UploadInfo composeUploadParam(String videoPath, Bitmap coverImage, String extraText){
        UploadInfo info = new UploadInfo();
        info.setToken(Constants.token);
        info.setStudentId(Constants.studentId);
        info.setUserName(Constants.userName);
        info.setExtraValue(extraText);

        try {
            byte []videoByte = convertInputStreamToBytes(new FileInputStream(new File(videoPath)));
            MultipartBody.Part videoPart = MultipartBody.Part.createFormData("video", "upload.mp4",
                    RequestBody.create(MediaType.parse("multipart/form_data"), videoByte));
            info.setVideo(videoPart);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            coverImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte []coverImageByte = baos.toByteArray();
            MultipartBody.Part coverPart = MultipartBody.Part.createFormData("cover_image", "cover.jpg",
                    RequestBody.create(MediaType.parse("multipart/form_data"), coverImageByte));
            info.setCoverImage(coverPart);

        }catch(IOException e){
            e.printStackTrace();
        }
        return info;
    }

    private static byte[] convertInputStreamToBytes(InputStream is) {
        byte []videoBytes = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // FileInputStream fis = new FileInputStream(new File(videoPath));
            byte []buf = new byte[1024];
            int n;
            while( (n = is.read(buf)) != -1) {
                baos.write(buf, 0,  n);
            }
            videoBytes = baos.toByteArray();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return videoBytes;
    }
    private Bitmap resizeImage(Bitmap bm) {
        int height = bm.getHeight();
        int width = bm.getWidth();
        int dim_height = videoPreview.getHeight();
        int dim_width = videoPreview.getWidth();

        float scaleHeight = ((float) dim_height) / height;
        float scaleWidth =  ((float) dim_width) / width;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }


}

