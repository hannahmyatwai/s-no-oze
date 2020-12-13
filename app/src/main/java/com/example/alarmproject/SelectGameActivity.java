package com.example.alarmproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectGameActivity extends AppCompatActivity {
    TextView pageTextView;
    Button buttonmental;
    Button buttonvisual;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_game_sub);

        pageTextView = findViewById(R.id.selectgame);
        buttonmental = findViewById(R.id.buttonmental);
        buttonvisual = findViewById(R.id.buttonVisual);

        buttonmental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mental_intent = new Intent(SelectGameActivity.this, MentalGameActivity.class);
                startActivity(mental_intent);
            }
        });
        buttonvisual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent visual_intent = new Intent(SelectGameActivity.this, VisualGameActivity.class);
                startActivity(visual_intent);
            }
        });
    }


}
