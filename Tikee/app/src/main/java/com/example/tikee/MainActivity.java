package com.example.tikee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tikee.Fragment.ListFragment;

public class MainActivity extends AppCompatActivity {

    private int nowFrag =-1;
    ImageView im_camera,im_video,im_cate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        im_camera = findViewById(R.id.im_camera);

        im_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordingActivity.class);
                startActivity(intent);
            }
        });

        im_video = findViewById(R.id.im_video);

        im_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                im_video.setImageResource(R.drawable.ic_video_chosen);
                im_cate.setImageResource(R.drawable.ic_category);
                updateFragment(ListFragment.newInstance("a"),1);
            }
        });

        im_cate = findViewById(R.id.im_cate);

        im_cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                im_video.setImageResource(R.drawable.ic_video);
                im_cate.setImageResource(R.drawable.ic_category_chosen);
                updateFragment(ListFragment.newInstance("a"),0);
            }
        });

        im_video.setImageResource(R.drawable.ic_video);
        im_cate.setImageResource(R.drawable.ic_category_chosen);
        updateFragment(ListFragment.newInstance("a"),0);

    }

    private void updateFragment(Fragment fragment, int fragIndex){
        if(fragIndex == nowFrag)    return;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();
        nowFrag = fragIndex;
    }
}
