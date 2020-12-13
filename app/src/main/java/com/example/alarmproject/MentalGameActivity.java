package com.example.alarmproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MentalGameActivity extends AppCompatActivity {
    public final String TAG = "Logcat";
    public int request_code = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mentalgame_layout);

        MentalQuestions algebra = new MentalQuestions();

        TextView textViewQuestion = (TextView) findViewById(R.id.textViewQuestion);
        textViewQuestion.setText(algebra.GenerateAlgebraQn());

        EditText editTextAnswerByUser = findViewById(R.id.editTextAnswerByUser);





        Button buttonSubmit = findViewById(R.id.buttonSubmitAnswer);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answerByUser = editTextAnswerByUser.getText().toString();

                if (answerByUser.equals(algebra.GetAnswer())){//if true:
                    Toast.makeText(MentalGameActivity.this, "Answer is correct! \n Enjoy the rest of your day!", Toast.LENGTH_LONG).show();
                    int data = 1;


                    //call the offTheAlarm method to turn off the alarm
                    Intent intentNew = new Intent(MentalGameActivity.this, CreateAlarmActivity.class);
                    intentNew.putExtra("data", data);
                    startActivityForResult(intentNew, 1);
                    //add

                }
                else if(editTextAnswerByUser.getText().toString().equals("") ){
                    Log.i(TAG,"Edit text is empty");

                    Toast.makeText( MentalGameActivity.this, "Please enter a value", Toast.LENGTH_LONG).show();
                    int data = 0;
                    Intent intentNew = new Intent(MentalGameActivity.this, CreateAlarmActivity.class);
                    intentNew.putExtra("data", data);
                    startActivityForResult(intentNew, 1);
                }else{
                    int data = 0;
                    Toast.makeText(MentalGameActivity.this, "Sorry! Please try again!", Toast.LENGTH_LONG).show();
                    Intent intentNew = new Intent(MentalGameActivity.this, CreateAlarmActivity.class);
                    intentNew.putExtra("data", data);
                    startActivityForResult(intentNew, 1);
                }


            }


        });



    }
}
