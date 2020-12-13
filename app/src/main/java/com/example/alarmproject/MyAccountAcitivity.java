package com.example.alarmproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

import javax.sql.DataSource;

public class MyAccountAcitivity extends AppCompatActivity {
    ImageView userprofile;
    TextView username;
    Button createAlarm;
    Button log_out;
    Button history;
    Button edit;
    FirebaseAuth mFirebaseAuth;
    ProfileDataSource PdataSource;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    final String KEY_DATA = "data";
    final String LOGCAT = "RV";
    final String PREF_FILE = "mainsharedpref";
    final int REQUEST_CODE_IMAGE = 1000;

    SharedPreferences mPreferences;

    private static int SELECT_PICTURE = 1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myuser_sub);

        userprofile = findViewById(R.id.userImage);
        history = findViewById(R.id.history);
        log_out = findViewById(R.id.loggedout);
        createAlarm = findViewById(R.id.createAlarm);
        username = findViewById(R.id.username1);
        edit = findViewById(R.id.profilepic);

        mPreferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        String json = mPreferences.getString(KEY_DATA, "");
        if( !json.isEmpty() ){
            Gson gson = new Gson();
            PdataSource = gson.fromJson(json, ProfileDataSource.class);
        }else{
            ArrayList<Integer> drawableId = new ArrayList<>();
            drawableId.add(R.drawable.user_default);
            PdataSource = ProfileUtils.firstLoadImages(this,
                    drawableId);
        }
        userprofile.setImageBitmap(PdataSource.getImage( PdataSource.getSize() - 1));

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");
        //show user's email
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();


        //create alarm
        createAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MyAccountAcitivity.this, CreateAlarmActivity.class);
                startActivity(newIntent);
            }
        });


        username.setText(mFirebaseUser.getEmail());
        //logout button
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                //logout
                FirebaseAuth.getInstance().signOut();


                Intent toMain = new Intent(MyAccountAcitivity.this, MainActivity.class);
                startActivity(toMain);
            }
        });

        //history button
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttoHistory = new Intent(MyAccountAcitivity.this, HistoryActivity.class);
                startActivity(intenttoHistory);

            }
        });

        //edit profile pic button
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toEdit = new Intent(MyAccountAcitivity.this, EditProfilePicActivity.class);
                startActivityForResult(toEdit, REQUEST_CODE_IMAGE);



            }
        });









    }
    @Override
    protected void onPause(){
        super.onPause();
        //sharedpreferences
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(PdataSource);
        prefsEditor.putString(KEY_DATA,json);
        prefsEditor.apply();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK){
            String name = data.getStringExtra( EditProfilePicActivity.KEY_NAME);
            String path = data.getStringExtra( EditProfilePicActivity.KEY_PATH);
            PdataSource.addData(name, path);
            Bitmap bitmap = PdataSource.getImage( PdataSource.getSize() - 1);
            Log.i(LOGCAT, "USERPROFILE");
            userprofile.setImageBitmap( bitmap);

        }


    }
}
