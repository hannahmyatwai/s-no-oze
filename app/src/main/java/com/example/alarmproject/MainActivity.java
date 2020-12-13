package com.example.alarmproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.example.alarmproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonCreateAlarm;
    Button buttonUserPage;
    Button buttonLeaderboard;
    TextView textViewWelcome;
    TextView textviewloggedin;
    public final String TAG = "Logcat";
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");


        textViewWelcome = findViewById(R.id.textViewWelcome);
        buttonCreateAlarm = findViewById(R.id.buttonCreateAlarm);
        buttonUserPage = findViewById(R.id.buttonAlarmsPage);
        buttonLeaderboard = findViewById(R.id.buttonLeaderboard);
        textviewloggedin = findViewById(R.id.loggedin_status);
        mFirebaseAuth = FirebaseAuth.getInstance();
        Intent intentSubToMain = getIntent();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null){
            //there is a user logged in
            textviewloggedin.setText(mFirebaseUser.getEmail());
        }else{
            //no one logged in
            textviewloggedin.setText("You've not logged in!");

        }


        buttonCreateAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAlarmActivity.class);
                startActivity(intent);
            }
        });
        buttonUserPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFirebaseUser != null){
                    //there is a user logged in
                    Intent intent = new Intent(MainActivity.this, MyAccountAcitivity.class);
                    startActivity(intent);
                }else{
                    //no one logged in
                    Intent intent1 = new Intent(MainActivity.this, AccountActivity.class);
                    startActivity(intent1);

                }

            }
        });
        buttonLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/html");
                emailIntent.putExtra(Intent.EXTRA_EMAIL,  new String[]{"theintmyat_wai@mymail.sutd.edu.sg"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry/Feedback");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "This app made me punctual to my classes! Really love No snooze feature! Keep going");
                startActivity(Intent.createChooser(emailIntent, "Send Email"));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menuCreateNewAlarm){
            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if (mFirebaseUser != null){
                Toast.makeText(MainActivity.this, "You've already logged in.", Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(this, LoginPageActivity.class);
                startActivity(intent);
            }
            return true;
        }

        if(id == R.id.menu_check_out_leaderBoard){
            Intent intent = new Intent( this, LeaderboardActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public void logout(MenuItem item) {
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null){
            Toast.makeText(MainActivity.this, "You've not logged in yet.", Toast.LENGTH_LONG).show();
        }else {
            //logout
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        finish();
    }
}
