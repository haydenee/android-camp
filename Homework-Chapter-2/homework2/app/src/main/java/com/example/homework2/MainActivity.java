package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("indicate", "MainActivity onCreate");
        initView();
    }
    protected void onStart(){
        super.onStart();
        Log.i("indicate", "MainActivity onStart");
    }
    protected void onResume() {
        super.onResume();
        Log.i("indicate", "MainActivity onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("indicate", "MainActivity onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("indicate", "MainActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("indicate", "MainActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("indicate", "MainActivity onDestroy");
    }


    //Button button1 = findViewById(R.id.button);

    private void initView(){
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                Toast.makeText(this,"This is a toast",Toast.LENGTH_LONG).show();
                break;
            case R.id.button3:
                Intent intent = new Intent(this,PracticeActivity.class);
                startActivity(intent);
                break;
            case R.id.button5:
                Intent implicitIntent = new Intent();
                implicitIntent.setAction(Intent.ACTION_DIAL);
                startActivity(implicitIntent);
                break;
            case R.id.button4:
                Intent intent4 = new Intent(Intent.ACTION_VIEW);
                intent4.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent4);
                break;
        }
    }


}
