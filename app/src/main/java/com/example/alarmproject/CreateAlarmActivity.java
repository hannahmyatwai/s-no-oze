package com.example.alarmproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateAlarmActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public final String TAG = "Logcat";

    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    static TextView alarm_status;
    Button alarm_on;
    Button alarm_off;
    static PendingIntent pending_intent;
    Context context;
    int choose_game;
    DatabaseHelper myDB;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_alarms_sub);
        this.context=this;
        myDB = new DatabaseHelper(this);


        alarm_manager=(AlarmManager) getSystemService(ALARM_SERVICE);
        alarm_timepicker=findViewById(R.id.alarmTimePicker);
        alarm_status=findViewById(R.id.alarm_status);
        final Calendar calendar =Calendar.getInstance(); //create instance of calendar

        Intent intent_alarm=new Intent(CreateAlarmActivity.this,AlarmReceiver.class); //create intent to AlarmReceiver class





        //'alarm on' button
        alarm_on=findViewById(R.id.alarm_on);
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                calendar.set(Calendar.HOUR_OF_DAY,alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE,alarm_timepicker.getMinute());

                int hour=alarm_timepicker.getHour();
                int minute=alarm_timepicker.getMinute();

                String hourStr=String.valueOf(hour);
                String minuteStr=String.valueOf(minute);
                if (minute<10){
                    minuteStr="0"+minuteStr;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                String date = dateFormat.format(calendar.getTime());
                String newEntry = "Alarm at "+hourStr+":"+minuteStr+":00 Created on "+date;
                //add data to SQlite Database
                AddData(newEntry);


                //update alarm status
                set_alarm_status("Alarm set to "+hourStr+":"+minuteStr);

                //extra string passed into receiver
                //tell clock that "alarm on" button is pressed
                intent_alarm.putExtra("extra","alarm on");

                //put extra int into intent_alarm
                //tell clock that you want a certain value from the spinner
                intent_alarm.putExtra("game_id",choose_game);

                //create pending intent -> delays intent until specified calendar time
                pending_intent=PendingIntent.getBroadcast(CreateAlarmActivity.this,0,
                        intent_alarm,PendingIntent.FLAG_UPDATE_CURRENT); //Broadcast to receiver when calendar time

                //set alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pending_intent);

            }
        });

        alarm_off=findViewById(R.id.alarm_off);
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentFromGame = getIntent();
                int data1 = intentFromGame.getIntExtra("data", 0);
                if (data1 == 1) {
                    set_alarm_status("Alarm off");

                    //cancel alarm
                    alarm_manager.cancel(pending_intent);

                    //put extra string into intent_alarm
                    //tell clock that "alarm off" is pressed
                    intent_alarm.putExtra("extra", "alarm_off");
                    //put extra int into intent_alarm
                    //prevent crashes in Null Pointer Exception

                    //stop ringtone
                    sendBroadcast(intent_alarm);
                    Intent newIntent1 = new Intent(CreateAlarmActivity.this, MainActivity.class);
                    startActivity(newIntent1);


                }
                else{
                    //Toast.makeText(CreateAlarmActivity.this, "1", Toast.LENGTH_LONG).show();
                    Intent toSelectGame = new Intent(CreateAlarmActivity.this, SelectGameActivity.class);
                    startActivity(toSelectGame);


                }




            }
        });
    }
    public static void set_alarm_status(String output){
        alarm_status.setText(output);
    }

    public void AddData(String newEntry) {

        boolean insertData = myDB.addData(newEntry);

        if(insertData==true){
            Toast.makeText(this, "Saved your alarm", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
    }


    //methods for spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(i)

        //output id that user selected
        choose_game=(int) l; //l is id
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
