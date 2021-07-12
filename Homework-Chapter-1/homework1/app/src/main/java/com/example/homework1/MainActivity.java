package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public static float focus,distance,apeture,sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Button btn1 = (Button) findViewById(R.id.btn1);
            final TextView sb1_tv2 = findViewById(R.id.sb1_tv2);
            SeekBar sb1 = (SeekBar) findViewById(R.id.sb1);
            final TextView et1_tv2 = findViewById(R.id.et1_tv2);
            final TextView et2_tv2 = findViewById(R.id.et2_tv2);
            EditText et1 = (EditText) findViewById(R.id.et1);
            EditText et2 = (EditText) findViewById(R.id.et2);
            final TextView tv3 = findViewById(R.id.tv3);
            RadioGroup rgrp = findViewById(R.id.rgrp1);
            final RadioButton rbtn1 = findViewById(R.id.rbtn1);
            final RadioButton rbtn2 = findViewById(R.id.rbtn2);
            rgrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    Log.d("test","Selected" + checkedId);
                    if(checkedId == rbtn1.getId())  sensor = 1;
                    else sensor = 0;
                }
            });
            et1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    et1_tv2.setText("F" + s);
                    apeture = Float.parseFloat(s.toString());
                }
            });
            et2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    et2_tv2.setText(s + "m");
                    distance = Float.parseFloat(s.toString());
                }
            });
            sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    sb1_tv2.setText(progress + "mm");
                    focus = progress;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv3.setText(Float.toString((2*focus*focus*distance*distance*apeture)/(30*(focus*focus*focus*focus-apeture*apeture*distance*distance/(30*30)))));
                    Log.d("test","OK");
                }
            });

    }


}
