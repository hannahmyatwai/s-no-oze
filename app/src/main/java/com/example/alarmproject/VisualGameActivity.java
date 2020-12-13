package com.example.alarmproject;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class VisualGameActivity extends AppCompatActivity {
    ImageButton oddImage;
    ImageButton oddImage1;
    ImageButton oddImage2;

    AlarmManager alarm_manager;
    Date d = new Date();
    Random random = new Random(d.getTime());
    public final String TAG = "Logcat";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualgame_layout);

        ArrayList<Integer> arrayOfImageButtonID = new ArrayList<>();
        arrayOfImageButtonID.add(R.id.imageButton1);
        arrayOfImageButtonID.add(R.id.imageButton2);
        arrayOfImageButtonID.add(R.id.imageButton3);
        arrayOfImageButtonID.add(R.id.imageButton4);
        arrayOfImageButtonID.add(R.id.imageButton5);
        arrayOfImageButtonID.add(R.id.imageButton6);
        arrayOfImageButtonID.add(R.id.imageButton7);
        arrayOfImageButtonID.add(R.id.imageButton8);
        arrayOfImageButtonID.add(R.id.imageButton9);
        arrayOfImageButtonID.add(R.id.imageButton10);
        arrayOfImageButtonID.add(R.id.imageButton11);
        arrayOfImageButtonID.add(R.id.imageButton12);
        arrayOfImageButtonID.add(R.id.imageButton13);
        arrayOfImageButtonID.add(R.id.imageButton14);
        arrayOfImageButtonID.add(R.id.imageButton15);
        arrayOfImageButtonID.add(R.id.imageButton16);
        arrayOfImageButtonID.add(R.id.imageButton17);
        arrayOfImageButtonID.add(R.id.imageButton18);
        arrayOfImageButtonID.add(R.id.imageButton19);
        arrayOfImageButtonID.add(R.id.imageButton20);
        arrayOfImageButtonID.add(R.id.imageButton21);
        arrayOfImageButtonID.add(R.id.imageButton22);
        arrayOfImageButtonID.add(R.id.imageButton23);
        arrayOfImageButtonID.add(R.id.imageButton24);

        int i = random.nextInt(arrayOfImageButtonID.size());

        oddImage = findViewById(arrayOfImageButtonID.get(i));
        Log.i(TAG, oddImage.toString());


        int j = random.nextInt(arrayOfImageButtonID.size());
        oddImage1 = findViewById(arrayOfImageButtonID.get(j));
        oddImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VisualGameActivity.this, "Congrats! You chose the correct button! Please press Alarm Off", Toast.LENGTH_LONG).show();

                int data = 0;
                Intent intentNew = new Intent(VisualGameActivity.this, CreateAlarmActivity.class);
                intentNew.putExtra("data", 1);
                startActivityForResult(intentNew, 1);





            }
        });
    }





}

